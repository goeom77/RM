package org.gyu.solution.data_usage.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.dao.DataUsageDao;
import org.gyu.solution.data_usage.dto.CheckJoinServiceDto;
import org.gyu.solution.data_usage.dto.SubscriptionDto;
import org.gyu.solution.data_usage.entity.DataUsage;
import org.gyu.solution.data_usage.vo.SubscriptionListOut;
import org.gyu.solution.global.base.Encryptor;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<SubscriptionDto> subscriptionDtoList = dataUsageDao.findAllByUserId(userId);
        if (subscriptionDtoList == null || subscriptionDtoList.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        List<SubscriptionListOut> subscriptionOutList = new ArrayList<>();
        for (SubscriptionDto subscriptionDto : subscriptionDtoList) {
            String token = generateTokenIfUserIdEqualsManagerId(subscriptionDto.getId() ,subscriptionDto.getUserId(), subscriptionDto.getManagerId());
            SubscriptionListOut subscriptionListOut = SubscriptionListOut.builder()
                    .serviceId(subscriptionDto.getServiceId())
                    .userId(subscriptionDto.getUserId())
                    .managerId(subscriptionDto.getManagerId())
                    .token(token)
                    .status(subscriptionDto.getStatus())
                    .build();
            subscriptionOutList.add(subscriptionListOut);
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
