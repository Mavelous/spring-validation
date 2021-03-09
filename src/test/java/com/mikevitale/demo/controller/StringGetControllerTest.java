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
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StringController.class)
@AutoConfigureMockMvc
public class StringGetControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void validStringPathVariable() throws Exception {
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string/mike");
		ResultActions actions = whenTheRequestIsMade(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().bytes("Username is valid".getBytes()));
	}

	@Test
	public void shoutsWhenStringPathVariableIsTooShort() throws Exception {
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string/a");
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPathVariable.username\",\n" +
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
	public void shoutsWhenStringPathVariableIsTooLong() throws Exception {
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string/wpeurhgiouwerhgoiuwerhgo");
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPathVariable.username\",\n" +
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
	public void shoutsWhenStringPathVariableDoesntMatchPattern() throws Exception {
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string/mike42");
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPathVariable.username\",\n" +
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
	public void shoutsWhenStringPathVariableIsTooLongAndDoesntMatchPattern() throws Exception {
		MockHttpServletRequestBuilder request = givenARequestFor("/java/string/wpeurhgiouwerhgoiuwerhgo42");
		ResultActions actions = whenTheRequestIsMade(request);
		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPathVariable.username\",\n" +
		                     "            \"message\": \"Username Pattern Validation Message\"\n" +
		                     "        },\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"validateStringPathVariable.username\",\n" +
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

	private MockHttpServletRequestBuilder givenARequestFor(String url) {
		return MockMvcRequestBuilders.get(url)
		                             .characterEncoding("UTF-8");
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
