package com.mikevitale.demo.controller;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.mikevitale.demo.DemoApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = DemoController.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerTest {
	@Autowired
	DemoController controller;

	@LocalServerPort
	private int port;

//	@Autowired
//	MockMvc mvc;

	Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void goodRequestString() throws Exception {
		RestTemplate rt = new RestTemplate();

		String result = rt.getForObject("http://localhost:" + port + "/java/string/woehifghoiuwehruoghouiwerhoiug", String.class);

		System.out.printf("Result = [%s]%n", result);
//		mvc.perform(MockMvcRequestBuilders.get("/java/string/mike")
//		).andExpect(MockMvcResultMatchers.status().isOk())
//		   .andExpect(MockMvcResultMatchers.content().string("Username is valid"));
	}

//	@Test
//	@Ignore
//	public void tooLongRequestString() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.get("/java/string/owehrguioweruhioghuoiwerhoiu")
//		).andExpect(MockMvcResultMatchers.status().isOk())
//		   .andExpect(MockMvcResultMatchers.content().string("owehrguioweruhioghuoiwerhoiu"));
//	}
//
//	@Test
//	public void goodRequestObject() throws Exception {
//		String username = "{\"username\": \"mike\"}";
//		mvc.perform(MockMvcRequestBuilders
//				.post("/java/object")
//				.content(username)
//		)
//		   .andExpect(MockMvcResultMatchers.status().isOk())
//		   .andExpect(MockMvcResultMatchers.content().string("Username is valid"));
//	}
//
//	@Test
//	public void tooLongRequestObject() throws Exception {
//		String username = "{\"username\": \"abcdefghijklmnop\"}";
//		mvc.perform(MockMvcRequestBuilders
//				.post("/java/object")
//				.content(username)
//		)
//		   .andExpect(MockMvcResultMatchers.status().isOk())
//		   .andExpect(MockMvcResultMatchers.content().string("Username is valid"));
//	}



//	private void validate_assertErrorsWith(String message, OwnerTransactionQueryParams queryParameters) {
//		Set<ConstraintViolation<?>> validate = validator.validate(queryParameters);
//		assertThat(validate.size()).as("Did not validate").isEqualTo(1);
//		assertThat(validate.stream().findFirst().get().getMessage()).isEqualTo(message);
//	}

}
