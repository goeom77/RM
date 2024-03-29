package org.gyu.solution.rm_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.service.DataUsageService;
import org.gyu.solution.global.base.Response;
import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.entity.RmService;
import org.gyu.solution.rm_service.service.RmServiceService;
import org.gyu.solution.rm_service.vo.CreateServiceIn;
import org.gyu.solution.rm_service.vo.UpdateServiceIn;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class RmServiceController {
    private final ModelMapper modelMapper;
    private final RmServiceService rmServiceService;
    private final UserService userService;
    private final DataUsageService dataUsageService;

    @PostMapping("/create")
    @Operation(summary = "서비스 구독 신청", description = "서비스 구독 신청을 진행합니다.", tags = {"Service"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 생성 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @Transactional
    public Response<?> createService(@RequestBody CreateServiceIn createServiceIn) {
        ServiceDto serviceDto = modelMapper.map(createServiceIn, ServiceDto.class);
        RmService rmService = rmServiceService.createService(serviceDto);
        User user = userService.findUserByToken();
        dataUsageService.createDataUsageByManager(user, rmService);
        return Response.ofSuccess();
    }
    @PatchMapping("/info/change")
    @Operation(summary = "서비스 구독 상세 변경", description = "스토리지, 제한 인원, 구독 기간을 연장합니다.", tags = {"Service"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상세 변경 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> changeServiceInfo(@RequestBody UpdateServiceIn updateServiceIn) {
        rmServiceService.UpdateServiceInfo(updateServiceIn);
        return Response.ofSuccess();
    }
}
