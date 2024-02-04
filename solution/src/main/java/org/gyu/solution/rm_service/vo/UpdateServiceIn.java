package org.gyu.solution.rm_service.vo;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@ToString
@Data
public class UpdateServiceIn {
    private Long serviceId;
    private Double storageSize;
    private Integer limitUser;
    private LocalDateTime expirationDate;
}
