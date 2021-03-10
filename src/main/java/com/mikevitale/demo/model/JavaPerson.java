package com.mikevitale.demo.model;

import javax.validation.Valid;

public class JavaPerson {
	@Valid
	private JavaUsername username = new JavaUsername();

	@Valid
	private JavaPhoneNumber phoneNumber = new JavaPhoneNumber();

	public void setUsername(String username) {
		this.username.setUsername(username);;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.setPhoneNumber(phoneNumber);
	}
}
