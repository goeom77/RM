package org.gyu.solution.data_usage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.dto.CheckJoinServiceDto;
import org.gyu.solution.data_usage.service.DataUsageService;
import org.gyu.solution.data_usage.vo.JoinServiceIn;
import org.gyu.solution.data_usage.vo.SubscriptionListOut;
import org.gyu.solution.global.base.Response;
import org.gyu.solution.rm_service.service.RmServiceService;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/data_usage")
@RequiredArgsConstructor
public class DataUsageController {
    private final DataUsageService dataUsageService;
    private final RmServiceService rmServiceService;
    private final UserService userService;
    @GetMapping("/subscription_list")
    @Operation(summary = "유저의 구독 내역 조회", description = "유저의 구독 내역과 상태를 조회합니다.", tags = {"DataUsage"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> subscriptionList() {
        User user = userService.findUserByToken();
        List<SubscriptionListOut> subscriptionListOut = dataUsageService.findDataUsageList(user.getId());
        return Response.ofSuccess(subscriptionListOut);
    }

    @PostMapping("/join")
    @Operation(summary = "서비스 구독에 가입 신청", description = "서비스 구독에 가입을 신청합니다.", tags = {"DataUsage"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 구독 요청 성공"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> joinService(@RequestBody JoinServiceIn joinServiceIn) {
        User user = userService.findUserByToken();
        CheckJoinServiceDto checkJoinServiceDto = dataUsageService.checkJoinService(user, joinServiceIn.getToken());
        dataUsageService.createDataUsageByUser(user, checkJoinServiceDto);
        return Response.ofSuccess();
    }

}
