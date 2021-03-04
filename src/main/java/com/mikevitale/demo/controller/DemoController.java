package com.mikevitale.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.mikevitale.demo.model.JavaUsername;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/java/")
public class DemoController {
	private static final Logger LOG = Logger.getLogger(DemoController.class.getName());

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

	@GetMapping("int/{id}")
	@ResponseBody
	ResponseEntity<String> validateIntPathVariable(
			@PathVariable("id")
			@Min(value = 5, message = "A minimum value of 5 is required")
			@Max(value = 9999, message = "A maximum value of 9999 can be given")
					int id) {
		LOG.info(() -> String.format("validatePathVariable(), Got id = %d", id));
		return ResponseEntity.ok("ID is valid");
	}

	@PostMapping(path = "java/object",
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUsernameAsObject(@Valid @RequestBody JavaUsername username) {
		return ResponseEntity.ok("Username is valid");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
