package com.mikevitale.demo.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class JavaUsername {
	@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
	@Size(min = 1, max = 15, message = "Username Size Validation Message")
	@Valid
	public String username;

	public void setUsername(String username) {
		System.out.printf("Username = [%s]%n", username);
		this.username = username;
	}
}
