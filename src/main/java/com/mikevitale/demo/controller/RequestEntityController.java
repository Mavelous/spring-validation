package com.mikevitale.demo.controller;

import java.util.logging.Logger;

import com.mikevitale.demo.model.JavaPerson;
import com.mikevitale.demo.model.UuidHolder;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/java/")
public class RequestEntityController {
	private static final Logger LOG = Logger.getLogger(RequestEntityController.class.getName());

	@PostMapping(path = "requestEntity",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> complexObjectWithHeaderInsideEntity(
			RequestEntity<JavaPerson> entity) {
		JavaPerson person = entity.getBody();
		LOG.info(() -> String.format("Got Uuid [%s]", entity.getHeaders().get("uuid")));
		UuidHolder uuid = new UuidHolder(entity.getHeaders().getFirst("uuid"));
		LOG.info(() -> String.format("Got Uuid [%s]", uuid));
		LOG.info(() -> String.format("Got Person [%s]", person));

		return ResponseEntity.ok("Person is valid");
	}
}
