package org.gyu.solution.data_usage.service;

import org.gyu.solution.data_usage.dto.CheckJoinServiceDto;
import org.gyu.solution.data_usage.vo.SubscriptionListOut;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.user.entity.User;

import java.util.List;

public interface DataUsageService {
    void createDataUsageByManager(User user, RmService rmService);
    void createDataUsageByUser(User user, CheckJoinServiceDto checkJoinServiceDto);
    List<SubscriptionListOut> findDataUsageList(Long userId);
    CheckJoinServiceDto checkJoinService(User user, String token);
}
