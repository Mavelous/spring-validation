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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(KIntController::class)
class KIntControllerTest(@Autowired private val mvc: MockMvc) {
	@Test
	fun validIntegerPathVariable() {
		val request: MockHttpServletRequestBuilder = givenARequestFor("/kotlin/int/5")
		val actions: ResultActions = whenTheRequestIsMade(request)
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk,
				MockMvcResultMatchers.content().bytes("ID is valid".toByteArray()))
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
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(resultActions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response))
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
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(resultActions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response))
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
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(resultActions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response))
	}

	private fun givenARequestFor(url: String): MockHttpServletRequestBuilder {
		return MockMvcRequestBuilders.get(url)
				.characterEncoding("UTF-8")
	}

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder): ResultActions {
		return mvc.perform(request)
	}

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) {
		resultActions.andExpect(ResultMatcher.matchAll(*matchers))
	}
}
