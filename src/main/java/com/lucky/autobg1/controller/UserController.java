package com.lucky.autobg1.controller;

import java.util.HashMap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.autobg1.services.UserServices;
import com.lucky.autobg1.userregistrationmodle.UserRegistration;
import com.lucky.autobg1.validation.UserValidation;
import com.lucky.autobg1.validation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    
    @Autowired
    private UserServices userService;
    @Autowired
    private UserValidation userValidation;
    
    
    @PostMapping("/createuser")
    public ResponseEntity<Map> createUser( @RequestBody UserRegistration userRegistration) {
    	Map map=new HashMap<>();
    	map=userValidation.userValidationExp(userRegistration);
    	//if (Boolean.FALSE.equals(map.get("status")))we can use like this also
    	if(map.get("status").equals(false)) {
    		return ResponseEntity.ok(map);
    	}
    	if(map.get("status").equals(true)) {
    	UserRegistration myData = (UserRegistration) map.get("Data");
        UserRegistration savedUser = userService.userRegistration(myData);
        return ResponseEntity.ok(map);
    	}
		return null;
    }
    @PostMapping("/createuser1")
    public ResponseEntity<Map> createUser1( @RequestBody UserRegistration userRegistration) {
    	Map map=new HashMap<>();
    	
    	//if (Boolean.FALSE.equals(map.get("status")))we can use like this also
    	try {
    	map=userValidation.userValidationexp1(userRegistration);
    	if(Boolean.TRUE.equals(map.get("status"))) {
    	UserRegistration myData = (UserRegistration) map.get("Data");
        UserRegistration savedUser = userService.userRegistration(myData);
        log.info("Hello endpoint was called");
        }
    	}
    	catch (InvalidActiveCodeException e) {
    		map.put("status", "false");
    		map.put("message", e.getMessage());
    		log.info("in catch block");
    	   return ResponseEntity.badRequest().body(map);
			
		}
    	 return ResponseEntity.ok(map);
	
    }
    
    @PostMapping("/createuser2")
    public ResponseEntity<Map<String, Object>> createUser2(@RequestBody UserRegistration userRegistration) {
        Map<String, Object> responseMap = new HashMap<>();
        
        try {
            responseMap = userValidation.userValidationexp1(userRegistration);

            if (Boolean.TRUE.equals(responseMap.get("status"))) {
                UserRegistration myData = (UserRegistration) responseMap.get("Data");
                UserRegistration savedUser = userService.userRegistration(myData);
                responseMap.put("status", true);
                responseMap.put("savedUser", savedUser);
                log.info("User registration was successful");
            }
        } catch (InvalidActiveCodeException e) {
            responseMap.put("status", false);
            responseMap.put("message", e.getMessage());
            log.info("Exception occurred during user registration: {}", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }

        return ResponseEntity.ok(responseMap);
    }
}
 
