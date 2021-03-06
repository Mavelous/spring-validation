package com.mikevitale.demo.controller;

import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikevitale.demo.model.JavaUsername;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestEntityTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	@Disabled
	public void shouldTestRequestEntity() throws Exception {
		JavaUsername name = new JavaUsername("ben");

		RequestEntity<JavaUsername> e = RequestEntity
				.post(new URI("http://localhost:8080/java/object2"))
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(name);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(e, String.class);
		assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
	}
}
