package org.gyu.solution.measured_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gyu.solution.rm_service.entity.ServiceType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeasuredDataDto {
    private Long serviceId;
    private ServiceType serviceType;
    private Double storageSize;
    private int limitUser;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    private Double storageUsage; // 사용한 데이터
}
