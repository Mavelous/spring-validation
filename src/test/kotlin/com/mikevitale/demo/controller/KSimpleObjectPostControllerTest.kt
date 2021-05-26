package com.mikevitale.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mikevitale.demo.model.KotlinUsername
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

@WebMvcTest(KSimpleObjectController::class)
class KSimpleObjectPostControllerTest(@Autowired private val mvc: MockMvc) {

	@Autowired
	private lateinit var objectMapper: ObjectMapper

	@Test
	fun validObject() {
		val validUsername = KotlinUsername("mike")
		val request: MockHttpServletRequestBuilder = givenARequestForUsername(validUsername)
		val actions: ResultActions = whenTheRequestIsMade(request)
		thenExpect(
			actions,
			status().isOk,
			content().contentType(MediaType.APPLICATION_JSON)
		)
	}

	@Test
	fun shoutsWhenObjectNameIsTooLong() {
		val username = KotlinUsername("wpeurhgiouwerhgoiuwerhgo")
		val request = givenARequestForUsername(username)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "username",
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
	fun shoutsWhenObjectNameDoesntMatchPattern() {
		val username = KotlinUsername("1234")
		val request = givenARequestForUsername(username)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "username",
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
	fun shoutsWhenObjectNameIsTooShortAndDoesntMatchPattern() {
		val username = KotlinUsername("1")
		val request = givenARequestForUsername(username)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "username",
					"message": "Username Size Validation Message"
				},
				{
					"fieldName": "username",
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
	fun shoutsWhenObjectNameIsTooShort() {
		val username = KotlinUsername("a")
		val request = givenARequestForUsername(username)
		val actions = whenTheRequestIsMade(request)
		val response = """{
			"validationErrors": [
				{
					"fieldName": "username",
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

	private fun givenARequestForUsername(username: KotlinUsername) = givenARequestFor(objectMapper.writeValueAsString(username))

	private fun givenARequestFor(requestBody: String) =
		MockMvcRequestBuilders
			.post("/kotlin/object")
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.content(requestBody)

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder) = mvc.perform(request)

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) = resultActions.andExpect(ResultMatcher.matchAll(*matchers))
}
