package kbsc.greenFunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(ApiCode apiCode, T data) {
        return new ApiResponse<>(apiCode.getStatus() ,data, null);
    }

}
