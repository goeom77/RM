package org.gyu.solution.log_data.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.log_data.vo.GetLogDataIn;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogDataDao {
    @Insert("INSERT INTO log_data " +
            "(storage_usage, expiration_date, service_id, created_date, updated_date) " +
            "VALUES " +
            "(#{storageUsage}, #{expirationDate}, #{serviceId}, #{createdDate}, #{updatedDate})")
    void insertLogData(GetLogDataIn getLogDataIn);
    @SelectProvider(type = LogDataSqlProvider.class, method = "findLogDataByServiceIdBetweenDate")
    List<LogData> findLogDataByServiceIdBetweenDate(LocalDateTime preMeasuredDate, LocalDateTime measuredDate);
}
