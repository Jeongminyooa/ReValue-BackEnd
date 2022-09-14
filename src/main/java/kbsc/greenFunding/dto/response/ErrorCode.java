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
    ;

    private int status;
    private String code;
    private String message;
}
