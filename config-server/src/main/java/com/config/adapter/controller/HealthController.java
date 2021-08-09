package com.config.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.AppProperties;

@RestController
@RequestMapping("/")
public class HealthController {

	@Autowired
	private AppProperties appProp;

	@GetMapping("/")
	public ResponseEntity<String> welcome() {
		return new ResponseEntity<String>(appProp.appInfo(), HttpStatus.OK);
	}
}
