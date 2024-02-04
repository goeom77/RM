package org.gyu.solution.user.service;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.data_usage.vo.JoinOut;
import org.gyu.solution.global.config.security.JwtTokenProvider;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.handler.BusinessException;
import org.gyu.solution.user.dao.UserDao;
import org.gyu.solution.user.dto.UserDto;
import org.gyu.solution.user.entity.User;
import org.gyu.solution.user.vo.UserLoginOut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public UserLoginOut signUp(UserDto userDto) {
        UUID uuidValue = UUID.randomUUID();
        String uuid = uuidValue.toString().replaceAll("-", "");
        String hashedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        User user = User.builder()
                .loginId(userDto.getLoginId())
                .password(hashedPassword)
                .name(userDto.getName())
                .UUID(uuid)
                .build();
        userDao.save(user);
        // todo : cashing
        return UserLoginOut.builder()
                .token(jwtTokenProvider.generateToken(user))
                .build();
    }

    @Override
    public void duplicateCheck(String loginId) {
        userDao.findByLoginId(loginId)
                .ifPresent(u -> {
                    throw new BusinessException(ErrorCode.DUPLICATE_USER);
                });
    }

    @Override
    public UserLoginOut login(UserDto userDto) {
        User user = userDao.findByLoginId(userDto.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));
        if (!new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.FAIL_LOGIN);
        }
        // todo : cashing
        return UserLoginOut.builder()
                .token(jwtTokenProvider.generateToken(user))
                .build();
    }

    @Override
    public User findUserByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String uuid = authentication.getName();
            return userDao.findByUUID(uuid)
                    .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));
        }
        throw new BusinessException(ErrorCode.INVALID_TOKEN);
    }

    @Override
    public List<JoinOut> findUserIdAndNameListByUserIdList(List<Long> userIdList) {
        return userDao.findUserIdAndNameListByUserIdList(userIdList);
    }
}
