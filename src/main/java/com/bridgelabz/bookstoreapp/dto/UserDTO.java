package com.bridgelabz.bookstoreapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    public String firstName;
    public  String lastName;
    public String kyc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate dob;
    public String email;
    public String password;


    public UserDTO(String firstName, String lastName, String kyc, LocalDate dob, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.kyc = kyc;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }
}
