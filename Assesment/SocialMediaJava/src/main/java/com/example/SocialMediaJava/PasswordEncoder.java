package com.example.SocialMediaJava;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "hello2020";
        String encodedPassword  = encoder.encode(rawPassword);

        System.out.println(encodedPassword);

    }



}
