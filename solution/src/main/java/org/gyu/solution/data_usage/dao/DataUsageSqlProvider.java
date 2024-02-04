package org.gyu.solution.data_usage.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class DataUsageSqlProvider {
    public String updateStatusByUserIdListAndServiceId(Map<String, Object> params) {
        List<Long> userIdList = (List<Long>) params.get("userIdList");
        Long serviceId = (Long) params.get("serviceId");

        return new SQL() {{
            UPDATE("data_usage");
            SET("status = true");
            WHERE("user_id IN (" + StringUtils.join(userIdList, ',') + ")");
            AND();
            WHERE("service_id = #{serviceId}");
        }}.toString();
    }


    public String deleteDataUsageByUserIdListAndServiceId(Map<String, Object> params) {
        List<Long> userIdList = (List<Long>) params.get("userIdList");
        Long serviceId = (Long) params.get("serviceId");
        return new SQL() {{
            DELETE_FROM("data_usage");
            WHERE("user_id IN (" + StringUtils.join(userIdList, ',') + ")");
            AND();
            WHERE("service_id = #{serviceId}");
        }}.toString();
    }
}