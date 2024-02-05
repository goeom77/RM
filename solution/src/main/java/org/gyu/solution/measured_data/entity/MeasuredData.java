package org.gyu.solution.measured_data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeasuredData {
    private Long id;
    private Double totalStorageData;
    private LocalDateTime totalExpirationDate;
    private Long serviceId; // 식별자
    private Boolean payable; // default false
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
