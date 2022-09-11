package kbsc.greenFunding.exception;

import kbsc.greenFunding.dto.response.ErrorCode;
import kbsc.greenFunding.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        log.error("handleException", ex);
        return new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
    }

    /**
     * enum 타입이 맞지 않을 때 발생
     */
    @ExceptionHandler(NoEnumException.class)
    public ErrorResponse handleNoEnumException(NoEnumException ex) {
        log.error("handleNoEnumException", ex);
        return new ErrorResponse(ex.getErrorCode());
    }

    /**
     * 파일 업로드 용량 초과시 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ErrorResponse handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException e) {
        log.error("handleMaxUploadSizeExceededException", e);
        return new ErrorResponse(ErrorCode.FILE_SIZE_EXCEED);
    }

    /**
     * 파일 개수 초과시 발생
     */
    @ExceptionHandler(MaxUploadNumberExceededException.class)
    protected ErrorResponse handleMaxUploadNumberExceedeException(MaxUploadNumberExceededException ex) {
        log.error("handleMaxUploadNumberExceedeException", ex);
        return new ErrorResponse(ex.getErrorCode());
    }
}
