package com.mikevitale.demo.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

val VALID_PHONE_NUMBER = KotlinPhoneNumber("11234567890")
val VALID_USERNAME = KotlinUsername("mike")

class KotlinPersonTest {
	lateinit var validator: Validator
	lateinit var person: KotlinPerson

	@BeforeEach
	fun setUp() {
		val validatorFactory = Validation.buildDefaultValidatorFactory()
		validator = validatorFactory.validator
	}

	@Test
	fun personShouldHaveValidUsername() {
		val username = KotlinUsername("mike")
		person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(0, violations.size)
	}

	@Test
	fun shoutsWhenPersonsUsernameIsTooShort() {
		val username = KotlinUsername("a")
		person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(1, violations.size)
		assertTrue(violations.any { it.message.equals("Username Size Validation Message") })
	}

	@Test
	fun shoutsWhenPersonsUsernameIsTooLong() {
		val username = KotlinUsername("abcdefghijklmnop")
		person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(1, violations.size)
		assertTrue(violations.any { it.message.equals("Username Size Validation Message") })
	}

	@Test
	fun shoutsWhenPersonsUsernameDoesntMatchPattern() {
		val username = KotlinUsername("12")
		person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(1, violations.size)
		assertTrue(violations.any { it.message.equals("Username Pattern Validation Message") })
	}

	@Test
	fun personShouldHaveValidPhoneNumber() {
		val phoneNumber = KotlinPhoneNumber("11234567890")
		person = KotlinPerson(VALID_USERNAME, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(0, violations.size)
	}

	@Test
	fun shoutsWhenPhoneNumberTooShort() {
		val phoneNumber = KotlinPhoneNumber("1")
		person = KotlinPerson(VALID_USERNAME, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(2, violations.size)
		assertTrue(violations.any { it.message.equals("Phone Number Pattern Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Size Validation Message") })
	}

	@Test
	fun shoutsWhenUsernameAndPhoneNumberTooShort() {
		val username = KotlinUsername("a")
		val phoneNumber = KotlinPhoneNumber("1")
		person = KotlinPerson(username, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(3, violations.size)
		assertTrue(violations.any { it.message.equals("Username Size Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Pattern Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Size Validation Message") })
	}

	@Test
	fun shoutsWhenUsernameTooShortAndDoesntMatchPatternAndPhoneNumberTooShort() {
		val username = KotlinUsername("1")
		val phoneNumber = KotlinPhoneNumber("1")
		person = KotlinPerson(username, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertEquals(4, violations.size)
		assertTrue(violations.any { it.message.equals("Username Size Validation Message") })
		assertTrue(violations.any { it.message.equals("Username Pattern Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Pattern Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Size Validation Message") })
	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinPerson>>) {
		for (violation in violations) {
			System.out.printf("***** ==> [%s]%n", violation.message)
		}
	}
}
