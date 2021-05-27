package com.mikevitale.demo.controller

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

private val logger = KotlinLogging.logger {}

@RestController
@Validated
@RequestMapping("/kotlin/")
open class KIntController {
	@GetMapping("int/{id}")
	@ResponseBody
	open fun validateIntPathVariable(
			@Min(value = 5, message = "A minimum value of 5 is required")
			@Max(value = 9999, message = "A maximum value of 9999 can be given")
			@PathVariable("id") id: Int)
	: ResponseEntity<String> {
		logger.info { "validateIntPathVariable: Got id [$id]" }
		return ResponseEntity.ok("ID is valid")
	}
}
