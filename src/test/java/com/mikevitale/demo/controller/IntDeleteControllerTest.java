package com.mikevitale.demo.controller;

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

@WebMvcTest(IntController.class)
public class IntDeleteControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void validIntegerPathVariable() throws Exception {
		int value = 5;
		MockHttpServletRequestBuilder request = givenARequestFor("/java/int", value);
		ResultActions actions = whenTheRequestIsMade(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().bytes("ID is valid".getBytes()));
	}

	@Test
	public void shoutsWhenIntegerPathVariableIsTooLow() throws Exception {
		int value = 4;
		final var request = givenARequestFor("/java/int", 4);
		final ResultActions resultActions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateIntDelete.id\",\n" +
		                     "            \"message\": \"A minimum value of 5 is required\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(resultActions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenIntegerPathVariableIsTooHigh() throws Exception {
		int value = 10000;
		final var request = givenARequestFor("/java/int", value);
		final ResultActions resultActions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateIntDelete.id\",\n" +
		                     "            \"message\": \"A maximum value of 9999 can be given\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(resultActions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenStringIsPassedToIntegerPathVariable() throws Exception {
		String value = "wegyrguywery";
		final var request = givenARequestFor("/java/int", value);
		final ResultActions resultActions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"id\",\n" +
		                     "            \"message\": \"Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \\\"wegyrguywery\\\"\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		final var content = MockMvcResultMatchers.content();
		thenExpect(resultActions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	private MockHttpServletRequestBuilder givenARequestFor(String url, int id) {
		return MockMvcRequestBuilders
				.delete(url + "/" + id);
	}

	private MockHttpServletRequestBuilder givenARequestFor(String url, String id) {
		return MockMvcRequestBuilders
				.delete(url + "/" + id);
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
