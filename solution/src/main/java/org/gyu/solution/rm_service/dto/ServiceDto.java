package org.gyu.solution.rm_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private String serviceType;
    private Double storageSize;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    private Integer limitUser;
    private String nickName;
    private String company;
    private String phoneNumber;
    private String email;
    private String address;
}
