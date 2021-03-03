package com.mikevitale.demo.controller;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class BrettTest {
	Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	@Ignore
	public void goodRequestString() throws Exception {
		RestTemplate rt = new RestTemplate();

		String result = rt.getForObject("http://localhost:8080/java/string/woehifghoiuwehruoghouiwerhoiug", String.class);

		System.out.printf("Result = [%s]%n", result);
//		mvc.perform(MockMvcRequestBuilders.get("/java/string/mike")
//		).andExpect(MockMvcResultMatchers.status().isOk())
//		   .andExpect(MockMvcResultMatchers.content().string("Username is valid"));
	}

}
