package org.gyu.solution.user.service;

import org.gyu.solution.data_usage.vo.JoinOut;
import org.gyu.solution.user.dto.UserDto;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.vo.UserLoginOut;

import java.util.List;

public interface UserService {
    UserLoginOut signUp(UserDto userDto);
    void duplicateCheck(String loginId);
    UserLoginOut login(UserDto userDto);
    User findUserByToken();
    List<JoinOut> findUserIdAndNameListByUserIdList(List<Long> dataUsageDtoList);
}
