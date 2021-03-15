package com.mikevitale.demo.model;

import javax.validation.Valid;

public class JavaPerson {
	@Valid
	public JavaUsername username = new JavaUsername();

	@Valid
	public JavaPhoneNumber phoneNumber = new JavaPhoneNumber();

	public JavaPerson() {}

	public void setUsername(JavaUsername username) {
		this.username = username;
	}

	public void setPhoneNumber(JavaPhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "JavaPerson{" +
		       "username=" + username +
		       ", phoneNumber=" + phoneNumber +
		       '}';
	}
}
