package com.mikevitale.demo.model;

import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JavaUsernameTest {
	Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void validUsernameMinSize() {
		JavaUsername username = new JavaUsername();

		username.setUsername("a");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void validUsernameMaxSize() {
		JavaUsername username = new JavaUsername();

		username.setUsername("abcdefghijklmno");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void usernameTooLong() {
		JavaUsername username = new JavaUsername();

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
		JavaUsername username = new JavaUsername();

		username.setUsername("1");

		Set<ConstraintViolation<JavaUsername>> violations = validator.validate(username);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Pattern Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void usernameTooShort() {
		JavaUsername username = new JavaUsername();

		username.setUsername("");

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
