package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;


@Data
public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
