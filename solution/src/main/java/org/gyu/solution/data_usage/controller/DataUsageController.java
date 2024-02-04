package org.gyu.solution.data_usage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.gyu.solution.data_usage.dto.CheckJoinServiceDto;
import org.gyu.solution.data_usage.service.DataUsageService;
import org.gyu.solution.data_usage.vo.*;
import org.gyu.solution.global.base.Response;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.gyu.solution.rm_service.service.RmServiceService;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.service.UserService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
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
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> joinService(@RequestBody JoinServiceIn joinServiceIn) {
        User user = userService.findUserByToken();
        CheckJoinServiceDto checkJoinServiceDto = dataUsageService.checkJoinService(user, joinServiceIn.getToken());
        dataUsageService.createDataUsageByUser(user, checkJoinServiceDto);
        return Response.ofSuccess();
    }

    @GetMapping("/join_list")
    @Operation(summary = "서비스 구독에 가입 신청 내역 조회", description = "서비스 구독에 가입 신청 내역을 모두 조회합니다.", tags = {"DataUsage"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 구독 요청 조회 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> joinList(@RequestParam("serviceId") Long serviceId) {
        User user = userService.findUserByToken();
        List<Long> userIdList =
                dataUsageService.findUserIdListByServiceIdAndManagerId(serviceId, user.getId());
        return Response.ofSuccess(JoinListOut.builder()
                .serviceInfo(rmServiceService.findServiceById(serviceId))
                .joinList(userService.findUserIdAndNameListByUserIdList(userIdList))
                .build());
    }

    @PatchMapping("/join/accept")
    @Operation(summary = "서비스 구독에 가입 신청 승인", description = "서비스 구독에 가입을 승인합니다.", tags = {"DataUsage"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 구독 요청 승인 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> acceptJoin(@RequestBody JoinAcceptIn joinAcceptIn) {
        User user = userService.findUserByToken();
        if (joinAcceptIn.getUserIdList().contains(user.getId())) {
            throw new BusinessException(ErrorCode.MANAGER_DELETE_REQUEST);
        }
        dataUsageService.acceptJoin(joinAcceptIn);
        return Response.ofSuccess();
    }

    @DeleteMapping("/join/cancel")
    @Operation(summary = "서비스 구독에 가입 신청 거부", description = "서비스 구독에 가입을 거부합니다 or 구독 취소", tags = {"DataUsage"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 구독 요청 거부 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public Response<?> cancelJoin(@RequestBody JoinCancelIn joinCancelIn) {
        User user = userService.findUserByToken();
        if (joinCancelIn.getUserIdList().contains(user.getId())) {
            throw new BusinessException(ErrorCode.MANAGER_DELETE_REQUEST);
        }
        dataUsageService.refuseJoin(joinCancelIn);
        return Response.ofSuccess();
    }
}
