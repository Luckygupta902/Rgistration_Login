package com.lucky.autobg1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.lucky.autobg1.Respons.CreatedTempletedResponse;
import com.lucky.autobg1.createdtempletedmodle.CreateTempletedEntity;
import com.lucky.autobg1.templetedservices.CreatedTemplatedSrvice;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class TempletedController {
	
	private static final Logger log = LoggerFactory.getLogger(TempletedController.class);

	@Autowired
	CreatedTemplatedSrvice createdTemplatedSrvice;

	@PostMapping("/createdtempleted")
	ResponseEntity<CreatedTempletedResponse>  generateTemplate(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam(required = true) String templateName,
			@RequestParam(required = false) int isAllImages ,@RequestParam(required = true)List<MultipartFile> backdropImag,
			@RequestParam(required = true)int numberPlate,@RequestParam(required = false)MultipartFile numberPlateImage,
			@RequestParam(required = false)MultipartFile brandImg,@RequestParam(required = false)String brandAlignment,@RequestParam(required = false) int isDefaultBackdrop,@RequestParam(required = false) String blurrify){
		   CreatedTempletedResponse createdTempletedResponse=new CreatedTempletedResponse();
		log.info("lucky is good");
		   if(isAllImages==0) {
			   if(backdropImag.size()<3) {
				   createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("One or More image is Requried");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
				   
				   
			   }
			   else {
				  String backdropExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(0));
				  String frontExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(1));
				  String rearExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(2));
				  if(!backdropExt.equalsIgnoreCase("jpg") || !backdropExt.equalsIgnoreCase("jpeg")||
						  !frontExt.equalsIgnoreCase("jpg")||!frontExt.equalsIgnoreCase("jpeg")||
						  !rearExt.equalsIgnoreCase("jpg")|| !rearExt.equalsIgnoreCase("jpeg")){
					  createdTempletedResponse.setCode(0);
					  createdTempletedResponse.setMessage("jpg and jpeg format is allow backdrop/fornt/rarer ");
					  createdTempletedResponse.setTempletId(0);
					  return ResponseEntity.ok(createdTempletedResponse);
					  
					  
				  }
			   }
		   }
		   else {
			   if(backdropImag.isEmpty()|| backdropImag==null) {
				   createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("backdrop image is empty and null is not allowed");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
			   }
			   String backdropExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(0));
			   if(!backdropExt.equalsIgnoreCase("jpg")|| !backdropExt.equalsIgnoreCase("jpeg")) {
				   createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("only jpeg jpg formated is allow backdrop");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
				   
			   }
			   
		   }
		   if(numberPlate==2 || numberPlateImage.isEmpty()|| numberPlateImage==null) {
			   createdTempletedResponse.setCode(0);
			   createdTempletedResponse.setMessage("NumberPlate null is not allowed");
			   createdTempletedResponse.setTempletId(0);
			   return ResponseEntity.ok(createdTempletedResponse);
		   }
		   if(!numberPlateImage.isEmpty()&& !(numberPlateImage==null)) {
			  String numberPlatExt= createdTemplatedSrvice.checkImgExtension(numberPlateImage);
			  if(!numberPlatExt.equalsIgnoreCase("jpg")||!numberPlatExt.equalsIgnoreCase("jpeg")) {
				  createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("only jpeg jpg formated is allow in allowed number formated");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
			  }
			  
		   }
		   if(brandAlignment.isEmpty() || brandImg.isEmpty()) {
			   createdTempletedResponse.setCode(0);
			   createdTempletedResponse.setMessage("NumberPlate null is not allowed");
			   createdTempletedResponse.setTempletId(0);
			   return ResponseEntity.ok(createdTempletedResponse);
			   
		   }
		   if(!brandAlignment.isEmpty() || !brandImg.isEmpty()) {
			   String brandImgExt=createdTemplatedSrvice.checkImgExtension(brandImg);
			   if(!brandImgExt.equalsIgnoreCase("jpg")||!brandImgExt.equalsIgnoreCase("jpeg")) {
					  createdTempletedResponse.setCode(0);
					   createdTempletedResponse.setMessage("only jpeg jpg formated is allow brandaligment");
					   createdTempletedResponse.setTempletId(0);
					   return ResponseEntity.ok(createdTempletedResponse);
				  }
			   
			   
		   }
		   if(templateName == null || templateName.isEmpty() || backdropImag == null || backdropImag.isEmpty()||backdropImag.get(0).getOriginalFilename().isEmpty()) {
				createdTempletedResponse.setCode(0);
				createdTempletedResponse.setMessage("One Or More Parameters Missing");
				createdTempletedResponse.setTempletId(0);
				return ResponseEntity.ok(createdTempletedResponse);
			}
		 
		   
		  CreateTempletedEntity createdTemplate= createdTemplatedSrvice.createdTemplated(token, templateName, isAllImages, backdropImag, numberPlate, numberPlateImage, brandImg, brandAlignment,isDefaultBackdrop, blurrify);
		if(createdTemplate!=null) {
		
			createdTempletedResponse.setCode(1);
			createdTempletedResponse.setMessage("Templated Created SucessFully");
			createdTempletedResponse.setTempletId(createdTemplate.getTemplateId());
			return ResponseEntity.ok(createdTempletedResponse);
		}
		else {
			createdTempletedResponse.setCode(0);
			createdTempletedResponse.setMessage("Templated created failed");
			createdTempletedResponse.setTempletId(0);
			return ResponseEntity.ok(createdTempletedResponse);
		}
	
	}
	

	@PostMapping("/edittempleted")
	ResponseEntity<CreatedTempletedResponse>  eidtTemplate(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam(required = true) String templateName,@RequestParam long templetId,
			@RequestParam(required = false) int isAllImages ,@RequestParam(required = true)List<MultipartFile> backdropImag,
			@RequestParam(required = true)int numberPlate,@RequestParam(required = false)MultipartFile numberPlateImage,
			@RequestParam(required = false)MultipartFile brandImg,@RequestParam(required = false)String brandAlignment,@RequestParam(required = false) int isDefaultBackdrop,@RequestParam(required = false) String blurrify){
		   CreatedTempletedResponse createdTempletedResponse=new CreatedTempletedResponse();
		
		   if(isAllImages==0) {
			   if(backdropImag.size()<3) {
				   createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("One or More image is Requried");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
				   
				   
			   }
			   else {
				  String backdropExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(0));
				  String frontExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(1));
				  String rearExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(2));
				  if(!backdropExt.equalsIgnoreCase("jpg") || !backdropExt.equalsIgnoreCase("jpeg")||
						  !frontExt.equalsIgnoreCase("jpg")||!frontExt.equalsIgnoreCase("jpeg")||
						  !rearExt.equalsIgnoreCase("jpg")|| !rearExt.equalsIgnoreCase("jpeg")){
					  createdTempletedResponse.setCode(0);
					  createdTempletedResponse.setMessage("jpg and jpeg format is allow backdrop/fornt/rarer ");
					  createdTempletedResponse.setTempletId(0);
					  return ResponseEntity.ok(createdTempletedResponse);
					  
					  
				  }
			   }
		   }
		   else {
			   if(backdropImag.isEmpty()|| backdropImag==null) {
				   createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("backdrop image is empty and null is not allowed");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
			   }
			   String backdropExt= createdTemplatedSrvice.checkImgExtension(backdropImag.get(0));
			   if(!backdropExt.equalsIgnoreCase("jpg")|| !backdropExt.equalsIgnoreCase("jpeg")) {
				   createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("only jpeg jpg formated is allow backdrop");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
				   
			   }
			   
		   }
		   if(numberPlate==2 || numberPlateImage.isEmpty()|| numberPlateImage==null) {
			   createdTempletedResponse.setCode(0);
			   createdTempletedResponse.setMessage("NumberPlate null is not allowed");
			   createdTempletedResponse.setTempletId(0);
			   return ResponseEntity.ok(createdTempletedResponse);
		   }
		   if(!numberPlateImage.isEmpty()&& !(numberPlateImage==null)) {
			  String numberPlatExt= createdTemplatedSrvice.checkImgExtension(numberPlateImage);
			  if(!numberPlatExt.equalsIgnoreCase("jpg")||!numberPlatExt.equalsIgnoreCase("jpeg")) {
				  createdTempletedResponse.setCode(0);
				   createdTempletedResponse.setMessage("only jpeg jpg formated is allow in allowed number formated");
				   createdTempletedResponse.setTempletId(0);
				   return ResponseEntity.ok(createdTempletedResponse);
			  }
			  
		   }
		   if(brandAlignment.isEmpty() || brandImg.isEmpty()) {
			   createdTempletedResponse.setCode(0);
			   createdTempletedResponse.setMessage("NumberPlate null is not allowed");
			   createdTempletedResponse.setTempletId(0);
			   return ResponseEntity.ok(createdTempletedResponse);
			   
		   }
		   if(!brandAlignment.isEmpty() || !brandImg.isEmpty()) {
			   String brandImgExt=createdTemplatedSrvice.checkImgExtension(brandImg);
			   if(!brandImgExt.equalsIgnoreCase("jpg")||!brandImgExt.equalsIgnoreCase("jpeg")) {
					  createdTempletedResponse.setCode(0);
					   createdTempletedResponse.setMessage("only jpeg jpg formated is allow brandaligment");
					   createdTempletedResponse.setTempletId(0);
					   return ResponseEntity.ok(createdTempletedResponse);
				  }
			   
			   
		   }
		   if(templateName == null || templateName.isEmpty() || backdropImag == null || backdropImag.isEmpty()||backdropImag.get(0).getOriginalFilename().isEmpty()) {
				createdTempletedResponse.setCode(0);
				createdTempletedResponse.setMessage("One Or More Parameters Missing");
				createdTempletedResponse.setTempletId(0);
				return ResponseEntity.ok(createdTempletedResponse);
			}
		 
		   
		  CreateTempletedEntity createdTemplate= createdTemplatedSrvice.editTemplated(token, templateName, templetId,isAllImages, backdropImag, numberPlate, numberPlateImage, brandImg, brandAlignment,isDefaultBackdrop, blurrify);
		if(createdTemplate!=null) {
		
			createdTempletedResponse.setCode(1);
			createdTempletedResponse.setMessage("Templated is updated");
			createdTempletedResponse.setTempletId(createdTemplate.getTemplateId());
			return ResponseEntity.ok(createdTempletedResponse);
		}
		else {
			createdTempletedResponse.setCode(0);
			createdTempletedResponse.setMessage("Templated cannot able to updated");
			createdTempletedResponse.setTempletId(0);
			return ResponseEntity.ok(createdTempletedResponse);
		}
	
	}
	@GetMapping("/loadtemplated")
	ResponseEntity<CreatedTempletedResponse> loadTemplates(@RequestHeader("Authorization") String token){
		CreatedTempletedResponse createdTempletedResponse=new CreatedTempletedResponse();
		CreateTempletedEntity createTempletedEntity=createdTemplatedSrvice.loadTemplated(token);
		if(createTempletedEntity==null) {
			createdTempletedResponse.setCode(0);
			createdTempletedResponse.setMessage("No Templated is present");
			createdTempletedResponse.setTempletId(createTempletedEntity.getTemplateId());
			return ResponseEntity.ok(createdTempletedResponse);
		}
		else {
			createdTempletedResponse.setCode(1);
			createdTempletedResponse.setMessage(" Templated is present");
			createdTempletedResponse.setTempletId(createTempletedEntity.getTemplateId());
			return ResponseEntity.ok(createdTempletedResponse);
		}
		}
	@GetMapping("/counttemplated")
	ResponseEntity<CreatedTempletedResponse> counttemplated(@RequestHeader("Authorization") String token){
		CreatedTempletedResponse createdTempletedResponse=new CreatedTempletedResponse();
		List< CreateTempletedEntity> createTempletedEntity=createdTemplatedSrvice.CountTemplatedSrvice(token);
		if(createTempletedEntity==null) {
			createdTempletedResponse.setCode(0);
			createdTempletedResponse.setMessage("No Templated is present");
			createdTempletedResponse.setTempletId(createTempletedEntity.get(0).getTemplateId());
			return ResponseEntity.ok(createdTempletedResponse);
		}
		else {
			createdTempletedResponse.setCode(1);
			createdTempletedResponse.setMessage(" Total is Count");
			createdTempletedResponse.setTempletId(createTempletedEntity.size());
			return ResponseEntity.ok(createdTempletedResponse);
		}
		
		
		
	}
	@PostMapping("/replace/backdropimage")
	ResponseEntity<Map<String, String>> replaceTemplate(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam(required = true) String templateName,@RequestParam long templetId,
			@RequestParam(required = false) int isAllImages ,@RequestParam(required = true)List<MultipartFile> backdropImag,
			@RequestParam(required = true)int numberPlate,@RequestParam(required = false)MultipartFile numberPlateImage,
			@RequestParam(required = false)MultipartFile brandImg,@RequestParam(required = false)String brandAlignment,@RequestParam(required = false) int isDefaultBackdrop,@RequestParam(required = false) String blurrify){
		  		Map<String, String> map= new HashMap<>(); 
		if(isAllImages==0) {
			if(backdropImag.isEmpty()||numberPlateImage.isEmpty()|| brandImg.isEmpty()||brandAlignment.isEmpty() ) {
				map.put("code", "0");
				map.put("message", "One more paramater is messing");
				}
			String backDrop=createdTemplatedSrvice.checkImgExtension(backdropImag.get(0));
			String front=createdTemplatedSrvice.checkImgExtension(backdropImag.get(1));
			String rarer=createdTemplatedSrvice.checkImgExtension(backdropImag.get(2));
			if(!backDrop.equalsIgnoreCase("jpg")|| !front.equalsIgnoreCase("jpg")||
					!rarer.equalsIgnoreCase("jpg")|| !backDrop.equalsIgnoreCase("jpge")|| 
					!front.equalsIgnoreCase("jpge")|| !rarer.equalsIgnoreCase("jpge")) {
				
				map.put("code", "0");
				map.put("message", "only jpge and jpg formated");
			}
				
			
		}
		else {
			if(backdropImag.get(0).isEmpty()) {
				map.put("code", "0");
				map.put("message", "One more paramater is messing");
			}
			String backDrop=createdTemplatedSrvice.checkImgExtension(backdropImag.get(0));
			if(!backDrop.equalsIgnoreCase("jpg")|| !backDrop.equalsIgnoreCase("jpge")) {
				map.put("code", "0");
				map.put("message", "only jpge and jpg formated");
			}
		}
		 String status=createdTemplatedSrvice.replaceBackDropImage(token, templateName, templetId,isAllImages, backdropImag, numberPlate, numberPlateImage, brandImg);
		
		if(status.equals("True")) {
			map.put("code", "1");
			map.put("message", "Templated is replaced SucessFully");
		}
		else {
			map.put("code", "0");
			map.put("message", "Templated  has not been replaced SucessFully");
		}
		
		return ResponseEntity.ok(map);
		
		
	}
	
	ResponseEntity<Map<String, String>> replaceTemplateBrandImag(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam(required = true) String templateName,@RequestParam long templetId,
			@RequestParam(required = false) int isAllImages ,@RequestParam(required = true)List<MultipartFile> backdropImag,
			@RequestParam(required = true)int numberPlate,@RequestParam(required = false)MultipartFile numberPlateImage,
			@RequestParam(required = false)MultipartFile brandImg,@RequestParam(required = false)String brandAlignment,@RequestParam(required = false) int isDefaultBackdrop,@RequestParam(required = false) String blurrify){
		Map<String,String> map= new HashMap<>();   
		if(brandImg.isEmpty()) {
			map.put("code","0");
			map.put("message", "BrandImage is empty");
			   
		   }
		String brandImage=createdTemplatedSrvice.checkImgExtension(brandImg);
		if(!brandImage.equalsIgnoreCase("jpge")|| brandImage.equalsIgnoreCase("jpg")) {
			map.put("code","0");
			map.put("message", "Only jpge and jpg formated is allowaded");
		}
		 String status=createdTemplatedSrvice.replaceBrandImage(token, templateName, templetId,isAllImages, backdropImag, numberPlate, numberPlateImage, brandImg);
		 if(status.equals("True")) {
				map.put("code", "1");
				map.put("message", "Templated is replaced SucessFully");
			}
			else {
				map.put("code", "0");
				map.put("message", "Templated  has not been replaced SucessFully");
			}
		 return  ResponseEntity.ok(map);
	
}}
