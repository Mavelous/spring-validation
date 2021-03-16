package com.mikevitale.demo.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
		assertTrue(violations.any { it.message.contains("Username Size Validation Message") })
	}

	@Test
	fun usernameCannotContainNumber() {
		username = KotlinUsername("12")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(1, violations.size)
		assertTrue(violations.any { it.message.contains("Username Pattern Validation Message") })
	}

	@Test
	fun usernameTooShort() {
		username = KotlinUsername("a")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(1, violations.size)
		assertTrue(violations.any { it.message.contains("Username Size Validation Message") })
	}

	@Test
	fun usernameTooShortAndDoesntMatchPattern() {
		username = KotlinUsername("1")
		val violations = validator.validate(username)
		printViolations(violations)
		assertFalse(violations.isEmpty())
		assertEquals(2, violations.size)
		assertTrue(violations.any { it.message.contains("Pattern") })
		assertTrue(violations.any { it.message.contains("Size") })
	}

	private fun printViolations(violations: Set<ConstraintViolation<KotlinUsername>>) {
		violations.forEach {
			System.out.printf("***** ==> [%s]%n", it.message)
		}
	}
}
