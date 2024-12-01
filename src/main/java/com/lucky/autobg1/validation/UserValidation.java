package com.lucky.autobg1.validation;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucky.autobg1.userregistrationmodle.RegistrationRequest;




@Component
public class UserValidation {
	
	private static final Logger log = LoggerFactory.getLogger(UserValidation.class);

	
	
	
	
	public Map<String, Object> userValidationexp2(RegistrationRequest userRegistration) {
	    Map<String, Object> map = new LinkedHashMap<>();
	    Map<String, Object> errors = new LinkedHashMap<>();
	    Predicate<String> isNullOrBlank = str -> Optional.ofNullable(str).map(String::isBlank).orElse(true);
	    if (isNullOrBlank.test(userRegistration.getUsername())) {
	       errors.put("Username","Username is null or blank");
	        
	    }
	   if(isNullOrBlank.test(userRegistration.getCc())) {
        	errors.put("Countrycode","Countrycode is null or blank");
        }
        if(isNullOrBlank.test(userRegistration.getEmail())) {
        	errors.put("Email","Email is null or blank");
        }
       
        if(isNullOrBlank.test(userRegistration.getPassword())) {
        	errors.put("Password","Password is null or blank");
        }
        if(isNullOrBlank.test(userRegistration.getVertical())) {
        	errors.put("Vertical","Vertical is null or blank");
        	
        }
        if(isNullOrBlank.test(userRegistration.getWhatsapp())) {
        	errors.put("Whatsapp","Whatsapp is null or blank");
        	
        }
        
        if(errors.isEmpty()) {
	    map.put("status", true);
	    map.put("message", "Registration Successful");
	    map.put("Data", userRegistration);
	    
	    return map;
        }
        else {
        	 map.put("status", false);
        	 map.put("message", "Registration UnSuccessful");
     	    map.put("Data", errors);
     	    return map;
        }
	}
		
	}

	 

	
	
	


