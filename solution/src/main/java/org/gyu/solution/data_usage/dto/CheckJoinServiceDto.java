package org.gyu.solution.data_usage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckJoinServiceDto {
    private Long userId; // 식별자
    private Long serviceId; // 식별자
    private Long managerId;
}
