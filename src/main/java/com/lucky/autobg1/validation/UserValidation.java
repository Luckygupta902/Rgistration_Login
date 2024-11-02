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

import com.lucky.autobg1.userregistrationmodle.UserRegistration;




@Component
public class UserValidation {
	
	private static final Logger log = LoggerFactory.getLogger(UserValidation.class);

	
	
	public Map userValidation1(UserRegistration userRegistration){
		Map map = new HashMap<>();//use here link hashmap
		boolean a=false;
		if(userRegistration.getActiveCode()==null || userRegistration.getActiveCode().isEmpty()|| userRegistration.getActiveCode().isBlank()) {
			map.put("status", a);
			map.put("message", "Activecode is null");
			return map;
			
			
		}
		else {
			a=true;
			map.put("status", a);
			map.put("Data", userRegistration);
			
		}
		
		return map;
		
	}
	//Yes, using a try-catch block to handle potential exceptions can be a valid approach, but throwing a generic Exception here is unnecessary since you're already handling the conditions where activeCode might be null, empty, or blank. Instead, you can simply check these conditions and add the message to the map without using exceptions.
	
	public Map userValidationExp(UserRegistration userRegistration){
		Map map = new HashMap<>();
		boolean a=false;
		try {
		if(userRegistration.getActiveCode()==null || userRegistration.getActiveCode().isEmpty()|| userRegistration.getActiveCode().isBlank()) {
			
			throw new Exception();
			
		}
		}
		catch (Exception e) {
			map.put("status", a);
			map.put("message", "Activecode is nullexp");
			return map;
			// TODO: handle exception
		}
		
			a=true;
			map.put("status", a);
			map.put("Data", userRegistration);
			return map;
		
	}
	//Create a Custom Exception Class
	
	public Map userValidationexp1(UserRegistration userRegistration) {
		Map map =new LinkedHashMap<>();
		
		if(userRegistration.getActiveCode().isBlank()||userRegistration.getActiveCode().isEmpty()|| userRegistration.getActiveCode()==null ) {
			log.info("ActiveCode is null or blank, throwing InvalidActiveCodeException");
	        throw new InvalidActiveCodeException("ActiveCode is null or blank");
		}
		
		if(userRegistration.getUsername().isBlank() || userRegistration.getUsername().isEmpty()|| userRegistration.getUsername()==null) {
			log.info("Username is null or blank ,throwing InvalidactiveCodeException");
			throw new InvalidActiveCodeException("Username is null or blank");
		}
		
		map.put("status", "true");
		map.put("Data", userRegistration);
		return map;
		
	}
	
	public Map<String, Object> userValidationexp2(UserRegistration userRegistration) {
	    Map<String, Object> map = new LinkedHashMap<>();

	    Predicate<String> isNullOrBlank = str -> Optional.ofNullable(str).map(String::isBlank).orElse(true);

	    if (isNullOrBlank.test(userRegistration.getActiveCode())) {
	        log.info("ActiveCode is null or blank, throwing InvalidActiveCodeException");
	        throw new InvalidActiveCodeException("ActiveCode is null or blank");
	    }

	    if (isNullOrBlank.test(userRegistration.getUsername())) {
	        log.info("Username is null or blank, throwing InvalidActiveCodeException");
	        throw new InvalidActiveCodeException("Username is null or blank");
	    }
        if(isNullOrBlank.test(userRegistration.getCountryCode())) {
        	throw new InvalidActiveCodeException("Countrycode is null or blank");
        }
        if(isNullOrBlank.test(userRegistration.getEmail())) {
        	throw new InvalidActiveCodeException("Email is null or blank");
        }
        if(isNullOrBlank.test(userRegistration.getIsPlanActive())) {
        	throw new InvalidActiveCodeException("PlanActive Plane is null or blank");
        }
        if(isNullOrBlank.test(userRegistration.getPassword())) {
        	throw new InvalidActiveCodeException("Password is null or blank");
        }
        if(isNullOrBlank.test(userRegistration.getVertical())) {
        	throw new InvalidActiveCodeException("Vertical is null or blank");
        	
        }
        if(isNullOrBlank.test(userRegistration.getWhatsapp())) {
        	throw new InvalidActiveCodeException("Whatsapp is null or blank");
        	
        }
        if(userRegistration.getTotalCredits()==null) {
        	throw new InvalidActiveCodeException("Totalcredits is null or blank");
        }
        if(userRegistration.getCurrentCredits()== null) {
        	throw new InvalidActiveCodeException("CurrentCredites is null or blank");
        }
	    map.put("status", true);
	    map.put("Data", userRegistration);
	    return map;
	}
		
	}

	 

	
	
	


