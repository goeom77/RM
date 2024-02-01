package org.gyu.solution.rm_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private String serviceType;
    private Double storageSize;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    private String company;
    private String phoneNumber;
    private String email;
    private String address;
}
