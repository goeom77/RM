package org.gyu.solution.measured_data.service;

import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageIn;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageOut;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasuredDataService {
    void insertMeasuredDataByMonthDate(LocalDateTime measuredDate,LogData logData);

    MeasuredDataUsageOut getMeasuredData(MeasuredDataUsageIn measuredDataUsageIn, LocalDateTime now);
}
