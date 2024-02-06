package org.gyu.solution.data_usage.dao;

import org.apache.ibatis.annotations.*;
import org.gyu.solution.data_usage.dto.DataUsageDto;
import org.gyu.solution.data_usage.entity.DataUsage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
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
    @UpdateProvider(type = DataUsageSqlProvider.class, method = "updateStatusByUserIdListAndServiceId")
    void updateStatusByUserIdListAndServiceId(Map<String, Object> params);
    @Select("SELECT COUNT(*) FROM data_usage WHERE service_id = #{serviceId} AND status = #{status}")
    Integer countByServiceIdAndStatus(Long serviceId, boolean status);
    @DeleteProvider(type = DataUsageSqlProvider.class, method = "deleteDataUsageByUserIdListAndServiceId")
    void deleteDataUsageByUserIdListAndServiceId(List<Long> userIdList, Long serviceId);
}
