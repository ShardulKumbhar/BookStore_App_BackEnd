package com.bridgelabz.bookstoreapp.entity;

import com.bridgelabz.bookstoreapp.dto.UserDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Component
@ToString
@Entity
@Table(name = "user_registration")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String kyc;
    private LocalDate dob;
    private String email;
    private String password;

    private String role;


    private LocalDate createdDate;
    private Boolean isVerified = false;

    public UserData(Long id, String firstName, String lastName, String kyc, String imageURL, LocalDate dob, String email, String password, LocalDate createdDate, Boolean isVerified,String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kyc = kyc;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.role = role;
        this.isVerified = isVerified;
    }

    public UserData(UserDTO userDTO) {
        this.updateUserData(userDTO);
    }

    public UserData() {
    }

    public void updateUserData(UserDTO userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.kyc = userDTO.kyc;
        this.dob = userDTO.dob;
        this.email = userDTO.email;
        this.role = userDTO.role;
        this.password = userDTO.password;
    }
}
