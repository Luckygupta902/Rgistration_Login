package com.lucky.autobg1.services;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lucky.autobg1.userregistrationmodle.AuthenticationRequest;
import com.lucky.autobg1.userregistrationmodle.RegistrationRequest;
import com.lucky.autobg1.userregistrationmodle.UserEntity;
import com.lucky.autobg1.userrrepository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service

public class UserServices {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    
    public UserEntity userRegistration(RegistrationRequest userRegistration) {
    	UserEntity userEntity= new UserEntity();
    	if(userRegistration!=null) {
    		userEntity.setUsername(userRegistration.getUsername());
    		userEntity.setEmail(userRegistration.getEmail());
    		userEntity.setVertical(userRegistration.getVertical());
    		String hashPassword=hashcodeCreated(userRegistration.getPassword());
    		userEntity.setPassword(hashPassword);
    		userEntity.setWhatsapp(userRegistration.getWhatsapp());
    		userEntity.setCountryCode(userRegistration.getCc());
    		String activecode=isActivatedCode();
    		userEntity.setActiveCode(activecode);
    		userEntity.setCurrentCredits(10);
    		userEntity.setTotalCredits(10);
    		userEntity.setIsPlanActive("0");
    		userEntity.setStatus("false");
    		userRepository.save(userEntity);
    		return userEntity;
    	}
    	return null;
       
    }
    
    public String isActivatedCode() {
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddMMyyyyHHmmss");
    	String simple=simpleDateFormat.format(new Date() );
    	
    	
    	Random random = new Random();
    	int randomNum =random.nextInt(91)+100;
    	return simple+randomNum;
    }
    
    public String hashcodeCreated( String password) {
    	 try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messagedigest=md.digest(password.getBytes());
			 BigInteger no = new BigInteger(1, messagedigest);
			StringBuilder str=new StringBuilder(no.toString(16));
			while (str.length() < 32) {
	            str.insert(0, "0");
	        }
	        return str.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    	
    	
    	
    }
    
    public void sendHtmlEmail(String to, String subject, String activeCode) {
        /*MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("lucky2301196@gmail.com");
            helper.setSubject(subject);
            helper.setText("Email content", true); // true indicates HTML content
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception properly
        }
        mailSender.send(message);
    }*/
    	SimpleMailMessage message =new SimpleMailMessage();
    	message.setFrom("lucky2301196@gmail.com");
    	message.setTo(to);
    	message.setText("mail send sucess");
    	message.setSubject(subject);
    	mailSender.send(message);
    	
    }

    public void signUpSuccessfulEMail(String email, String subject, String activeCode) {
    	 String activationLink = "http://127.0.0.1:5173/activatedcode?token=" + activeCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lucky2301196@gmail.com");
        message.setTo(email);
        message.setText("<p>Please click the link below to activate your account:</p>"
                + "<a href='" + activationLink + "'>Activate Account</a>" + activeCode);
        message.setSubject(subject);
        
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }
    
    
    
    public boolean activateUserAccount(String token) {
        UserEntity user = userRepository.findByActiveCode(token);
        if (user != null) {
            user.setActiveCode("");
            user.setStatus("True");
            user.setIsPlanActive("True");
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
   

    public void signUpSuccessfulEMail1(String email, String subject, String activeCode) {
        String activationLink = "http://localhost:8080/activate?activecode=" + activeCode;

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("lucky2301196@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);

            // HTML email content
            String htmlContent = "<p>Please click the link below to activate your account:</p>"
                    + "<p><a href='" + activationLink + "'>Activate Account</a></p>"
                    + "<p>If you did not sign up, you can ignore this email.</p>";

            helper.setText(htmlContent, true);  // Set to true to indicate HTML content

            mailSender.send(mimeMessage);
            System.out.println("HTML email sent successfully to: " + email);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
   public UserEntity creatUserLogin(AuthenticationRequest authenticationRequest){
	  UserEntity userEntity= userRepository.findByEmail(authenticationRequest.getEmail());
	  if(userEntity!= null) {
		  return userEntity;
	  }
	  else {
		  return null;
	  }
	
	   
   }


}
