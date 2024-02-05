package org.gyu.solution.log_data.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.log_data.dao.LogDataDao;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.log_data.vo.GetLogDataIn;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogDataServiceImpl implements LogDataService{
    private final LogDataDao logDataDao;
    @Override
    public void getLogData(GetLogDataIn getLogDataIn) {
        logDataDao.insertLogData(getLogDataIn);
    }

    @Override
    public List<LogData> findLatestLogDataByServiceId(LocalDateTime measuredDate, LocalDateTime preMeasuredDate) {
        return logDataDao.findLogDataByServiceIdBetweenDate(preMeasuredDate,measuredDate);
    }
}
