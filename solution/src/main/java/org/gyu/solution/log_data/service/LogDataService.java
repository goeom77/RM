package org.gyu.solution.log_data.service;

import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.log_data.vo.GetLogDataIn;

import java.time.LocalDateTime;
import java.util.List;

public interface LogDataService {
    void getLogData(GetLogDataIn getLogDataIn);
    List<LogData> findLatestLogDataByServiceId(LocalDateTime measuredDate, LocalDateTime preMeasuredDate);
}
