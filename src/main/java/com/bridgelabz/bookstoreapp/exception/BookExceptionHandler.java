package com.bridgelabz.bookstoreapp.exception;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseDTO> handleBookExceptionHandler(
            BookStoreException bookStoreException){
        return new ResponseEntity<>(new ResponseDTO(bookStoreException.exceptionTypes.errorMsg,
                null),
                HttpStatus.BAD_REQUEST);
    }
}