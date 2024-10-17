package com.app.pharmacy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("API9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_RESOURCE_FOUND("404", "Not Found", HttpStatus.NOT_FOUND),
    INVALID_KEY("PS0000", "Uncategorized error", HttpStatus.BAD_REQUEST),
    UN_AUTHORIZE("403", "Forbidden", HttpStatus.FORBIDDEN),
    USER_EXISTED("PS00001", "Username or Email is existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST("PS00002", "User is not found", HttpStatus.BAD_REQUEST),
    USERNAME_CANNOT_CHANGE("PS00003", "Username cannot be changed!", HttpStatus.BAD_REQUEST),
    MEDICINE_NOT_EXIST("PS00004", "Medicine is not found", HttpStatus.BAD_REQUEST),
    SUPPLIER_NOT_EXIST("PS00005", "Supplier is not found", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_EXIST("PS00006", "Customer is not found", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXIST("PS00007", "Category is not found", HttpStatus.BAD_REQUEST),
    LOCATION_RACK_NOT_EXIST("PS00008", "Location rack is not found", HttpStatus.BAD_REQUEST),
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
