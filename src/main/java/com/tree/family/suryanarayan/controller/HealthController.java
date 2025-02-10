package com.tree.family.suryanarayan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/status")
	public ResponseEntity<String> status() {
		return ResponseEntity.ok("UP");
	}
	
}
