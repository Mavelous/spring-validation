package com.mikevitale.demo.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation

private val VALID_PHONE_NUMBER = KotlinPhoneNumber("11234567890")
private val VALID_USERNAME = KotlinUsername("mike")

class KotlinPersonTest {
	private val validator = Validation.buildDefaultValidatorFactory().validator

	@Test
	fun personShouldHaveValidUsername() {
		val username = KotlinUsername("mike")
		val person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).isEmpty()
	}

	@Test
	fun shoutsWhenPersonsUsernameIsTooShort() {
		val username = KotlinUsername("a")
		val person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactly("Username Size Validation Message")
	}

	@Test
	fun shoutsWhenPersonsUsernameIsTooLong() {
		val username = KotlinUsername("abcdefghijklmnop")
		val person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactly("Username Size Validation Message")
	}

	@Test
	fun shoutsWhenPersonsUsernameDoesntMatchPattern() {
		val username = KotlinUsername("12")
		val person = KotlinPerson(username, VALID_PHONE_NUMBER)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactly("Username Pattern Validation Message")
	}

	@Test
	fun personShouldHaveValidPhoneNumber() {
		val phoneNumber = KotlinPhoneNumber("11234567890")
		val person = KotlinPerson(VALID_USERNAME, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).isEmpty()
	}

	@Test
	fun shoutsWhenPhoneNumberTooShort() {
		val phoneNumber = KotlinPhoneNumber("1")
		val person = KotlinPerson(VALID_USERNAME, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactlyInAnyOrder(
			"Phone Number Size Validation Message",
			"Phone Number Pattern Validation Message"
		)
	}

	@Test
	fun shoutsWhenUsernameAndPhoneNumberTooShort() {
		val username = KotlinUsername("a")
		val phoneNumber = KotlinPhoneNumber("1")
		val person = KotlinPerson(username, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactlyInAnyOrder(
			"Username Size Validation Message",
			"Phone Number Pattern Validation Message",
			"Phone Number Size Validation Message"
		)
	}

	@Test
	fun shoutsWhenUsernameTooShortAndDoesntMatchPatternAndPhoneNumberTooShort() {
		val username = KotlinUsername("1")
		val phoneNumber = KotlinPhoneNumber("1")
		val person = KotlinPerson(username, phoneNumber)
		val violations = validator.validate(person)
		printViolations(violations)
		assertThat(violations).extracting("message").containsExactlyInAnyOrder(
			"Username Size Validation Message",
			"Username Pattern Validation Message",
			"Phone Number Pattern Validation Message",
			"Phone Number Size Validation Message"
		)
	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinPerson>>) {
		for (violation in violations) {
			System.out.printf("***** ==> [%s]%n", violation.message)
		}
	}
}
