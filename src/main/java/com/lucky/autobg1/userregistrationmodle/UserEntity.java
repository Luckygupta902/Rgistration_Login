package com.lucky.autobg1.userregistrationmodle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name="user")
public class UserEntity {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	
	private String username;


	private String vertical;


	private String whatsapp;


	private String email;


	private String countryCode;


	private Integer currentCredits;


	private Integer totalCredits;


	private String activeCode;


	private String isPlanActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public String getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getCurrentCredits() {
		return currentCredits;
	}

	public void setCurrentCredits(Integer currentCredits) {
		this.currentCredits = currentCredits;
	}

	public Integer getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(Integer totalCredits) {
		this.totalCredits = totalCredits;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getIsPlanActive() {
		return isPlanActive;
	}

	public void setIsPlanActive(String isPlanActive) {
		this.isPlanActive = isPlanActive;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "status")

	private String status;

	private String password;

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", vertical=" + vertical + ", whatsapp=" + whatsapp
				+ ", email=" + email + ", countryCode=" + countryCode + ", currentCredits=" + currentCredits
				+ ", totalCredits=" + totalCredits + ", activeCode=" + activeCode + ", isPlanActive=" + isPlanActive
				+ ", status=" + status + ", password=" + password + "]";
	}
	

	
	

}
