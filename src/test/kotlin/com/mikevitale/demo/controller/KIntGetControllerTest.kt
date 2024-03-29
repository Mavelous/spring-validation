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

@WebMvcTest(KIntController::class)
class KIntGetControllerTest(@Autowired private val mvc: MockMvc) {
	@Test
	fun validIntegerPathVariable() {
		val request: MockHttpServletRequestBuilder = givenARequestFor("/kotlin/int/5")
		val actions: ResultActions = whenTheRequestIsMade(request)
		thenExpect(
			actions,
			status().isOk,
			content().bytes("ID is valid".toByteArray())
		)
	}

	@Test
	fun shoutsWhenIntegerPathVariableIsTooLow() {
		val request = givenARequestFor("/kotlin/int/4")
		val resultActions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateIntPathVariable.id",
					"message": "A minimum value of 5 is required"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			resultActions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenIntegerPathVariableIsTooHigh() {
		val request = givenARequestFor("/kotlin/int/10000")
		val resultActions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "validateIntPathVariable.id",
					"message": "A maximum value of 9999 can be given"
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			resultActions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	@Test
	fun shoutsWhenStringIsPassedToIntegerPathVariable() {
		val request = givenARequestFor("/kotlin/int/wegyrguywery")
		val resultActions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "id",
					"message": "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"wegyrguywery\""
				}
			]
		}""".trimIndent()
		val content = content()
		thenExpect(
			resultActions,
			status().isBadRequest,
			content.contentType(MediaType.APPLICATION_JSON),
			content.json(response)
		)
	}

	private fun givenARequestFor(url: String) = MockMvcRequestBuilders.get(url).characterEncoding("UTF-8")

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder) = mvc.perform(request)

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) = resultActions.andExpect(ResultMatcher.matchAll(*matchers))
}
