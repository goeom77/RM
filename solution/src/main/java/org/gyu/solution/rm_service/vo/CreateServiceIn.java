package org.gyu.solution.rm_service.vo;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Getter
@ToString
@Data
public class CreateServiceIn {
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
