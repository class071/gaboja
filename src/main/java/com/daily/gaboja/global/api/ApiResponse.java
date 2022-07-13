package com.daily.gaboja.global.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private T response;

    public static <T> ApiResponse success(HttpStatus httpStatus, T response) {
        return new ApiResponse( httpStatus, null, response);
    }

    public static ApiResponse error(HttpStatus httpStatus, String message) {
        return new ApiResponse<>( httpStatus, message, null);
    }
}
