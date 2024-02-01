package org.gyu.solution.rm_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.service.RmServiceService;
import org.gyu.solution.rm_service.vo.CreateServiceIn;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class RmServiceController {
    private final ModelMapper modelMapper;
    private final RmServiceService rmServiceService;
    @PostMapping("/create")
    @Operation(summary = "서비스 구독 신청", description = "서비스 구독 가입을 진행합니다.", tags = {"Service"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 생성 성공"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    public void createService(@RequestBody CreateServiceIn createServiceIn) {
        ServiceDto serviceDto = modelMapper.map(createServiceIn, ServiceDto.class);
        rmServiceService.createService(serviceDto);
        // todo : 결과 값 encrypt해서 보내기(cache에 저장하기)
        // todo : dataUsage 생성 만들기
        // todo : log에 넣기(transactional로 관리)
    }

}
