package org.gyu.solution.user.service;

import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.vo.UserSignUpIn;

import java.util.List;

public interface UserService {
    void signUp(UserSignUpIn userSignUpIn);
    List<User> findAll();
}
