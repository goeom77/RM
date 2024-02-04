package org.gyu.solution.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gyu.solution.global.error.ErrorCode;
import org.gyu.solution.global.error.ErrorResponse;
import org.gyu.solution.global.error.handler.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // 필터 과정
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // HTTP 요청 헤더에서 "Authorization" 값을 가져옵니다.
            final String authHeader = request.getHeader("Authorization");
            // JWT 토큰 및 사용자 ID를 초기화합니다.
            final String jwt;
            final String uuid;
            // authHeader가 null이거나 "Bearer "로 시작하지 않으면 다음 필터로 전달합니다.
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7); // "Bearer " 제외
            // JWT 토큰에서 사용자 UUID를 가져옵니다.
            uuid = jwtTokenProvider.getUUID(jwt);
            // 유효성 검사
            if (uuid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // uuid 기반으로 사용자 정보를 가져옵니다.
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(uuid);
                // 토큰이 유효한 경우 인증 정보를 생성하고 Security Context에 설정합니다.
                if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // 현재 요청에 대한 세부 정보를 인증 토큰에 설정합니다.
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // 인증 토큰을 Security Context에 설정합니다.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            // 다음 필터로 요청과 응답을 전달합니다.
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handleTokenExpired(response, e);
        }
    }
    private void handleTokenExpired(HttpServletResponse response, ExpiredJwtException e) throws IOException {
        // JWT 만료에 대한 응답을 처리합니다.
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String result = objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.INVALID_TOKEN));
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
}