package org.gyu.solution.rm_service.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.rm_service.dao.RmServiceDao;
import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.rm_service.entity.ServiceType;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class RmServiceServiceImpl implements RmServiceService{
    private final RmServiceDao rmServiceDao;
    @Override
    public void createService(ServiceDto serviceDto) {
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
    }
}
