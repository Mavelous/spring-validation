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

public class JavaPersonTest {
	Validator validator;

	private JavaPerson person;

	@BeforeEach
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		person = new JavaPerson();
	}

	@Test
	public void personShouldHaveValidUsername() {
		JavaUsername username = new JavaUsername("mike");
		person.setUsername(username);

		Set<ConstraintViolation<JavaPerson>> violations = validator.validate(person);
		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void shoutsWhenPersonsUsernameIsTooShort() {
		JavaUsername username = new JavaUsername("a");
		person.setUsername(username);

		Set<ConstraintViolation<JavaPerson>> violations = validator.validate(person);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Size Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void shoutsWhenPersonsUsernameIsTooLong() {
		JavaUsername username = new JavaUsername("abcdefghijklmnop");
		person.setUsername(username);

		Set<ConstraintViolation<JavaPerson>> violations = validator.validate(person);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Size Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void shoutsWhenPersonsUsernameDoesntMatchPattern() {
		JavaUsername username = new JavaUsername("12");
		person.setUsername(username);

		Set<ConstraintViolation<JavaPerson>> violations = validator.validate(person);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Username Pattern Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	@Test
	public void personShouldHaveValidPhoneNumber() {
		JavaPhoneNumber phoneNumber = new JavaPhoneNumber("11234567890");
		person.setPhoneNumber(phoneNumber);

		Set<ConstraintViolation<JavaPerson>> violations = validator.validate(person);
		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void shoutsWhenPhoneNumberTooShort() {
		JavaPhoneNumber phoneNumber = new JavaPhoneNumber("1");
		person.setPhoneNumber(phoneNumber);

		Set<ConstraintViolation<JavaPerson>> violations = validator.validate(person);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());
		violations.stream().filter(v -> v.getMessage().contains("Phone Number Pattern")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Phone Number Size")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	private void printViolations(Set<ConstraintViolation<JavaPerson>> violations) {
		for (ConstraintViolation<JavaPerson> violation : violations) {
			System.out.printf("***** ==> [%s]%n", violation.getMessage());
		}
	}
}
