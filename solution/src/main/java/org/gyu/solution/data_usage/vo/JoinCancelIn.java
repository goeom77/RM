package org.gyu.solution.data_usage.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinCancelIn {
    private Long serviceId;
    private List<Long> userIdList;
}
