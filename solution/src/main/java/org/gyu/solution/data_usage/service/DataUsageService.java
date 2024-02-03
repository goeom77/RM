package org.gyu.solution.data_usage.service;

import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.user.entity.User;

public interface DataUsageService {
    void createDataUsage(User user, RmService rmService, boolean status);
}
