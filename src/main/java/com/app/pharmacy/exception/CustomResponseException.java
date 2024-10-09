package com.app.pharmacy.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomResponseException extends RuntimeException {

    public CustomResponseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;
}
