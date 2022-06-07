package com.bridgelabz.bookstoreapp.util;

import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {

    public Long generateOTP() {
        return (long) Math.floor(Math.random() * 900000) + 100000;
    }
}
