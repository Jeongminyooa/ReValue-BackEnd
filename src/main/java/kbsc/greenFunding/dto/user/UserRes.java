package kbsc.greenFunding.dto.user;

import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public class UserRes {
    private final Long id;

    public UserRes(Claims claims) {
        this.id = claims.get("id", Long.class);
    }
}
