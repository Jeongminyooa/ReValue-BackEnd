package kbsc.greenFunding.exception;

import kbsc.greenFunding.dto.response.ErrorCode;

public class EmptyFileException extends RuntimeException {
    private ErrorCode errorCode;

    public EmptyFileException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
