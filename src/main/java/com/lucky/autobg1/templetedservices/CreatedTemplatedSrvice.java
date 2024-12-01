package com.lucky.autobg1.templetedservices;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lucky.autobg1.userrrepository.UserRepository;
import com.lucky.autobg1.userrrepository.UserTemplatesRepository;

import lombok.extern.slf4j.Slf4j;

import com.lucky.autobg1.JwtUtil.JwtTokenUtil;
import com.lucky.autobg1.createdtempletedmodle.*;
import com.lucky.autobg1.services.UserServices;
import com.lucky.autobg1.userregistrationmodle.UserEntity;

@Slf4j
@Service
public class CreatedTemplatedSrvice {
	
	private static final Logger log = LoggerFactory.getLogger(CreatedTemplatedSrvice.class);

	@Autowired
	UserTemplatesRepository userTemplatesRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	public String checkImgExtension( MultipartFile multipartFile){
		String file=multipartFile.getOriginalFilename();
		
		 int dotIndex = file.lastIndexOf('.');
	        return (dotIndex != -1) ? file.substring(dotIndex + 1) : "";
	        
	}
	
	
	public CreateTempletedEntity createdTemplated(String token, String templateName, int isAllImages,
			List<MultipartFile> backdropImag, int numberPlate, MultipartFile numberPlateImage, MultipartFile brandImg,
			String brandAlignment, int isDefaultBackdrop, String blurrify) {
		List<String>  urls=new ArrayList<>();
		CreateTempletedEntity newTemplated=new CreateTempletedEntity();
		String token1=token.replace("Bearer ", "");
		String userName=jwtTokenUtil.extractUsername(token1);
		UserEntity userEntity=userRepository.findByUsername(userName);
		newTemplated.setUserId(userEntity.getId());
		newTemplated.setTemplateName(templateName);
		newTemplated.setTemplateType("CC");
		if(backdropImag.size()>1) {
			newTemplated.setIsAllImage(0);
		}
		else {
			newTemplated.setIsAllImage(1);
		}
		newTemplated.setNumlate(numberPlate);
		newTemplated.setGuideJson("'{\"BACKDROP\":{\"T_Y\":\"None\",\"M_Y\":\"None\",\"L_Y\":\"None\",\"SL_X\":\"None\",\"SR_X\":\"None\"},\"front_rear\":{\"T_Y\":\"None\",\"M_Y\":\"None\",\"L_Y\":\"None\",\"SL_X\":\"None\",\"SR_X\":\"None\"},\"SIDE\":{\"T_Y\":\"None\",\"M_Y\":\"None\",\"L_Y\":\"None\"}}'");
	    newTemplated.setTemplateCreatedAt(LocalDateTime.now());
	    newTemplated.setTemplateUpdatedAt(LocalDateTime.now());
	    userTemplatesRepository.save(newTemplated);
	    try {
	    	 urls=saveTemplateInS3(isAllImages,backdropImag,numberPlateImage,brandImg,newTemplated.getTemplateId(),userEntity.getId());
	    }
	    catch (Exception e) {
			// TODO: handle exception
		}
	    
		 if(isAllImages==1) {
			 newTemplated.setBackdropImg(urls.get(0));
			 newTemplated.setNumberPlateImg(urls.get(1));
			 newTemplated.setBackdropImg(urls.get(2));
			 newTemplated.setBrandAlignment(brandAlignment);
			 newTemplated.setTemplateUpdatedAt(LocalDateTime.now());
			 userTemplatesRepository.save(newTemplated);
			 }
		 else {
			 newTemplated.setBackdropImg(urls.get(0));
			 newTemplated.setForntRearImg(urls.get(1));
			 newTemplated.setSideImg(urls.get(2));
			 newTemplated.setNumberPlateImg(urls.get(3));
			 newTemplated.setBackdropImg(urls.get(4));
			
		 }
		 newTemplated.setBrandAlignment(brandAlignment);
		 newTemplated.setTemplateUpdatedAt(LocalDateTime.now());
		 userTemplatesRepository.save(newTemplated);
		
		
		return newTemplated;
	}
	private List<String>  saveTemplateInS3(int isAllImages, List<MultipartFile> backdropImag, MultipartFile numberPlateImage,
			MultipartFile brandImg, long templateId, long userId) {
		List<String> urls=new ArrayList<>();
		if(backdropImag.size()>1) {
			for(MultipartFile multipartFile:backdropImag) {
				String fileName=multipartFile.getOriginalFilename();
				String s3object="templates/" + userId + "/" + templateId + "/" + fileName;
				urls.add(s3object);
				
			}
		}
		else {
			String filename=backdropImag.get(0).getOriginalFilename();
			String s3object="templates/" + userId + "/" + templateId + "/" + filename;
			urls.add(s3object);
		}
		
		if(!numberPlateImage.isEmpty()&& numberPlateImage!=null) {
		String fileName=numberPlateImage.getOriginalFilename();
		String s3object="numberPlate/" + userId + "/" + templateId + "/" + fileName;
		urls.add(s3object);
		}
		else {
			urls.add(null);
		}
		
		if(!brandImg.isEmpty()&& brandImg!=null) {
			String fileName=brandImg.getOriginalFilename();
			String s3object="brandName/" + userId + "/" + templateId + "/" + fileName;
			urls.add(s3object);
			}
			else {
				urls.add(null);
			}
		// TODO Auto-generated method stub
		return urls;
	}


	public CreateTempletedEntity editTemplated(String token, String templateName, long templetId, int isAllImages,
			List<MultipartFile> backdropImag, int numberPlate, MultipartFile numberPlateImage, MultipartFile brandImg,
			String brandAlignment, int isDefaultBackdrop, String blurrify) {
		List<String> urls=new ArrayList<>();
		// TODO Auto-generated method stub
		CreateTempletedEntity modifidTempletedEntity=userTemplatesRepository.findById(templetId);
		String token1=token.replace("Bearer", "");
		String name=jwtTokenUtil.generateToken(token1);
		UserEntity userEntity=userRepository.findByUsername(name);
		long userid=userEntity.getId();
		if(userid!=modifidTempletedEntity.getUserId()) {
			return null;
		}
		modifidTempletedEntity.setTemplateName(templateName);
		if(backdropImag.size()>1) {
			modifidTempletedEntity.setIsAllImage(0);
		}
		else {
			modifidTempletedEntity.setIsAllImage(1);
		}
		modifidTempletedEntity.setNumlate(numberPlate);
		modifidTempletedEntity.setTemplateCreatedAt(LocalDateTime.now());
		modifidTempletedEntity.setTemplateUpdatedAt(LocalDateTime.now());
		userTemplatesRepository.save(modifidTempletedEntity);
		try {
	    	 urls=saveTemplateInS3(isAllImages,backdropImag,numberPlateImage,brandImg,modifidTempletedEntity.getTemplateId(),userEntity.getId());
	    }
	    catch (Exception e) {
			// TODO: handle exception
		}
		if(isAllImages==1) {
			 modifidTempletedEntity.setBackdropImg(urls.get(0));
			 modifidTempletedEntity.setNumberPlateImg(urls.get(1));
			 modifidTempletedEntity.setBackdropImg(urls.get(2));
			 modifidTempletedEntity.setBrandAlignment(brandAlignment);
			 modifidTempletedEntity.setTemplateUpdatedAt(LocalDateTime.now());
			 userTemplatesRepository.save(modifidTempletedEntity);
			 }
		 else {
			 modifidTempletedEntity.setBackdropImg(urls.get(0));
			 modifidTempletedEntity.setForntRearImg(urls.get(1));
			 modifidTempletedEntity.setSideImg(urls.get(2));
			 modifidTempletedEntity.setNumberPlateImg(urls.get(3));
			 modifidTempletedEntity.setBackdropImg(urls.get(4));
			
		 }
		 modifidTempletedEntity.setBrandAlignment(brandAlignment);
		 modifidTempletedEntity.setTemplateUpdatedAt(LocalDateTime.now());
		 userTemplatesRepository.save(modifidTempletedEntity);
		
		return modifidTempletedEntity;
	}


	public CreateTempletedEntity loadTemplated(String token) {
		String token1=token.replace("Bearer", "");
		String userName=jwtTokenUtil.generateToken(token1);
		UserEntity userEntity=userRepository.findByUsername(userName);
		long userId=userEntity.getId();
		CreateTempletedEntity createTempletedEntity=userTemplatesRepository.findById(userId);
		
		// TODO Auto-generated method stub
		return createTempletedEntity;
	}
	public List< CreateTempletedEntity> CountTemplatedSrvice(String token) {
		String token1=token.replace("Bearer", "");
		String userName=jwtTokenUtil.generateToken(token1);
		UserEntity userEntity=userRepository.findByUsername(userName);
		long userId=userEntity.getId();
		List< CreateTempletedEntity> createTempletedEntity=userTemplatesRepository.findAllById(userId);
		
		// TODO Auto-generated method stub
		return createTempletedEntity;
	}


	public String replaceBackDropImage(String token, String templateName, long templetId, int isAllImages,
			List<MultipartFile> backdropImag, int numberPlate, MultipartFile numberPlateImage, MultipartFile brandImg) {
		List<String> urls=new ArrayList<>();
		CreateTempletedEntity createTemplateReplace=userTemplatesRepository.findById(templetId);
		String token1=token.replace("Bearer ", "");
		String userName=jwtTokenUtil.generateToken(token1);
		UserEntity userEntity=userRepository.findByUsername(userName);
		long id=userEntity.getId();
		
		if(id!= createTemplateReplace.getUserId()) {
			return "false";
			
		}
		
		if(isAllImages==0) {
			String fileName=backdropImag.get(0).getOriginalFilename();
			String s3object="templates/" + id + "/" + templetId + "/" + fileName;
			urls.add(s3object);
			String fileNameFront1=backdropImag.get(1).getOriginalFilename();
			String s3object1="templates/" + id + "/" + templetId + "/" +fileNameFront1 ;
			urls.add(s3object1);
			String fileNameBack=backdropImag.get(2).getOriginalFilename();
			String s3object2="templates/" + id + "/" + templetId + "/" +fileNameBack ;
			urls.add(s3object2);
			createTemplateReplace.setBackdropImg(urls.get(0));
			createTemplateReplace.setForntRearImg(urls.get(1));
			createTemplateReplace.setSideImg(s3object2);
			createTemplateReplace.setTemplateUpdatedAt(LocalDateTime.now());
			createTemplateReplace.setIsAllImage(isAllImages);
			userTemplatesRepository.save(createTemplateReplace);
			return "True";
		}
		if(isAllImages==1) {
			String fileName=backdropImag.get(0).getOriginalFilename();
			String s3object="templates/" + id + "/" + templetId + "/" + fileName;
			urls.add(s3object);
			createTemplateReplace.setBackdropImg(urls.get(0));
			createTemplateReplace.setTemplateUpdatedAt(LocalDateTime.now());
			createTemplateReplace.setIsAllImage(isAllImages);
			userTemplatesRepository.save(createTemplateReplace);
			return "True";
		}
		return "false";
		
		
	}


	public String replaceBrandImage(String token, String templateName, long templetId, int isAllImages,
			List<MultipartFile> backdropImag, int numberPlate, MultipartFile numberPlateImage, MultipartFile brandImg) {
		List<String> urls=new ArrayList<>();
		CreateTempletedEntity createTemplateReplace=userTemplatesRepository.findById(templetId);
		String token1=token.replace("Bearer ", "");
		String userName=jwtTokenUtil.generateToken(token1);
		UserEntity userEntity=userRepository.findByUsername(userName);
		long id=userEntity.getId();
		
		if(id!= createTemplateReplace.getUserId()) {
			return "false";
			
		}
		String fileName=brandImg.getOriginalFilename();
		String s3object="templates/" + id + "/" + templetId + "/" + fileName;
		urls.add(s3object);
		createTemplateReplace.setBackdropImg(urls.get(0));
		createTemplateReplace.setTemplateUpdatedAt(LocalDateTime.now());
		createTemplateReplace.setIsAllImage(isAllImages);
		userTemplatesRepository.save(createTemplateReplace);
		return "True";
	}

   
	


	
	

}
