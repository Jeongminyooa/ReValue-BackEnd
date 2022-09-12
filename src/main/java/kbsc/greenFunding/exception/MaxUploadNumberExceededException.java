package kbsc.greenFunding.exception;

import kbsc.greenFunding.dto.response.ErrorCode;
import lombok.Getter;

@Getter
public class MaxUploadNumberExceededException extends RuntimeException {
    private ErrorCode errorCode;

    public MaxUploadNumberExceededException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
