package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import com.mikevitale.demo.model.JavaPerson;
import com.mikevitale.demo.model.UuidHolder;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/java/")
public class ObjectWithHeaderController {
	private static final Logger LOG = Logger.getLogger(ObjectWithHeaderController.class.getName());

	@PostMapping(path = "header",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> complexObjectWithHeader(
			@Valid @RequestHeader("uuid") UuidHolder uuid,

			RequestEntity<JavaPerson> entity) {
		JavaPerson person = entity.getBody();
		LOG.info(() -> String.format("Got Uuid [%s]", uuid));
		LOG.info(() -> String.format("Got Person [%s]", person));

		return ResponseEntity.ok("Person is valid");
	}
}
