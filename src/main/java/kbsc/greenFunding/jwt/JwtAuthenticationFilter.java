package kbsc.greenFunding.jwt;

import kbsc.greenFunding.dto.user.UserRes;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 token 값 받아오기
        String token = jwtTokenProvider.getJwtFromRequest(request);
        System.out.println(token);

        // 토큰 유효성 검사
        if (token != null) {
            System.out.println("인증 완료염" + token);
            // 인증 객체 생성
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            // SecurityContextHolder에 인증 객체 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
