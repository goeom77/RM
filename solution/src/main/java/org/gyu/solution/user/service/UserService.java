package org.gyu.solution.user.service;

import org.gyu.solution.user.dto.UserDto;
import org.gyu.solution.user.vo.UserLoginOut;

public interface UserService {
    UserLoginOut signUp(UserDto userDto);
    void duplicateCheck(String loginId);
    UserLoginOut login(UserDto userDto);
}
