package org.gyu.solution.log_data.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetLogDataIn {
    private Double storageUsage;
    private LocalDateTime expirationDate;
    private Long serviceId; // 식별자
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
