package com.hz;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtils {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public static void main(String[] args) {

        String password = "123456";
        String s = encodePassword(password);
        System.out.println(s);
    }

}
