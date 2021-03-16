package com.mikevitale.demo.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.util.AssertionErrors
import java.util.*
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class KotlinUsernameTest {
	lateinit var validator: Validator
	lateinit var username: KotlinUsername

	@BeforeEach
	fun setup() {
		val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
		validator = factory.validator
	}

	@Test
	fun validUsernameMinSize() {
		username = KotlinUsername("ab")
		val violations: Set<ConstraintViolation<KotlinUsername>> = validator.validate(username)
		printViolations(violations)
		assertEquals(0, violations.size)
	}

	@Test
	fun validUsernameMaxSize() {
		username = KotlinUsername("abcdefghijklmno")
		val violations = validator.validate(username)
		printViolations(violations)
		assertEquals(0, violations.size)
	}

	@Test
	fun usernameTooLong() {
		username = KotlinUsername("abcdefghijklmnop")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(1, violations.size)
		assertEquals("Username Size Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null))!!.message)
	}

	@Test
	fun usernameCannotContainNumber() {
		username = KotlinUsername("12")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(1, violations.size)
		assertEquals("Username Pattern Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null))!!.message)
	}

	@Test
	fun usernameTooShort() {
		username = KotlinUsername("a")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(1, violations.size)
		assertEquals("Username Size Validation Message",
				Objects.requireNonNull(violations.stream().findFirst().orElse(null))!!.message)
	}

	@Test
	fun usernameTooShortAndDoesntMatchPattern() {
		username = KotlinUsername("1")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(2, violations.size)
		violations.stream().filter { v: ConstraintViolation<KotlinUsername> -> v.message.contains("Pattern") }.findAny().ifPresentOrElse({ }) { AssertionErrors.fail("Message not found") }
		violations.stream().filter { v: ConstraintViolation<KotlinUsername> -> v.message.contains("Size") }.findAny().ifPresentOrElse({ }) { AssertionErrors.fail("Message not found") }
	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinUsername>>) {
		for (violation in violations) {
			System.out.printf("***** ==> [%s]%n", violation.message)
		}
	}
}
