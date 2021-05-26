package com.mikevitale.demo.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation

class KotlinUsernameTest {
	private val validator = Validation.buildDefaultValidatorFactory().validator

	@Test
	fun validUsernameMinSize() {
		val username = KotlinUsername("ab")
		val violations: Set<ConstraintViolation<KotlinUsername>> = validator.validate(username)
		printViolations(violations)
		assertThat(violations).isEmpty()
	}

	@Test
	fun validUsernameMaxSize() {
		val username = KotlinUsername("abcdefghijklmno")
		val violations = validator.validate(username)
		printViolations(violations)
		assertThat(violations).isEmpty()
	}

	@Test
	fun usernameTooLong() {
		val username = KotlinUsername("abcdefghijklmnop")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertThat(violations).extracting("message").containsExactly("Username Size Validation Message")
	}

	@Test
	fun usernameCannotContainNumber() {
		val username = KotlinUsername("12")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertThat(violations).extracting("message").containsExactly("Username Pattern Validation Message")

	}

	@Test
	fun usernameTooShort() {
		val username = KotlinUsername("a")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertThat(violations).extracting("message").containsExactly("Username Size Validation Message")

	}

	@Test
	fun usernameTooShortAndDoesntMatchPattern() {
		val username = KotlinUsername("1")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertThat(violations).extracting("message").containsExactlyInAnyOrder(
			"Username Pattern Validation Message",
			"Username Size Validation Message"
		)

	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinUsername>>) {
		violations.forEach {
			System.out.printf("***** ==> [%s]%n", it.message)
		}
	}
}
