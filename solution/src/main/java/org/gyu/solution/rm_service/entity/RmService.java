package org.gyu.solution.rm_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RmService {
    private Long id;
    private ServiceType serviceType;
    private Double storageSize;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    /**
     * 이하 company info ,nullable false
     */
    private String company;
    private String phoneNumber;
    private String email;
    private String address;
}
