package com.mikevitale.demo.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class JavaPhoneNumber {
	@Pattern(regexp = "^1[0-9]{3}[0-9]{3}[0-9]{4}$", message = "Phone Number Pattern Validation Message")
	@Size(min = 11, max = 11, message = "Phone Number Size Validation Message")
	public String phoneNumber;

	public JavaPhoneNumber() {}

	public JavaPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "JavaPhoneNumber{" +
		       "phoneNumber='" + phoneNumber + '\'' +
		       '}';
	}
}
