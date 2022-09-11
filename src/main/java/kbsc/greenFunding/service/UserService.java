package kbsc.greenFunding.service;

import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.dto.user.UserLoginReq;
import kbsc.greenFunding.dto.user.UserSignUpReq;
import kbsc.greenFunding.entity.User;
import kbsc.greenFunding.exception.NoEnumException;
import kbsc.greenFunding.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long join(UserSignUpReq signUpReq) {
        if(userJpaRepository.findByUserId(signUpReq.getUserId()).isPresent()) {
            // throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = User.builder().userId(signUpReq.getUserId()).pw(signUpReq.getPw())
                .name(signUpReq.getName()).birth(signUpReq.getBirth()).gender(signUpReq.getGender())
                .tel(signUpReq.getTel()).agreement(signUpReq.isAgreement())
                .signDate(LocalDateTime.now()).build();
        userJpaRepository.save(user);
        return user.getId();
    }

    /*
    public boolean checkUserId(String userId) {
        if(userJpaRepository.findByUserId(userId).isPresent()) {
            // throw new IllegalArgumentException("이미 가입된 이메일입니다.");
            return false;
        }
        return true;
    }
    */

    @Transactional
    public Long login(UserLoginReq loginReq) {
        User user = userJpaRepository.findByUserId(loginReq.getId())
                .orElseThrow(() -> new IllegalArgumentException("가입된 아이디가 아닙니다."));

        String pw = loginReq.getPw();

        if(!user.getPw().equals(pw)) { // 로그인 실패
            throw new IllegalArgumentException();
        }
        return user.getId();
    }
}
