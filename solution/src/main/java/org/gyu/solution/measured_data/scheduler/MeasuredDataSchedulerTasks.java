package org.gyu.solution.measured_data.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.log_data.service.LogDataService;
import org.gyu.solution.measured_data.service.MeasuredDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeasuredDataSchedulerTasks {
    private final LogDataService logDataService;
    private final MeasuredDataService measuredDataService;
    /**
     * 매월 1일 0시에 log_data 를 기준으로 중간 정산하는 로직
     */
    @Scheduled(cron = "0 0 0 1 * *") // 매월 1일 0시 0분 0초에 실행
    public void executeAllData() {
        System.out.println("executeAllData - scheduler start");
        LocalDateTime measuredDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime preMeasuredDate = measuredDate.minusMonths(1);
        // 1일 0시를 기준으로 데이터 최근값 serviceId별로 조회
        List<LogData> latestLogDataByServiceId = logDataService.findLatestLogDataByServiceId(measuredDate, preMeasuredDate);
        // serviceId별로 조회한 데이터를 기준으로 월별 데이터 생성
        for(LogData logData : latestLogDataByServiceId){ // 추후 batch로 변경
            measuredDataService.insertMeasuredDataByMonthDate(measuredDate, logData);
        }
    }

}
