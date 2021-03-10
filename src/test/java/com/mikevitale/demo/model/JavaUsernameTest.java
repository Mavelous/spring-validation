package com.mikevitale.demo.model;

import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.fail;

public class JavaUsernameTest {
	Validator validator;

	private JavaUsername username;

	@BeforeEach
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		username = new JavaUsername();
	}

	@Test
	public void validUsernameMinSize() {
		username.setUsername("ab");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void validUsernameMaxSize() {
		username.setUsername("abcdefghijklmno");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void usernameTooLong() {
		username.setUsername("abcdefghijklmnop");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Size Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void usernameCannotContainNumber() {
		username.setUsername("12");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Pattern Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void usernameTooShort() {
		username.setUsername("a");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Size Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void usernameTooShortAndDoesntMatchPattern() {
		username.setUsername("1");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());

		violations.stream().filter(v -> v.getMessage().contains("Pattern")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Size")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	private void printViolations(Set<ConstraintViolation<JavaUsername>> violations) {
		for (ConstraintViolation<JavaUsername> violation : violations) {
			System.out.printf("***** ==> [%s]%n", violation.getMessage());
		}
	}
}
