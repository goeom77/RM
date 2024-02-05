package org.gyu.solution.log_data.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;

public class LogDataSqlProvider {
    public String findLogDataByServiceIdBetweenDate(
            @Param("preMeasuredDate") LocalDateTime preMeasuredDate,
            @Param("measuredDate") LocalDateTime measuredDate){
        return new SQL() {{
            SELECT("id, storage_usage, expiration_date, service_id, created_date, updated_date");
            FROM("log_data ld");
            WHERE("(service_id, updated_date) IN (" +
                    "SELECT service_id, MAX(updated_date) AS updated_date " +
                    "FROM log_data " +
                    "WHERE updated_date <= #{measuredDate} AND updated_date > #{preMeasuredDate} " +
                    "GROUP BY service_id)");
        }}.toString();
    }
}
