package org.gyu.solution.measured_data.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.gyu.solution.log_data.entity.LogData;

import java.time.LocalDateTime;

@Mapper
public interface MeasuredDataDao {
    @Insert("INSERT INTO measured_data " +
            "(total_storage_data, total_expiration_date, service_id, payable, created_date, updated_date) " +
            "VALUES " +
            "(#{data.storageUsage}, #{data.expirationDate}, #{data.serviceId}, false, #{measuredDate}, #{measuredDate})")
    void insertMeasuredDataByMonthDate(LocalDateTime measuredDate, LogData data);
}
