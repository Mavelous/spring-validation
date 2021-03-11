package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import com.mikevitale.demo.model.JavaPerson;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/java/complex/")
public class ComplexObjectController {
	private static final Logger LOG = Logger.getLogger(ComplexObjectController.class.getName());

	@PostMapping(path = "object",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postUsernameAsObject(@Valid @RequestBody JavaPerson person) {
		LOG.info(() -> String.format("Got Person [%s]", person));

		return ResponseEntity.ok("Person is valid");
	}

	@PutMapping(path = "object",
	         consumes = APPLICATION_JSON_VALUE,
	         produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putUsernameAsObject(@Valid @RequestBody JavaPerson person) {
		LOG.info(() -> String.format("Got Person [%s]", person));

		return ResponseEntity.ok("Person is valid");
	}
}
