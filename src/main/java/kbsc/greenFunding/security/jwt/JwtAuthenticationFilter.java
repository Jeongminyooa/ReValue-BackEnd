package kbsc.greenFunding.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kbsc.greenFunding.dto.response.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Getter
@Slf4j
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 token 값 받아오기
        String token = jwtTokenProvider.getJwtFromRequest(request);
        System.out.println("유효성 체크 전 " + token);

        try {
            // 토큰 유효성 검사
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                System.out.println("인증 완료염" + token);
                // 인증 객체 생성
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                // SecurityContextHolder에 인증 객체 저장
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", ErrorCode.INVALID_AUTH_TOKEN.toString());
        } catch (ExpiredJwtException e) {
            log.error(ErrorCode.EXPIRED_AUTH_TOKEN.toString());
            request.setAttribute("exception", ErrorCode.EXPIRED_AUTH_TOKEN.toString());
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_AUTH_TOKEN.toString());
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", ErrorCode.WRONG_TOKEN.toString());
        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", token);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", ErrorCode.INTER_SERVER_ERROR.toString());
        }


        filterChain.doFilter(request, response);
    }
}
