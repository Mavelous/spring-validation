package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/java/")
public class IntController {
	private static final Logger LOG = Logger.getLogger(IntController.class.getName());

	@GetMapping("int/{id}")
	@ResponseBody
	ResponseEntity<String> validateIntPathVariable(
			@PathVariable("id")
			@Min(value = 5, message = "A minimum value of 5 is required")
			@Max(value = 9999, message = "A maximum value of 9999 can be given")
					int id) {
		LOG.info(() -> String.format("validatePathVariable(), Got id = %d", id));
		return ResponseEntity.ok("ID is valid");
	}
}
