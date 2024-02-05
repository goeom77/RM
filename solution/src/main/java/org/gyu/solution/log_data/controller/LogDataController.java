package org.gyu.solution.log_data.controller;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.log_data.service.LogDataService;
import org.gyu.solution.log_data.vo.GetLogDataIn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log_data")
public class LogDataController {

    private final LogDataService logDataService;
    /**
     * 로그 데이터 생성 테스트
     */
    @PostMapping("/get_log_data")
    public void getLogData() {
        LocalDateTime localDataTime = LocalDateTime.now();
        LocalDateTime expirationDate = localDataTime.plusDays(10);
        GetLogDataIn getLogDataIn = new GetLogDataIn(22.2, expirationDate, 3L, localDataTime,localDataTime);
        logDataService.getLogData(getLogDataIn);
    }
}
