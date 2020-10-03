package com.example.demo.model;

import java.io.Serializable;


public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String jwttoken;
	private User user;
	private String role;
	
	public JwtResponse(String jwttoken, User user, String role) {
		super();
		this.jwttoken = jwttoken;
		this.user = user;
		this.role = role;
	}
	public String getJwttoken() {
		return jwttoken;
	}
	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}