package com.mikevitale.demo.controller

import mu.KotlinLogging
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

private val logger = KotlinLogging.logger {}

@RestController
@Validated
@RequestMapping("/kotlin/")
open class KStringController {

	@GetMapping(path = ["string/{username}"],
			produces = [APPLICATION_JSON_VALUE])
	@ResponseBody
	open fun validateStringPathVariable(
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 2, max = 15, message = "Username Size Validation Message")
			@PathVariable("username") username: String?
	): ResponseEntity<String>? {
		logger.info { "validateStringPathVariable: Got Username [$username]" }
		System.out.printf("validateStringPathVariable: Username is [$username]%n")

		return ResponseEntity.ok("Username is valid")
	}

	@PostMapping(path = ["string"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
	@ResponseBody
	open fun validateStringPost(
			@Pattern(regexp = "[A-Za-z]+", message = "Username Pattern Validation Message")
			@Size(min = 2, max = 15, message = "Username Size Validation Message")
			@RequestBody username: String?): ResponseEntity<String>? {
		logger.info { String.format("validateStringPost: Got Username [%s]", username) }
		System.out.printf("validateStringPost: Username is [%s]%n", username)
		return ResponseEntity.ok("Username is valid")
	}
}
