package com.spring.boot.application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_details", schema = "employee_management")
public class UserDetails {
	
	@Column(nullable = false)
	private String firstname;
	private String lastname;
	@Id
	private String email;
	@Column(nullable = false)
	private String password;
	
	
	
	public UserDetails() {
		super();
	}

	public UserDetails(String firstname, String lastname, String email, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "userDetails [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + "]";
	}
}
