package com.dynamic.Quickbill.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Biller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer billerId;
	
	@Column
	private String email;
	
	@Column
	private String password;

	public Integer getBillerId() {
		return billerId;
	}

	public void setBillerId(Integer userId) {
		this.billerId = userId;
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
	
	
}
