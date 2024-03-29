package com.mikevitale.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikevitale.demo.model.JavaPerson;
import com.mikevitale.demo.model.JavaPhoneNumber;
import com.mikevitale.demo.model.JavaUsername;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ComplexObjectController.class)
public class ComplexObjectPostControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	private JavaPerson person;
	private static final JavaPhoneNumber VALID_PHONE_NUMBER = new JavaPhoneNumber("11234567890");
	private static final JavaUsername VALID_USERNAME = new JavaUsername("mike");

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
				MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void shoutsWhenObjectNameIsTooLong() throws Exception {
		JavaUsername username = new JavaUsername("wpeurhgiouwerhgoiuwerhgo");
		person.setUsername(username);
		person.setPhoneNumber(VALID_PHONE_NUMBER);
		final var request = givenARequestFor(person);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenObjectNameDoesntMatchPattern() throws Exception {
		JavaUsername username = new JavaUsername("1234");
		person.setUsername(username);
		person.setPhoneNumber(VALID_PHONE_NUMBER);
		final var request = givenARequestFor(person);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username.username\",\n" +
		                     "            \"message\": \"Username Pattern Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenObjectNameIsTooShortAndDoesntMatchPattern() throws Exception {
		JavaUsername username = new JavaUsername("1");
		person.setUsername(username);
		person.setPhoneNumber(VALID_PHONE_NUMBER);
		final var request = givenARequestFor(person);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        },\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username.username\",\n" +
		                     "            \"message\": \"Username Pattern Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenObjectNameIsTooShort() throws Exception {
		JavaUsername username = new JavaUsername("a");
		person.setUsername(username);
		person.setPhoneNumber(VALID_PHONE_NUMBER);
		final var request = givenARequestFor(person);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	private MockHttpServletRequestBuilder givenARequestFor(JavaPerson person) throws JsonProcessingException {
		var requestBody = objectMapper.writeValueAsString(person);
		System.out.printf("****** ==> [%s]%n", requestBody);
		return givenARequestFor(requestBody);
	}

	private MockHttpServletRequestBuilder givenARequestFor(String requestBody) {
		return MockMvcRequestBuilders.post("/java/complex/object")
		                             .contentType(MediaType.APPLICATION_JSON)
		                             .characterEncoding("UTF-8")
		                             .content(requestBody);
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
