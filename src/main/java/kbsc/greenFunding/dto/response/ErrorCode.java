package kbsc.greenFunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "COMMON-ERR-404", "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "INTER SERVER ERROR"),
    NO_ENUM_CONSTANT(500, "ENUM-ERR-500", "ENUM TYPE ERROR"),
    EMPTY_FILE(500, "EMPTY-FILE_ERR-500", "FILE NOT EXISTS"),
    FILE_SIZE_EXCEED(500, "FILE-SIZE-ERR-500", "FILE SIZE EXCEED ERROR"),
    MAX_NUMBER_FILE_EXCEED(500, "MAX-NUMBER-FILE-ERR-500", "MAX UPLOAD NUMBER FILE EXCEED"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    ACCESS_DENIED(401, "UNAUTHORIZED", "인증되지 않은 사용자입니다."),
    INVALID_AUTH_TOKEN(401 ,"UNAUTHORIZED", "권한 정보가 없는 토큰입니다"),
    EXPIRED_AUTH_TOKEN(401, "UNAUTHORIZED", "액세스 토큰이 만료되었습니다. 토큰을 재발급해주세요."),
    UNAUTHORIZED_MEMBER(401, "UNAUTHORIZED", "현재 내 계정 정보가 존재하지 않습니다"),
    UNSUPPORTED_AUTH_TOKEN(401, "UNAUTHORIZED", "지원되지 않는 토큰입니다."),
    WRONG_TOKEN(401, "UNAUTHORIZED", "잘못된 형식의 토큰입니다."),
    ;

    private int status;
    private String code;
    private String message;
}
