package com.lucky.autobg1.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.lucky.autobg1.userregistrationmodle.UserRegistration;
import com.lucky.autobg1.userrrepository.UserRepository;


@Service

public class UserServices {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserRegistration userRegistration(UserRegistration userRegistration) {
        return userRepository.save(userRegistration);
    }
}
