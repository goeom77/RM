package org.gyu.solution.user.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class UserSqlProvider {
    public String findUserIdAndNameListByUserIdList(Map<String, List<Long>> params) {
        List<Long> userIdList = params.get("userIdList");
        return new SQL() {{
            SELECT("id, name");
            FROM("user");
            WHERE("id IN (" + StringUtils.join(userIdList, ',') + ")");
        }}.toString();
    }
}
