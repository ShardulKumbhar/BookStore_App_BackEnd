package com.bridgelabz.bookstoreapp.exception;

public class UserException extends RuntimeException {
    public enum ExceptionType {
        USER_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        PASSWORD_INVALID,
        ALREADY_VERIFIED,
        INVALID_DATA,
        USER_NOT_FOUND,
    }

    public UserException.ExceptionType type;


    public UserException(String message,UserException.ExceptionType type){
        super(message);
        this.type = type;
    }
}
