package org.gyu.solution.data_usage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataUsage {
    private Long id;
    private Long userId; // 식별자
    private Long serviceId; // 식별자
    private Long managerId;
    private boolean status; // default false
}
