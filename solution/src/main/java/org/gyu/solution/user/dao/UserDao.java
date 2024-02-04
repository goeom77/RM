package org.gyu.solution.user.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.gyu.solution.data_usage.vo.JoinOut;
import org.gyu.solution.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE login_id = #{loginId}  LIMIT 1")
    Optional<User> findByLoginId(String loginId);
    @Select("SELECT * FROM user WHERE uuid = #{UUID}  LIMIT 1")
    Optional<User> findByUUID(String UUID);
    @Insert("INSERT INTO user (login_id, password, name, uuid) " +
            "VALUES (#{loginId}, #{password}, #{name} , #{UUID})")
    void save(User user);
    @SelectProvider(type = UserSqlProvider.class, method = "findUserIdAndNameListByUserIdList")
    List<JoinOut> findUserIdAndNameListByUserIdList(List<Long> userIdList);

}
