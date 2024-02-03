package org.gyu.solution.data_usage.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.gyu.solution.data_usage.entity.DataUsage;

@Mapper
public interface DataUsageDao {
    @Insert("INSERT INTO " +
                "data_usage (user_id, service_id, manager_id, status) " +
            "VALUES " +
                "(#{userId}, #{serviceId}, #{managerId}, #{status})")
    void save(DataUsage dataUsage);
}
