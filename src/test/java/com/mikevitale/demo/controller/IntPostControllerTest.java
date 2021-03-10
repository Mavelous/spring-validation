package com.mikevitale.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IntController.class)
@AutoConfigureMockMvc
public class IntPostControllerTest {
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
		                     "            \"fieldName\": \"validateIntPost.id\",\n" +
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
		                     "            \"fieldName\": \"validateIntPost.id\",\n" +
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
		                     "            \"fieldName\": \"Unrecognized token 'wegyrguywery': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\\n at [Source: (PushbackInputStream); line: 1, column: 13]\",\n" +
		                     "            \"message\": \"JSON parse error: Unrecognized token 'wegyrguywery': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false'); nested exception is com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'wegyrguywery': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\\n at [Source: (PushbackInputStream); line: 1, column: 13]\"" +
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
				.post(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(String.valueOf(id));
	}

	private MockHttpServletRequestBuilder givenARequestFor(String url, String id) {
		return MockMvcRequestBuilders
				.post(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(id);
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
