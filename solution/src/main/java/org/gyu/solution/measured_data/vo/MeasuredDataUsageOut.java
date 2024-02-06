package org.gyu.solution.measured_data.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gyu.solution.rm_service.entity.ServiceType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasuredDataUsageOut {
    private Long serviceId;
    private ServiceType serviceType;
    private Double storageUsage; // 사용한 데이터
    private Double storageData; // 사용 가능한 데이터
    private int limitUser; // 사용 가능한 사용자 수
    private int usedUser; // 사용자 수
    private LocalDateTime expirationDate; // 만료일
    private LocalDateTime now; // 대시보드 시간 -> 프론트로 대체가능
}
