package com.bridgelabz.bookstoreapp.dto;


import lombok.Data;

@Data
public class ResponseDTO {
    private int status;
    private String message;
    private Object data;

    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO() {

    }
    public ResponseDTO(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
