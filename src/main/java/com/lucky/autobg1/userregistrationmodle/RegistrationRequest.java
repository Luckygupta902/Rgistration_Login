package com.lucky.autobg1.userregistrationmodle;

public class RegistrationRequest {
	private String username;
	private String vertical;
	private String whatsapp;
	private String email;
	private String cc;
	private String password;
	public RegistrationRequest() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "RegistrationRequest [username=" + username + ", vertical=" + vertical + ", whatsapp=" + whatsapp
				+ ", email=" + email + ", cc=" + cc + ", password=" + password + "]";
	}
	


 
}


