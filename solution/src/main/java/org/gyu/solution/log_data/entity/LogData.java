package org.gyu.solution.log_data.entity;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogData {
    private Long id;
    private Double storageUsage;
    private LocalDateTime expirationDate;
    private Long serviceId; // 식별자
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
