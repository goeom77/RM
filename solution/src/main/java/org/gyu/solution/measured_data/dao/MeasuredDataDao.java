package org.gyu.solution.measured_data.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.measured_data.entity.MeasuredData;
import org.gyu.solution.measured_data.vo.MeasuredDataUsageIn;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MeasuredDataDao {
    @Insert("INSERT INTO measured_data " +
            "(total_storage_data, total_expiration_date, service_id, payable, created_date, updated_date) " +
            "VALUES " +
            "(#{data.storageUsage}, #{data.expirationDate}, #{data.serviceId}, false, #{measuredDate}, #{measuredDate})")
    void insertMeasuredDataByMonthDate(LocalDateTime measuredDate, LogData data);
    @Select("SELECT * FROM measured_data " +
            "WHERE service_id = #{in.serviceId} " +
            "AND updated_date <= #{now}")
    List<MeasuredData> findMeasuredDataByServiceIdBetweenDate(MeasuredDataUsageIn in, LocalDateTime now);
}
