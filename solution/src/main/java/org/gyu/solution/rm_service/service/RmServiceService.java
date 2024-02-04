package org.gyu.solution.rm_service.service;

import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.rm_service.vo.UpdateServiceIn;
import org.gyu.solution.user.entity.User;

public interface RmServiceService {
    RmService createService(ServiceDto serviceDto);
    ServiceDto findServiceById(Long serviceId);
    void checkLimitNumber(Long serviceId, int size);
    void UpdateServiceInfo(UpdateServiceIn updateServiceIn);
}
