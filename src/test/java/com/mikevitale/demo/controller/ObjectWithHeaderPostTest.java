package com.mikevitale.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikevitale.demo.model.JavaPerson;
import com.mikevitale.demo.model.JavaPhoneNumber;
import com.mikevitale.demo.model.JavaUsername;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ObjectWithHeaderController.class)
@AutoConfigureMockMvc
public class ObjectWithHeaderPostTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	private JavaPerson person;

	private static final JavaPhoneNumber VALID_PHONE_NUMBER = new JavaPhoneNumber("11234567890");
	private static final JavaUsername VALID_USERNAME = new JavaUsername("mike");
	private static final String VALID_UUID = "1234abcd-ef56-78ab-90cd-ef1234abcd56";

	@BeforeEach
	void setUp() {
		person = new JavaPerson();
	}

	@Test
	public void validObject() throws Exception {
		person.setUsername(VALID_USERNAME);
		person.setPhoneNumber(VALID_PHONE_NUMBER);

		final var request = givenARequestFor(person);

		final ResultActions actions = whenTheRequestIsMade(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().contentType(APPLICATION_JSON));
	}

	private MockHttpServletRequestBuilder givenARequestFor(JavaPerson person) throws JsonProcessingException {
		var requestBody = objectMapper.writeValueAsString(person);
		System.out.printf("****** ==> [%s]%n", requestBody);
		return givenARequestFor(requestBody);
	}

	private MockHttpServletRequestBuilder givenARequestFor(String requestBody) {
		return MockMvcRequestBuilders.post("/java/header")
		                             .contentType(APPLICATION_JSON)
		                             .characterEncoding("UTF-8")
		                             .header("uuid", VALID_UUID)
		                             .content(requestBody);
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
