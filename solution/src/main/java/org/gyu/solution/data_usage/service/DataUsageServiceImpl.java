package org.gyu.solution.data_usage.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.dao.DataUsageDao;
import org.gyu.solution.data_usage.dto.CheckJoinServiceDto;
import org.gyu.solution.data_usage.dto.DataUsageDto;
import org.gyu.solution.data_usage.entity.DataUsage;
import org.gyu.solution.data_usage.vo.JoinAcceptIn;
import org.gyu.solution.data_usage.vo.JoinCancelIn;
import org.gyu.solution.data_usage.vo.SubscriptionListOut;
import org.gyu.solution.global.base.Encryptor;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataUsageServiceImpl implements DataUsageService{
    private final DataUsageDao dataUsageDao;
    private final Encryptor Encryptor;

    @Override
    public void createDataUsageByManager(User user, RmService rmService) {
        // 관리자가 만드는 경우
        DataUsage dataUsage = DataUsage.builder()
                .userId(user.getId())
                .serviceId(rmService.getId())
                .managerId(user.getId())
                .status(true)
                .build();
        dataUsageDao.save(dataUsage);
    }

    @Override
    public void createDataUsageByUser(User user, CheckJoinServiceDto dto) {
        // 유저가 만드는 경우
        DataUsage dataUsage = DataUsage.builder()
                .userId(user.getId())
                .serviceId(dto.getServiceId())
                .managerId(dto.getManagerId())
                .status(false)
                .build();
        dataUsageDao.save(dataUsage);
    }

    @Override
    public List<SubscriptionListOut> findDataUsageList(Long userId) {
        List<DataUsageDto> dataUsageDtoList = dataUsageDao.findAllByUserId(userId);
        if (dataUsageDtoList == null || dataUsageDtoList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        List<SubscriptionListOut> subscriptionOutList = new ArrayList<>();
        for (DataUsageDto dataUsageDto : dataUsageDtoList) {
            String token = generateTokenIfUserIdEqualsManagerId(dataUsageDto.getId() , dataUsageDto.getUserId(), dataUsageDto.getManagerId());
            SubscriptionListOut subscriptionListOut = SubscriptionListOut.builder()
                    .serviceId(dataUsageDto.getServiceId())
                    .userId(dataUsageDto.getUserId())
                    .managerId(dataUsageDto.getManagerId())
                    .token(token)
                    .status(dataUsageDto.getStatus())
                    .build();
            subscriptionOutList.add(subscriptionListOut);
        }
        if (subscriptionOutList.isEmpty() || subscriptionOutList == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        return subscriptionOutList;
    }

    @Override
    public CheckJoinServiceDto checkJoinService(User user, String token) {
        String[] tokens = Encryptor.decryptTokens(token); // 암호화된 토큰을 복호화하여 tokens 배열에 저장
        Long id = Long.valueOf(tokens[0]);
        Long userId = Long.valueOf(tokens[1]);
        // 토큰 유효성 검사
        Long serviceId = dataUsageDao.findDataUsageById(id);
        if (serviceId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        return CheckJoinServiceDto.builder()
                .userId(user.getId())
                .serviceId(serviceId)
                .managerId(userId)
                .build();
    }

    @Override
    public List<Long> findUserIdListByServiceIdAndManagerId(Long serviceId, Long managerId) {
        return dataUsageDao.findAllUserIdByServiceIdAndManagerId(serviceId, managerId, false);
    }

    @Override
    public void acceptJoin(JoinAcceptIn joinAcceptIn) {
        Map<String, Object> params = new HashMap<>();
        params.put("userIdList", joinAcceptIn.getUserIdList());
        params.put("serviceId", joinAcceptIn.getServiceId());
        dataUsageDao.updateStatusByUserIdListAndServiceId(params);
    }

    @Override
    public Integer countByServiceIdAndStatus(Long serviceId, boolean status) {
        return dataUsageDao.countByServiceIdAndStatus(serviceId, status);
    }

    @Override
    public void refuseJoin(JoinCancelIn joinCancelIn) {
        dataUsageDao.deleteDataUsageByUserIdListAndServiceId(joinCancelIn.getUserIdList(), joinCancelIn.getServiceId());
    }

    private String generateTokenIfUserIdEqualsManagerId(Long id, Long userId, Long managerId) {
        /*
           userId와 managerId가 동일한 경우에만 토큰 생성
           추후 manager 추가기능 구현시 수정 필요
         */
        if (userId != null && userId.equals(managerId)) {
            return Encryptor.encryptTokens(id.toString(), userId.toString());
        } else {
            return null;
        }
    }
}
