package com.mikevitale.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.*;
import javax.validation.constraints.*;

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
public class DemoController {
	private static final Logger LOG = Logger.getLogger(DemoController.class.getName());

	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

	Validator validator = validatorFactory.getValidator();

	@GetMapping(path = "/java/string/{username}",
			produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> getUsernameAsString(
			@PathVariable("username")
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 1, max = 15, message = "Username Size Validation Message")
			@Valid String username) {
		LOG.info(() -> String.format("Got Username [%s]", username));

//		Set<ConstraintViolation<String>> violations = validator.validate(username);
//		printViolations(violations);

		System.out.println(String.format("Username is [%s]%n", username));

		return ResponseEntity.ok("Username is valid");
	}

	@GetMapping("validatePathVariable/{id}")
	ResponseEntity<String> validatePathVariable(
			@PathVariable("id")
			@Min(value = 5, message = "A minimum value of 5 is required")
			@Max(value = 9999, message = "A maximum value of 9999 can be given")
					int id) {
		LOG.info(() -> String.format("validatePathVariable(), Got id = %d", id));
		return ResponseEntity.ok("valid");
	}

	@GetMapping(path = "/java/string2/{username}",
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUsernameAsString2(
			@PathVariable("username") @NotBlank @Size(max = 10) String username) {

		Set<ConstraintViolation<String>> violations = validator.validate(username);
		printViolations(violations);

		System.out.println(String.format("Username is [%s]%n", username));

		return ResponseEntity.ok("Username is valid");
	}

	private void printViolations(Set<ConstraintViolation<String>> violations) {
		for (ConstraintViolation<String> violation : violations) {
			System.out.printf("***** ==> [%s]%n", violation.getMessage());
		}
	}

	@PostMapping(path = "/java/object",
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
