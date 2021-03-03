package com.mikevitale.demo.controller;

import javax.validation.Validator;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@WebMvcTest(DemoController.class)
@AutoConfigureMockMvc
//@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerTest {
	@Autowired
	DemoController controller;

//	@LocalServerPort
//	private int port;

	@Autowired
	private MockMvc mvc;

	Validator validator;

//	@Before
//	public void setUp() {
//		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//		validator = validatorFactory.getValidator();
//	}

	@Test
	public void validUsername() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/java/string/mike")
				.characterEncoding("UTF-8");
		ResultActions actions = mvc.perform(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().bytes("Username is valid".getBytes()));
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}

	@Test
	@Ignore
	public void goodRequestString() throws Exception {
		RestTemplate rt = new RestTemplate();

//		String result = rt.getForObject("http://localhost:" + port + "/java/string/woehifghoiuwehruoghouiwerhoiug", String.class);

//		System.out.printf("Result = [%s]%n", result);
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
