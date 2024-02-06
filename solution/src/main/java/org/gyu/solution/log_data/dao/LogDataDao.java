package org.gyu.solution.log_data.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.gyu.solution.log_data.entity.LogData;
import org.gyu.solution.log_data.vo.GetLogDataIn;
import org.gyu.solution.measured_data.dto.MeasuredDataDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface LogDataDao {
    @Insert("INSERT INTO log_data " +
            "(storage_usage, expiration_date, service_id, created_date, updated_date) " +
            "VALUES " +
            "(#{storageUsage}, #{expirationDate}, #{serviceId}, #{createdDate}, #{updatedDate})")
    void insertLogData(GetLogDataIn getLogDataIn);
    @SelectProvider(type = LogDataSqlProvider.class, method = "findLogDataByServiceIdBetweenDate")
    List<LogData> findLogDataByServiceIdBetweenDate(LocalDateTime preMeasuredDate, LocalDateTime measuredDate);
    @Select("SELECT s.id AS serviceId, s.service_type, s.storage_size, s.limit_user, s.created_date, s.expiration_date, ld.storage_usage " +
            "FROM rm_service s " +
            "JOIN log_data ld ON s.id = ld.service_id " +
            "WHERE s.id = #{serviceId} " +
            "ORDER BY ld.updated_date DESC " +
            "LIMIT 1")
    MeasuredDataDto findMeasuredDataDtoByServiceId(Long serviceId);
}
