package com.mikevitale.demo.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class JavaUsername {
	@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
	@Size(min = 2, max = 15, message = "Username Size Validation Message")
	public String username;

	public JavaUsername() {}

	public JavaUsername(String username) {
		setUsername(username);
	}

	public void setUsername(String username) {
		System.out.printf("Username = [%s]%n", username);
		this.username = username;
	}

	@Override
	public String toString() {
		return "JavaUsername{" +
		       "username='" + username + '\'' +
		       '}';
	}
}
