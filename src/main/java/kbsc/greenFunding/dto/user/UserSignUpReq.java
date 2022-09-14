package kbsc.greenFunding.dto.user;

import kbsc.greenFunding.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignUpReq {
    private String userId;
    private String pw;
    private String name;
    private String birth;
    private Gender gender;
    private String tel;
    private boolean agreement;


}
