package com.mikevitale.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mikevitale.demo.model.KotlinUsername
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(KSimpleObjectController::class)
@AutoConfigureMockMvc
class KSimpleObjectPostControllerTest {
	@Autowired
	private lateinit var mvc: MockMvc

	@Autowired
	private lateinit var objectMapper: ObjectMapper

	@Test
	fun validObject() {
		val validUsername = KotlinUsername("mike")
		val request: MockHttpServletRequestBuilder = givenARequestForUsername(validUsername)
		val actions: ResultActions = whenTheRequestIsMade(request)
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk,
				MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	}

	@Test
	@Disabled(value = "Currently returning 200, not 400.")
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
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response))
	}

	private fun givenARequestForUsername(username: KotlinUsername): MockHttpServletRequestBuilder {
		val requestBody = objectMapper.writeValueAsString(username)
		return givenARequestFor(requestBody)
	}

	private fun givenARequestFor(requestBody: String): MockHttpServletRequestBuilder {
		return MockMvcRequestBuilders.post("/kotlin/object")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(requestBody)
	}

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder): ResultActions {
		return mvc.perform(request)
	}

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) {
		resultActions.andExpect(ResultMatcher.matchAll(*matchers))
	}
}
