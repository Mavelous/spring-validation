package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import com.mikevitale.demo.model.JavaUsername;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
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

	@PutMapping(path = "object",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putUsernameAsObject(@Valid @RequestBody JavaUsername username) {
		LOG.info(() -> String.format("Got Username [%s]", username));

		return ResponseEntity.ok("Username is valid");
	}

	@DeleteMapping(path = "object/{username}",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUsernameAsObject(@Valid @PathVariable("username") JavaUsername username) {
		LOG.info(() -> String.format("Got Username [%s]", username));

		return ResponseEntity.ok("Username is valid");
	}
}
