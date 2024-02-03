package org.gyu.solution.data_usage.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.dao.DataUsageDao;
import org.gyu.solution.data_usage.entity.DataUsage;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataUsageServiceImpl implements DataUsageService{
    private final DataUsageDao dataUsageDao;


    @Override
    public void createDataUsage(User user, RmService rmService, boolean status) {
        if (status) {
            // 관리자가 만드는 경우
            DataUsage dataUsage = DataUsage.builder()
                    .userId(user.getId())
                    .serviceId(rmService.getId())
                    .managerId(user.getId())
                    .status(true)
                    .build();
            dataUsageDao.save(dataUsage);
        } else {
            // todo : 구독자가 만드는 경우 save 생성
        }
    }
}
