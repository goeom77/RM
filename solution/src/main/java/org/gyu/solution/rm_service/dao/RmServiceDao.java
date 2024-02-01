package org.gyu.solution.rm_service.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.gyu.solution.rm_service.entity.RmService;

@Mapper
public interface RmServiceDao {
    @Insert("INSERT INTO rm_service " +
            "(service_type, storage_size, created_date, expiration_date, " +
            "company, phone_number, email, address) " +
            "VALUES " +
            "(#{serviceType}, #{storageSize}, #{createdDate}, #{expirationDate}, " +
            "#{company}, #{phoneNumber}, #{email}, #{address})")
    void save(RmService rmService);
}
