package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import com.mikevitale.demo.model.JavaUsername;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Validated
@RequestMapping("/java/")
public class ObjectController {
	private static final Logger LOG = Logger.getLogger(ObjectController.class.getName());

	@PostMapping(path = "object",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUsernameAsObject(@Valid @RequestBody JavaUsername username) {
		LOG.info(() -> String.format("Got Username [%s]", username));

		return ResponseEntity.ok("Username is valid");
	}

	@PostMapping(path = "object2",
			consumes = APPLICATION_FORM_URLENCODED_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUsernameAsObject2(RequestEntity<JavaUsername> entity) {
		JavaUsername username = entity.getBody();
		LOG.info(() -> String.format("Got Username [%s]", username));

		return ResponseEntity.ok("Username is valid");
	}
}
