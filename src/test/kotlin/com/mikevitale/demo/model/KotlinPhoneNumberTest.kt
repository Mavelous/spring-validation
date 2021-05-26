package com.mikevitale.demo.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation

class KotlinPhoneNumberTest {
	private val validator = Validation.buildDefaultValidatorFactory().validator

	@Test
	fun phoneNumberShouldBeCorrectLengthAndPattern() {
		val phoneNumber = KotlinPhoneNumber("11234567890")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertThat(violations).isEmpty()
	}

	@Test
	fun shoutsWhenLengthTooShort() {
		val phoneNumber = KotlinPhoneNumber("1")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactlyInAnyOrder(
			"Phone Number Pattern Validation Message",
			"Phone Number Size Validation Message"
		)
	}

	@Test
	fun shoutsWhenLengthTooLong() {
		val phoneNumber = KotlinPhoneNumber("123456789012")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactlyInAnyOrder(
			"Phone Number Pattern Validation Message",
			"Phone Number Size Validation Message"
		)
	}

	@Test
	fun shoutsWhenPatternNotMatchedButCorrectSize() {
		val phoneNumber = KotlinPhoneNumber("abcdefghijk")
		val violations = validator.validate(phoneNumber)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactly("Phone Number Pattern Validation Message")
	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinPhoneNumber>>) {
		violations.forEach {
			System.out.printf("***** ==> [%s]%n", it.message)
		}
	}
}
