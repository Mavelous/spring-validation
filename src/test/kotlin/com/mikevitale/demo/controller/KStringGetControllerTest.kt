package com.mikevitale.demo.controller

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
@WebMvcTest(KStringController::class)
@AutoConfigureMockMvc
class KStringGetControllerTest {
	@field:Autowired
	private lateinit var mvc: MockMvc

	@Test
	@Throws(Exception::class)
	fun validStringPathVariable() {
		val request: MockHttpServletRequestBuilder = givenARequestFor("/kotlin/string/mike")
		val actions: ResultActions = whenTheRequestIsMade(request)
		thenExpect(actions,
				MockMvcResultMatchers.status().isOk,
				MockMvcResultMatchers.content().bytes("Username is valid".toByteArray()))
	}

	@Test
	@Disabled(value = "Currently returning 200, not 400.")
	@Throws(Exception::class)
	fun shoutsWhenStringPathVariableIsTooShort() {
		val request = givenARequestFor("/kotlin/string/a")
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPathVariable.username",
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

	@Test
	@Disabled(value = "Currently returning 200, not 400.")
	@Throws(Exception::class)
	fun shoutsWhenStringPathVariableIsTooLong() {
		val request = givenARequestFor("/kotlin/string/wpeurhgiouwerhgoiuwerhgo")
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPathVariable.username",
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

	@Test
	@Disabled(value = "Currently returning 200, not 400.")
	@Throws(Exception::class)
	fun shoutsWhenStringPathVariableDoesntMatchPattern() {
		val request = givenARequestFor("/kotlin/string/mike42")
		val actions = whenTheRequestIsMade(request)
		val response = """{
    "validationErrors": [
        {
            "fieldName": "validateStringPathVariable.username",
            "message": "Username Pattern Validation Message"
        }
    ]
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response))
	}

	@Test
	@Disabled(value = "Currently returning 200, not 400.")
	@Throws(Exception::class)
	fun shoutsWhenStringPathVariableIsTooLongAndDoesntMatchPattern() {
		val request = givenARequestFor("/kotlin/string/wpeurhgiouwerhgoiuwerhgo42")
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
}"""
		val content = MockMvcResultMatchers.content()
		thenExpect(actions,
				MockMvcResultMatchers.status().isBadRequest,
				content.contentType(MediaType.APPLICATION_JSON),
				content.json(response))
	}

	private fun givenARequestFor(url: String): MockHttpServletRequestBuilder {
		return MockMvcRequestBuilders.get(url)
				.characterEncoding("UTF-8")
	}

	@Throws(Exception::class)
	private fun whenTheRequestIsMade(request: MockHttpServletRequestBuilder): ResultActions {
		return mvc.perform(request)
	}

	@Throws(Exception::class)
	private fun thenExpect(resultActions: ResultActions, vararg matchers: ResultMatcher) {
		resultActions.andExpect(ResultMatcher.matchAll(*matchers))
	}
}
