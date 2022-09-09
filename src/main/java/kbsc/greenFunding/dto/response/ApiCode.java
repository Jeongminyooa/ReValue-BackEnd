package kbsc.greenFunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiCode {
    SUCCESS(200, "SUCCESS-200"),
    CREATED(201, "CREATED-201"),
    ;

    private int status;
    private String message;

}
