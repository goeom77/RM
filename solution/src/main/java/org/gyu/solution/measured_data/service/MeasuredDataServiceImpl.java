package org.gyu.solution.measured_data.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.measured_data.dao.MeasuredDataDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasuredDataServiceImpl implements MeasuredDataService{
    private final MeasuredDataDao measuredDataDao;
    @Override
    public void insertMeasuredDataByMonthDate(LocalDateTime measuredDate,List<LogData> latestLogDataByServiceId) {
        for (LogData logData : latestLogDataByServiceId) {
            measuredDataDao.insertMeasuredDataByMonthDate(measuredDate, logData);
        }

    }
}
