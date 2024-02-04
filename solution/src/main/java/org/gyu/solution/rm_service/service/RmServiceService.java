package org.gyu.solution.rm_service.service;

import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.user.entity.User;

public interface RmServiceService {
    RmService createService(ServiceDto serviceDto);
    ServiceDto findServiceById(Long serviceId);
}
