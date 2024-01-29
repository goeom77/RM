package org.gyu.solution.global.config.security;

import lombok.RequiredArgsConstructor;
import org.gyu.solution.user.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserDao userDao;
    // 사용자의 세부 정보를 제공하는 서비스를 Bean으로 등록
    @Bean
    public UserDetailsService userDetailsService() {
        return loginId -> userDao.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found : {}" + loginId));
    }
    // 인증 제공자를 Bean으로 등록. 여기서는 DaoAuthenticationProvider를 사용
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // DAO 기반의 인증 제공자 생성
        authenticationProvider.setUserDetailsService(userDetailsService()); // 사용자의 세부 정보 서비스 설정
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 인코더 설정
        return authenticationProvider;
    }
    // 인증 관리자를 Bean으로 등록. Spring Security에서 중요한 역할을 하는 인터페이스
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // 기본 인증 관리자를 가져옴
    }

    // 비밀번호 인코더를 제공. 여기서는 BCrypt 방식의 비밀번호 인코딩 사용
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
