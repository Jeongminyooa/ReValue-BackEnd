package kbsc.greenFunding.exception;

import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        log.error("handleException", ex);
        return new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
    }

    @ExceptionHandler(NoEnumException.class)
    public ErrorResponse handleNoEnumException(NoEnumException ex) {
        log.error("handleNoEnumException", ex);
        return new ErrorResponse(ex.getErrorCode());
    }
}
