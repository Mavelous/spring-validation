package com.mikevitale.demo.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(KStringController::class)
class KStringGetControllerTest(@Autowired private val mvc: MockMvc) {
	@Test
	fun validStringPathVariable() {
		val request: MockHttpServletRequestBuilder = givenARequestFor("mike")
		val actions: ResultActions = whenTheRequestIsMade(request)
		thenExpect(
			actions,
			status().isOk,
			content().bytes("Username is valid".toByteArray())
		)
	}

	@Test
	fun shoutsWhenStringPathVariableIsTooShort() {
		val request = givenARequestFor("a")
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPathVariable.username",
					"message": "Username Size Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringPathVariableIsTooLong() {
		val request = givenARequestFor("wpeurhgiouwerhgoiuwerhgo")
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPathVariable.username",
					"message": "Username Size Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringPathVariableDoesntMatchPattern() {
		val request = givenARequestFor("mike42")
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPathVariable.username",
					"message": "Username Pattern Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringPathVariableIsTooLongAndDoesntMatchPattern() {
		val request = givenARequestFor("wpeurhgiouwerhgoiuwerhgo42")
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateStringPathVariable.username",
					"message": "Username Pattern Validation Message"
				},
				{
					"fieldName": "validateStringPathVariable.username",
					"message": "Username Size Validation Message"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			actions,
			status().isBadRequest,
			content.contentType(APPLICATION_JSON),
			content.json(response)
		)
	}

	private fun givenARequestFor(url: String) = MockMvcRequestBuilders.get("/kotlin/string/$url").characterEncoding("UTF-8")

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder) = mvc.perform(request)

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) = resultActions.andExpect(ResultMatcher.matchAll(*matchers))
}
