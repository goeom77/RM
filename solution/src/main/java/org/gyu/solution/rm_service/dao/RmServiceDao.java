package org.gyu.solution.rm_service.dao;

import org.apache.ibatis.annotations.*;
import org.gyu.solution.rm_service.dto.ServiceDto;
import org.gyu.solution.rm_service.entity.RmService;

import java.util.Optional;

@Mapper
public interface RmServiceDao {
    @Insert("INSERT INTO rm_service " +
            "(service_type, storage_size, created_date, expiration_date, limit_user, " +
            "nick_name, company, phone_number, email, address) " +
            "VALUES " +
            "(#{serviceType}, #{storageSize}, #{createdDate}, #{expirationDate}, #{limitUser}, " +
            "#{nickName}, #{company}, #{phoneNumber}, #{email}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(RmService rmService);
    @Select("SELECT * FROM rm_service WHERE id = #{serviceId}")
    Optional<ServiceDto> findServiceDtoById(Long serviceId);
    @Select("SELECT * FROM rm_service WHERE id = #{serviceId}")
    Optional<RmService> findServiceById(Long serviceId);
    @Select("SELECT limit_user FROM rm_service WHERE id = #{id}")
    int findLimitUserById(Long id);

    @Update("UPDATE rm_service " +
            "SET storage_size = #{storageSize}, limit_user = #{limitUser}, expiration_date = #{expirationDate} " +
            "WHERE id = #{id}")
    void updateServiceInfo(RmService rmService);

}
