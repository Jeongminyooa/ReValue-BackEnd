package kbsc.greenFunding.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginReq {
    private String id;
    private String pw;
}
