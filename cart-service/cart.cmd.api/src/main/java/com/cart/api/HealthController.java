package com.cart.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.core.config.AppProperties;

@RestController
@RequestMapping("/")
public class HealthController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(HealthController.class);

	@Autowired
	private AppProperties appProp;

	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		log.debug("Welcome api called");
		return new ResponseEntity<String>(appProp.appInfo(), HttpStatus.OK);
	}
}
