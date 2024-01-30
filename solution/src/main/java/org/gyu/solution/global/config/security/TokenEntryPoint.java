package org.gyu.solution.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class TokenEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    // 사용자가 인증되지 않은 상태에서 보호된 자원에 접근 시도할 때 호출
    // 액세스 토큰이 만료될 경우 이 메서드가 실행된다.
    // 401로 응답 반환
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String result = objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.UNAUTHORIZED));
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
}
