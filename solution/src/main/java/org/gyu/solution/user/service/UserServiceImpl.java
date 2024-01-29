package org.gyu.solution.user.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.user.dao.UserDao;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.vo.UserSignUpIn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    @Override
    public void signUp(UserSignUpIn userSignUpIn) {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String hashedPassword = new BCryptPasswordEncoder().encode(userSignUpIn.getPassword());
        User user = User.builder()
                .loginId(userSignUpIn.getLoginId())
                .password(hashedPassword)
                .UUID(uuidStr)
                .build();
        userDao.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

}
