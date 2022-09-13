package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.response.ApiCode;
import kbsc.greenFunding.dto.response.ApiResponse;
import kbsc.greenFunding.dto.user.MyPageRes;
import kbsc.greenFunding.dto.user.UserLoginReq;
import kbsc.greenFunding.dto.user.UserSignUpReq;
import kbsc.greenFunding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse<Long> join(@RequestBody UserSignUpReq userSignUpReq) {
        Long userId = userService.join(userSignUpReq);
        return ApiResponse.success(ApiCode.SUCCESS, userId);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLoginReq userLoginReq) {
        return ApiResponse.success(ApiCode.SUCCESS, userService.login(userLoginReq));
    }

    @GetMapping("/mypage/{userId}")
    public ApiResponse<MyPageRes> mypage(@PathVariable("userId") Long userId) {
        MyPageRes result = userService.mypage(userId);
        return ApiResponse.success(ApiCode.SUCCESS, result);
    }
}
