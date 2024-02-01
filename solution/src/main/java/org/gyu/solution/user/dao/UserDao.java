package org.gyu.solution.user.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.gyu.solution.user.entity.User;

import java.util.Optional;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE login_id = #{loginId}  LIMIT 1")
    Optional<User> findByLoginId(String loginId);
    @Select("SELECT * FROM user WHERE uuid = #{UUID}  LIMIT 1")
    Optional<User> findByUUID(String UUID);
    @Insert("INSERT INTO user (login_id, password, uuid) " +
            "VALUES (#{loginId}, #{password}, #{UUID})")
    void save(User user);
}
