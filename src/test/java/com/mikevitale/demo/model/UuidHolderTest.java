package com.mikevitale.demo.model;

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

public class UuidHolderTest {
	private static final String VALID_UUID = "1234abcd-ef56-78ab-90cd-ef1234abcd56";
	private static final String VALID_UUID_ALL_CAPS = "1234ABCD-EF56-78AB-90CD-EF1234ABCD56";

	Validator validator;

	private UuidHolder uuid;

	@BeforeEach
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		uuid = new UuidHolder();
	}

	@Test
	public void validUuid() {
		uuid.setUuid(VALID_UUID);

		Set<ConstraintViolation<UuidHolder>> violations = validator.validate(uuid);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void validUuidCaseInsensitive() {
		uuid.setUuid(VALID_UUID_ALL_CAPS);

		Set<ConstraintViolation<UuidHolder>> violations = validator.validate(uuid);

		printViolations(violations);

		assertEquals(0, violations.size());
	}

	@Test
	public void uuidTooLong() {
		uuid.setUuid("1234abcd-ef56-78ab-90cd-ef1234abcd567");

		Set<ConstraintViolation<UuidHolder>> violations = validator.validate(uuid);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());

		violations.stream().filter(v -> v.getMessage().contains("Version UUID")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Allows 36 characters")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	@Test
	public void uuidTooShort() {
		uuid.setUuid("1234abcd-ef56-78ab-90cd-ef1234abcd5");

		Set<ConstraintViolation<UuidHolder>> violations = validator.validate(uuid);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());

		violations.stream().filter(v -> v.getMessage().contains("Version UUID")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Allows 36 characters")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	@Test
	public void usernameTooShortAndDoesntMatchPattern() {
		uuid.setUuid("1234ghij-kl56-78mn-90op-qr1234stuv5");

		Set<ConstraintViolation<UuidHolder>> violations = validator.validate(uuid);
		printViolations(violations);

		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());

		violations.stream().filter(v -> v.getMessage().contains("Version UUID")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
		violations.stream().filter(v -> v.getMessage().contains("Allows 36 characters")).findAny().ifPresentOrElse(a -> {}, () -> fail("Message not found"));
	}

	private void printViolations(Set<ConstraintViolation<UuidHolder>> violations) {
		for (ConstraintViolation<UuidHolder> violation : violations) {
			System.out.printf("***** ==> [%s]%n", violation.getMessage());
		}
	}
}
