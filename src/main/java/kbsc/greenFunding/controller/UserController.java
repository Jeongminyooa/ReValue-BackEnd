package kbsc.greenFunding.controller;

import kbsc.greenFunding.dto.user.UserLoginReq;
import kbsc.greenFunding.dto.user.UserSignUpReq;
import kbsc.greenFunding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Long join(@RequestBody UserSignUpReq userSignUpReq) {
        return userService.join(userSignUpReq);
    }

    @PostMapping("/login")
    public Long login(@RequestBody UserLoginReq userLoginReq) {
        return userService.login(userLoginReq);
    }
}
