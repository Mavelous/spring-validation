package com.mikevitale.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(StringController.class)
public class StringPostControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void validStringPost() throws Exception {
		String value = "mike";
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string", value);
		ResultActions actions = whenTheRequestIsMade(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().bytes("Username is valid".getBytes()));
	}

	@Test
	public void shoutsWhenStringPostIsTooShort() throws Exception {
		String value = "a";
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string", value);
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPost.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		ContentResultMatchers content = MockMvcResultMatchers.content();

		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenStringPostIsTooLong() throws Exception {
		String value = "abcdefghijklmnop";
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string", value);
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPost.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		ContentResultMatchers content = MockMvcResultMatchers.content();

		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenStringPostDoesntMatchPattern() throws Exception {
		String value = "mike42";
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string", value);
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPost.username\",\n" +
		                     "            \"message\": \"Username Pattern Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		ContentResultMatchers content = MockMvcResultMatchers.content();

		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	@Test
	public void shoutsWhenStringPostIsTooLongAndDoesntMatchPattern() throws Exception {
		String value = "wpeurhgiouwerhgoiuwerhgo42";
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string", value);
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPost.username\",\n" +
		                     "            \"message\": \"Username Pattern Validation Message\"\n" +
		                     "        },\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPost.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        }\n" +
		                     "    ]\n" +
		                     "}";
		ContentResultMatchers content = MockMvcResultMatchers.content();

		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest(),
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response));
	}

	private MockHttpServletRequestBuilder givenARequestFor(String url, String name) throws JsonProcessingException {
		return MockMvcRequestBuilders
				.post(url)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(name);
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
