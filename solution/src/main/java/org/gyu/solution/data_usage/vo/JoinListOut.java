package org.gyu.solution.data_usage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gyu.solution.rm_service.dto.ServiceDto;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinListOut {
    private ServiceDto serviceInfo;
    private List<JoinOut> joinList;
}
