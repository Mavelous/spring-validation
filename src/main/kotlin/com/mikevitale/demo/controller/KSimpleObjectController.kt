package com.mikevitale.demo.controller

import com.mikevitale.demo.model.KotlinUsername
import mu.KotlinLogging
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/kotlin/")
open class KSimpleObjectController {
	@PostMapping(path = ["object"],
			consumes = [APPLICATION_JSON_VALUE],
			produces = [APPLICATION_JSON_VALUE])
	open fun getUsernameAsObject(@RequestBody @Valid username: KotlinUsername?): ResponseEntity<String>? {
		logger.info { "Got Username [$username]" }
		return ResponseEntity.ok("Username is valid")
	}
}
