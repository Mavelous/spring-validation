package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;
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
			@Size(min = 1, max = 15, message = "Username Size Validation Message")
			@Valid String username) {
		LOG.info(() -> String.format("Got Username [%s]", username));

		System.out.printf("Username is [%s]%n%n", username);

		return ResponseEntity.ok("Username is valid");
	}
}
