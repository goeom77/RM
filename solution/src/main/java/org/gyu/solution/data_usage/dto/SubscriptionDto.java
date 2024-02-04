package org.gyu.solution.data_usage.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionDto {
    private Long id;
    private Long userId; // 식별자
    private Long serviceId; // 식별자
    private Long managerId;
    private Boolean status; // default false
}
