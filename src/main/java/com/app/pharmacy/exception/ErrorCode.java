package com.app.pharmacy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("API9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("PS1000", "Uncategorized error", HttpStatus.BAD_REQUEST),
    UN_AUTHORIZE("403", "Forbidden", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(String code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final String code;
    private final String message;
    private final HttpStatusCode statusCode;
}
