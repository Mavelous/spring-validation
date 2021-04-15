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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(KStringController::class)
class KStringPostControllerTest(@Autowired private val mvc: MockMvc) {
	@Test
	fun validStringPost() {
		val value = "mike"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk,
				MockMvcResultMatchers.content().bytes("Username is valid".toByteArray()))
	}

	@Test
	fun shoutsWhenStringPostIsTooShort() {
		val value = "a"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPost.username",
            "message": "Username Size Validation Message"
        }
    ]
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(APPLICATION_JSON),
				content.json(response))
	}

	@Test
	fun shoutsWhenStringPostIsTooLong() {
		val value = "abcdefghijklmnop"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPost.username",
            "message": "Username Size Validation Message"
        }
    ]
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(APPLICATION_JSON),
				content.json(response))
	}

	@Test
	fun shoutsWhenStringPostDoesntMatchPattern() {
		val value = "mike42"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPost.username",
            "message": "Username Pattern Validation Message"
        }
    ]
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(APPLICATION_JSON),
				content.json(response))
	}

	@Test
	fun shoutsWhenStringPostIsTooLongAndDoesntMatchPattern() {
		val value = "wpeurhgiouwerhgoiuwerhgo42"
		val request = givenARequestFor(value)
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPost.username",
            "message": "Username Pattern Validation Message"
        },
        {
            "fieldName": "validateStringPost.username",
            "message": "Username Size Validation Message"
        }
    ]
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(APPLICATION_JSON),
				content.json(response))
	}

	private fun givenARequestFor(name: String): MockHttpServletRequestBuilder {
		return MockMvcRequestBuilders
				.post("/kotlin/string")
				.characterEncoding("UTF-8")
				.contentType(APPLICATION_JSON)
				.content(name)
	}

	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder): ResultActions {
		return mvc.perform(request)
	}

	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) {
		resultActions.andExpect(ResultMatcher.matchAll(*matchers))
	}
}
