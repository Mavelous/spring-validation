package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/java/")
public class StringController {
	private static final Logger LOG = Logger.getLogger(StringController.class.getName());

	@GetMapping(path = "string/{username}",
			produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> validateStringPathVariable(
			@PathVariable("username")
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 2, max = 15, message = "Username Size Validation Message")
			String username) {
		LOG.info(() -> String.format("validateStringPathVariable: Got Username [%s]", username));

		System.out.printf("validateStringPathVariable: Username is [%s]%n", username);

		return ResponseEntity.ok("Username is valid");
	}

	@PostMapping(path = "string",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> validateStringPost(
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 2, max = 15, message = "Username Size Validation Message")
			@RequestBody String username) {
		LOG.info(() -> String.format("validateStringPost: Got Username [%s]", username));

		System.out.printf("validateStringPost: Username is [%s]%n", username);

		return ResponseEntity.ok("Username is valid");
	}

	@PutMapping(path = "string",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> validateStringPut(
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 2, max = 15, message = "Username Size Validation Message")
			@RequestBody String username) {
		LOG.info(() -> String.format("validateStringPost: Got Username [%s]", username));

		System.out.printf("validateStringPost: Username is [%s]%n", username);

		return ResponseEntity.ok("Username is valid");
	}

	@DeleteMapping(path = "string",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> validateStringDelete(
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 2, max = 15, message = "Username Size Validation Message")
			@RequestBody String username) {
		LOG.info(() -> String.format("validateStringPost: Got Username [%s]", username));

		System.out.printf("validateStringPost: Username is [%s]%n", username);

		return ResponseEntity.ok("Username is valid");
	}
}
