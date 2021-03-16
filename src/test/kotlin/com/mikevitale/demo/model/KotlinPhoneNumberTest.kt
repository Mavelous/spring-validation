package com.mikevitale.demo.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class KotlinPhoneNumberTest {
	lateinit var validator: Validator
	lateinit var phoneNumber: KotlinPhoneNumber

	@BeforeEach
	fun setup() {
		val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
		validator = factory.validator
	}

	@Test
	fun phoneNumberShouldBeCorrectLengthAndPattern() {
		phoneNumber = KotlinPhoneNumber("11234567890")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertEquals(0, violations.size)
	}

	@Test
	fun shoutsWhenLengthTooShort() {
		phoneNumber = KotlinPhoneNumber("1")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertEquals(2, violations.size)
		assertTrue(violations.any { it.message.equals("Phone Number Pattern Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Size Validation Message") })
	}

	@Test
	fun shoutsWhenLengthTooLong() {
		phoneNumber = KotlinPhoneNumber("123456789012")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertEquals(2, violations.size)
		assertTrue(violations.any { it.message.equals("Phone Number Pattern Validation Message") })
		assertTrue(violations.any { it.message.equals("Phone Number Size Validation Message") })
	}

	@Test
	fun shoutsWhenPatternNotMatchedButCorrectSize() {
		phoneNumber = KotlinPhoneNumber("abcdefghijk")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertEquals(1, violations.size)
		assertTrue(violations.any { it.message.equals("Phone Number Pattern Validation Message") })
	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinPhoneNumber>>) {
		violations.forEach {
			System.out.printf("***** ==> [%s]%n", it.message)
		}
	}
}
