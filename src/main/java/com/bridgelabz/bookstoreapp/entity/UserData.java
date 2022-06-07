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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String kyc;
    private LocalDate dob;
    private String email;
    private String password;
    private LocalDate createdDate;
    private Boolean isVerified=false;

    public UserData(Long id, String firstName, String lastName, String kyc, LocalDate dob, String email, String password, LocalDate createdDate, Boolean isVerified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kyc = kyc;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.isVerified = isVerified;
    }

    public UserData(UserDTO userDTO) {
        this.updateUserData(userDTO);
    }

    public UserData() {
    }

    private void updateUserData(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.kyc = userDTO.getKyc();
        this.dob = userDTO.getDob();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }
}
