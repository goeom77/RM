package org.gyu.solution.data_usage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionListOut {
    private Long userId; // 식별자
    private Long serviceId; // 식별자
    private Long managerId;
    private Boolean status;
    private String token;
}
