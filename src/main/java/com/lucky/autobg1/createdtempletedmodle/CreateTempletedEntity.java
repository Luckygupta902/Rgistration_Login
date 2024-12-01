package com.lucky.autobg1.createdtempletedmodle;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_templatesEntity")
public class CreateTempletedEntity {
	
	
	@Id
	@Column(name="templete_id")
	private long templateId;
	
	@Column(name ="template_name")
	private String templateName; 
	
	@Column(name ="template_type")
	private String templateType; 
	
	@Column(name="is_all_image")
	private int isAllImage;
	
	@Column(name="backdrop_img")
	private String backdropImg;
	
	@Column(name="side_img")
	private String sideImg;
	
	@Column(name="fornt_rear_img")
	private String forntRearImg;
	
	@Column(name="number_plate_img")
	private String numberPlateImg;
	
	@Column(name="num_plate")
	private int numlate;
	
	@Column(name="guide_json")
	private String guideJson;
	
	@Column(name="brand_img")
	private String brandImg;
	
	@Column(name="brand_aligment")
	private String brandAlignment;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="template_created_at")
	private LocalDateTime templateCreatedAt;
	
	@Column(name="template_updated_at")
	private LocalDateTime templateUpdatedAt;

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public int getIsAllImage() {
		return isAllImage;
	}

	public void setIsAllImage(int isAllImage) {
		this.isAllImage = isAllImage;
	}

	public String getBackdropImg() {
		return backdropImg;
	}

	public void setBackdropImg(String backdropImg) {
		this.backdropImg = backdropImg;
	}

	public String getSideImg() {
		return sideImg;
	}

	public void setSideImg(String sideImg) {
		this.sideImg = sideImg;
	}

	public String getForntRearImg() {
		return forntRearImg;
	}

	public void setForntRearImg(String forntRearImg) {
		this.forntRearImg = forntRearImg;
	}

	public String getNumberPlateImg() {
		return numberPlateImg;
	}

	public void setNumberPlateImg(String numberPlateImg) {
		this.numberPlateImg = numberPlateImg;
	}

	public int getNumlate() {
		return numlate;
	}

	public void setNumlate(int numlate) {
		this.numlate = numlate;
	}

	public String getGuideJson() {
		return guideJson;
	}

	public void setGuideJson(String guideJson) {
		this.guideJson = guideJson;
	}

	public String getBrandImg() {
		return brandImg;
	}

	public void setBrandImg(String brandImg) {
		this.brandImg = brandImg;
	}

	public String getBrandAlignment() {
		return brandAlignment;
	}

	public void setBrandAlignment(String brandAlignment) {
		this.brandAlignment = brandAlignment;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public LocalDateTime getTemplateCreatedAt() {
		return templateCreatedAt;
	}

	public void setTemplateCreatedAt(LocalDateTime templateCreatedAt) {
		this.templateCreatedAt = templateCreatedAt;
	}

	public LocalDateTime getTemplateUpdatedAt() {
		return templateUpdatedAt;
	}

	public void setTemplateUpdatedAt(LocalDateTime templateUpdatedAt) {
		this.templateUpdatedAt = templateUpdatedAt;
	}

	
	

}
