package org.gyu.solution.data_usage.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.gyu.solution.data_usage.dto.DataUsageDto;
import org.gyu.solution.data_usage.entity.DataUsage;

import java.util.List;

@Mapper
public interface DataUsageDao {
    @Insert("INSERT INTO " +
                "data_usage (user_id, service_id, manager_id, status) " +
            "VALUES " +
                "(#{userId}, #{serviceId}, #{managerId}, #{status})")
    void save(DataUsage dataUsage);

    @Select("SELECT * FROM data_usage WHERE user_id = #{userId}")
    List<DataUsageDto> findAllByUserId(Long userId);
    @Select("SELECT service_id FROM data_usage WHERE id = #{id}")
    Long findDataUsageById(Long id);
    @Select("SELECT user_id FROM data_usage " +
            "WHERE service_id = #{serviceId} AND manager_id = #{managerId} AND status = #{status}")
    List<Long> findAllUserIdByServiceIdAndManagerId(Long serviceId, Long managerId, Boolean status);
}
