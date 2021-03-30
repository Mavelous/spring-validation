package com.mikevitale.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikevitale.demo.model.JavaUsername;

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

@WebMvcTest(SimpleObjectController.class)
public class SimpleObjectPostControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void validObject() throws Exception {
		JavaUsername validUsername = new JavaUsername("mike");
		final var request = givenARequestForUsername(validUsername);
		final ResultActions actions = whenTheRequestIsMade(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void shoutsWhenObjectNameIsTooLong() throws Exception {
		JavaUsername username = new JavaUsername("wpeurhgiouwerhgoiuwerhgo");
		final var request = givenARequestForUsername(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username\",\n" +
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
		final var request = givenARequestForUsername(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username\",\n" +
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
		final var request = givenARequestForUsername(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        },\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username\",\n" +
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
		final var request = givenARequestForUsername(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"username\",\n" +
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

	private MockHttpServletRequestBuilder givenARequestForUsername(JavaUsername username) throws JsonProcessingException {
		var requestBody = objectMapper.writeValueAsString(username);
		return givenARequestFor(requestBody);
	}

	private MockHttpServletRequestBuilder givenARequestFor(String requestBody) {
		return MockMvcRequestBuilders.post("/java/object")
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
