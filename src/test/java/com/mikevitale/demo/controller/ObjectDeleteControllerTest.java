package com.mikevitale.demo.controller;

import com.mikevitale.demo.model.JavaUsername;

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
@WebMvcTest(ObjectController.class)
@AutoConfigureMockMvc
public class ObjectDeleteControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void validObject() throws Exception {
		JavaUsername validUsername = new JavaUsername("mike");
		final var request = givenARequestFor(validUsername);
		final ResultActions actions = whenTheRequestIsMade(request);
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void shoutsWhenObjectNameIsTooLong() throws Exception {
		JavaUsername username = new JavaUsername("wpeurhgiouwerhgoiuwerhgo");
		final var request = givenARequestFor(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"deleteUsernameAsObject.username.username\",\n" +
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
		final var request = givenARequestFor(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"deleteUsernameAsObject.username.username\",\n" +
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
		final var request = givenARequestFor(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"deleteUsernameAsObject.username.username\",\n" +
		                     "            \"message\": \"Username Size Validation Message\"\n" +
		                     "        },\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"deleteUsernameAsObject.username.username\",\n" +
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
		final var request = givenARequestFor(username);
		final ResultActions actions = whenTheRequestIsMade(request);

		final var response = "{\n" +
		                     "    \"validationErrors\": [\n" +
		                     "        {\n" +
		                     "            \"fieldName\": \"deleteUsernameAsObject.username.username\",\n" +
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

	private MockHttpServletRequestBuilder givenARequestFor(JavaUsername username) {
		return MockMvcRequestBuilders.delete("/java/object/" + username.getUsername())
		                             .contentType(MediaType.APPLICATION_JSON)
		                             .characterEncoding("UTF-8");
	}

	private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
		return mvc.perform(request);
	}

	private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
		resultActions.andExpect(ResultMatcher.matchAll(matchers));
	}
}
