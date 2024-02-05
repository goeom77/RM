package org.gyu.solution.measured_data.service;

import org.gyu.solution.log_data.entity.LogData;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasuredDataService {
    void insertMeasuredDataByMonthDate(LocalDateTime measuredDate,List<LogData> latestLogDataByServiceId);
}
