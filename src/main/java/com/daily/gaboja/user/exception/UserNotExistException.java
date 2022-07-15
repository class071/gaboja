package com.daily.gaboja.user.exception;

public class UserNotExistException extends RuntimeException {

    private static final String message = "존재하지 않는 유저입니다.";

    public UserNotExistException(){
        super(message);
    }
}
