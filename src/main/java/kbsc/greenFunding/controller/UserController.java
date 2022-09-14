package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.dto.user.MyPageRes;
import kbsc.greenFunding.dto.user.UserLoginReq;
import kbsc.greenFunding.dto.user.UserSignUpReq;
import kbsc.greenFunding.security.jwt.JwtTokenProvider;
import kbsc.greenFunding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public ApiResponse<Long> join(@RequestBody UserSignUpReq userSignUpReq) {
        Long userId = userService.join(userSignUpReq);
        return ApiResponse.success(ApiCode.SUCCESS, userId);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLoginReq userLoginReq) {
        String token = userService.login(userLoginReq);
        return ApiResponse.success(ApiCode.SUCCESS, token);
    }

    @GetMapping("/mypage")
    public ApiResponse<MyPageRes> mypage(HttpServletRequest req) {
        String token = jwtTokenProvider.getJwtFromRequest(req);
        Long userId = jwtTokenProvider.getUserPk(token);

        MyPageRes result = userService.mypage(userId);
        return ApiResponse.success(ApiCode.SUCCESS, result);
    }
}
