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
import static org.springframework.test.util.AssertionErrors.fail;

public class JavaPhoneNumberTest {
	Validator validator;

	private JavaPhoneNumber phoneNumber;

	@BeforeEach
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		phoneNumber = new JavaPhoneNumber();
	}

	@Test
	public void phoneNumberShouldBeCorrectLengthAndPattern() {
		phoneNumber.setPhoneNumber("11234567890");
		Set<ConstraintViolation<JavaPhoneNumber>> violations = validator.validate(phoneNumber);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void shoutsWhenLengthTooShort() {
		phoneNumber.setPhoneNumber("1");
		Set<ConstraintViolation<JavaPhoneNumber>> violations = validator.validate(phoneNumber);

		printViolations(violations);

		assertEquals(2, violations.size());
		violations.stream().filter(v -> v.getMessage().contains("Pattern")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Size")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	@Test
	public void shoutsWhenLengthTooLong() {
		phoneNumber.setPhoneNumber("123456789012");
		Set<ConstraintViolation<JavaPhoneNumber>> violations = validator.validate(phoneNumber);

		printViolations(violations);

		assertEquals(2, violations.size());
		violations.stream().filter(v -> v.getMessage().contains("Pattern")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Size")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	@Test
	public void shoutsWhenPatternNotMatchedButCorrectSize() {
		phoneNumber.setPhoneNumber("abcdefghijk");
		Set<ConstraintViolation<JavaPhoneNumber>> violations = validator.validate(phoneNumber);

		printViolations(violations);

		assertEquals(1, violations.size());
		assertEquals("Phone Number Pattern Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null)).getMessage());
	}

	private void printViolations(Set<ConstraintViolation<JavaPhoneNumber>> violations) {
		for (ConstraintViolation<JavaPhoneNumber> violation : violations) {
			System.out.printf("***** ==> [%s]%n", violation.getMessage());
		}
	}
}
