package com.lucky.autobg1.validation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.lucky.autobg1.userregistrationmodle.AuthenticationRequest;

@Component
public class AuthenticationValidation {
public 	Map<String, Object>  authValadtion( AuthenticationRequest authenticationRequest){
	Map<String, Object> map=new LinkedHashMap<>();
	Map<String, Object> errors=new LinkedHashMap<>();
	 Predicate<String> isNullOrBlank = str -> Optional.ofNullable(str).map(String::isBlank).orElse(true);
	    if (isNullOrBlank.test(authenticationRequest.getEmail())) {
	       errors.put("Email","Username is null or blank");
	        
	    }
	    if (isNullOrBlank.test(authenticationRequest.getPassword())) {
		      errors.put("Username","Username is null or blank");
		        
		    }
	    if(errors.isEmpty()) {
	    	map.put("status", "True");
	    	
	    	
	    	
	    }
	    else {
	    	map.put("status", "false");
	    	map.put("errors", errors);
	    	
	    	
	    	
	    }
	    
	    return map;
		
	}

}
