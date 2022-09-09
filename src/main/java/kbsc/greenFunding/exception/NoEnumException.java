package kbsc.greenFunding.exception;

import kbsc.greenFunding.dto.response.ErrorCode;
import lombok.Getter;

@Getter
public class NoEnumException extends RuntimeException {
    private ErrorCode errorCode;

    public NoEnumException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
