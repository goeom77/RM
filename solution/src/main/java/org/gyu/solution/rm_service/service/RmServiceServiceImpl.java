package org.gyu.solution.rm_service.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.rm_service.dao.RmServiceDao;
import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.rm_service.entity.ServiceType;
import org.gyu.solution.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
@RequiredArgsConstructor
public class RmServiceServiceImpl implements RmServiceService{
    private final RmServiceDao rmServiceDao;
    @Override
    public RmService createService(ServiceDto serviceDto) {
        System.out.println("serviceDto = " + serviceDto);
        RmService rmService = RmService.builder()
                .serviceType(ServiceType.valueOf(serviceDto.getServiceType()))
                .storageSize(serviceDto.getStorageSize())
                .createdDate(serviceDto.getCreatedDate())
                .expirationDate(serviceDto.getExpirationDate())
                .company(serviceDto.getCompany())
                .phoneNumber(serviceDto.getPhoneNumber())
                .email(serviceDto.getEmail())
                .address(serviceDto.getAddress())
                .build();
        rmServiceDao.save(rmService);
        return rmService;
    }

}
