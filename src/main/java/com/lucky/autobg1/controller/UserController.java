package com.lucky.autobg1.controller;

import java.time.LocalDateTime;
import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.autobg1.services.UserServices;
import com.lucky.autobg1.userregistrationmodle.AuthenticationRequest;
import com.lucky.autobg1.userregistrationmodle.RegistrationRequest;
import com.lucky.autobg1.userregistrationmodle.UserEntity;
import com.lucky.autobg1.validation.UserValidation;
import com.lucky.autobg1.validation.*;
import com.lucky.autobg1.JwtUtil.*;

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
    @Autowired
    private AuthenticationValidation authenticationValidation;
    @Autowired
    private JwtTokenUtil security;
    
    
   // @CrossOrigin(origins = "http://127.0.0.1:5174") 
    @PostMapping("/createuser2")
    public ResponseEntity<Map<String, Object>> createUser2(@RequestBody RegistrationRequest userRegistration) {
        Map<String, Object> responseMap = new HashMap<>();
        
        try {
            responseMap = userValidation.userValidationexp2(userRegistration);

            if (Boolean.TRUE.equals(responseMap.get("status"))) {
                RegistrationRequest myData = (RegistrationRequest) responseMap.get("Data");
                UserEntity savedUser = userService.userRegistration(myData);
                if(savedUser!= null) {
                responseMap.put("status", "true");
                responseMap.put("Data", savedUser);
                String subject="From lucky";
                CompletableFuture.runAsync(()-> {
        			//userService.signUpSuccessfulEMail(savedUser.getEmail(),subject,savedUser.getActiveCode());
                	userService.signUpSuccessfulEMail1(savedUser.getEmail(), "From lucky", savedUser.getActiveCode());
        			//userService.sendWelcomeEmail(user.getEmail(), user.getUserName());
        		});
                }
                else {
                	responseMap.put("status", "false");
                    responseMap.put("massege", "Already user is present");
                    return ResponseEntity.ok(responseMap);
                }
                return ResponseEntity.ok(responseMap);
                
            }
            else {
            	throw new Exception();
            }
        } 
        
        catch (Exception e) {
            return ResponseEntity.badRequest().body(responseMap);
        }

      
    }
    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam String activecode) {
        boolean isActivated = userService.activateUserAccount(activecode);
        if (isActivated) {
            return ResponseEntity.ok("Account activated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired activation token.");
        }
    }
   public ResponseEntity<String> activeAccount1(@RequestParam String activecode){
	boolean isActivated  = userService.activateUserAccount(activecode);
	if(isActivated) {
		return ResponseEntity.ok("Account activated successfully.");
	}
	else {
		return ResponseEntity.badRequest().body("Invalid  activation token");
	}
    	
    }
   @GetMapping("/login")
   public ResponseEntity<Map<String, Object>>  userLogin(@RequestBody AuthenticationRequest authenticationRequest){
	   
	   Map<String, Object> maprespons =new LinkedHashMap<>();
	   maprespons= authenticationValidation.authValadtion(authenticationRequest);
	   //log.info("aftre validation");
	   if(maprespons.get("status")=="True") {
	   //log.info("if true");
	  UserEntity userEntity=userService.creatUserLogin(authenticationRequest);
	  //log.info("data"+userEntity);
	  if(userEntity==null) {
		  maprespons.put("status", "False");
		 maprespons.put("message", "login Unsucessfully");
		 //log.info("not null");
		 return ResponseEntity.ok(maprespons);
	  }
	  else {
	  if(userEntity.getStatus().equals("false")) {
		  maprespons.put("status", "False");
		  maprespons.put("message","Account is not activated");
		 // log.info("Account is not activated");
		  return ResponseEntity.ok(maprespons);
	  }
	  else {
		  
		  String token=security.generateToken(userEntity.getUsername());
		  maprespons.put("message","login sucessfully");
		  maprespons.put("token", token);
		  return ResponseEntity.ok(maprespons);
	} 
	}}
	   
	   else {
		   
		  return ResponseEntity.badRequest().body(maprespons);
	   }


}
}
 
