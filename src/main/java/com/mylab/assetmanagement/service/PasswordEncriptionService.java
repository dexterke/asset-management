package com.mylab.assetmanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class PasswordEncriptionService extends BCryptPasswordEncoder{

    private BCryptPasswordEncoder passwordEncoder;

    public PasswordEncriptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
