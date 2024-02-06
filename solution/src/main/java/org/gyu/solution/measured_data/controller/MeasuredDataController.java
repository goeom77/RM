package org.gyu.solution.measured_data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.global.base.Response;
import org.gyu.solution.measured_data.service.MeasuredDataService;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageIn;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageOut;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/measured_data")
@RequiredArgsConstructor
public class MeasuredDataController {
    private final MeasuredDataService measuredDataService;
    @GetMapping("/usage")
    @Operation(summary = "서비스 구독 신청", description = "서비스 사용 현황을 조회합니다.", tags = {"MeasuredData"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 생성 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "404", description = "볼수 있는 대시보드가 없습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> getMeasuredData(@RequestBody MeasuredDataUsageIn measuredDataUsageIn) {
        LocalDateTime now = LocalDateTime.now(); // 추후 결재 로그를 통해 결재일을 기준으로 조회하도록 변경
        MeasuredDataUsageOut measuredDataOut = measuredDataService.getMeasuredData(measuredDataUsageIn, now);
        return Response.ofSuccess(measuredDataOut);
    }
}
