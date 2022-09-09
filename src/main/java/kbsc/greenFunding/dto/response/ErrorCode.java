package kbsc.greenFunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "COMMON-ERR-404", "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "INTER SERVER ERROR"),
    NO_ENUM_CONSTANT(500, "ENUM-ERR-500", "ENUM TYPE ERROR"),
    ;

    private int status;
    private String code;
    private String message;
}
