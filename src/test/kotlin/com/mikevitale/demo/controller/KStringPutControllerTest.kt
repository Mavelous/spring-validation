package com.mikevitale.demo.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(KStringController::class)
class KStringPutControllerTest(@Autowired private val mvc: MockMvc) {
	@Test
	fun validStringPut() {
		val value = "mike"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		thenExpect(
			actions,
			status().isOk,
			content().bytes("Username is valid".toByteArray())
		)
	}

	@Test
	fun shoutsWhenStringPutIsTooShort() {
		val value = "a"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPut.username",
					"message": "Username Size Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringPutIsTooLong() {
		val value = "abcdefghijklmnop"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPut.username",
					"message": "Username Size Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringPutDoesntMatchPattern() {
		val value = "mike42"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPut.username",
					"message": "Username Pattern Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringPutIsTooLongAndDoesntMatchPattern() {
		val value = "wpeurhgiouwerhgoiuwerhgo42"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPut.username",
					"message": "Username Pattern Validation Message"
				},
				{
					"fieldName": "validateStringPut.username",
					"message": "Username Size Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	private fun givenARequestFor(name: String) = MockMvcRequestBuilders
		.put("/kotlin/string")
		.characterEncoding("UTF-8")
		.contentType(MediaType.APPLICATION_JSON)
		.content(name)

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder) = mvc.perform(request)

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) = resultActions.andExpect(ResultMatcher.matchAll(*matchers))
}
