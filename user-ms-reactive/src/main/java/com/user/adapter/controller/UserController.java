package com.user.adapter.controller;

import static com.user.constants.APIConstant.V1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.adapter.entities.UserDetails;
import com.user.domainlayer.service.UserService;
import com.user.dto.AuthRequest;
import com.user.dto.AuthResponse;
import com.user.dto.Response;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(V1)
public class UserController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userSvc;

	@PostMapping(value = "/")
	public Mono<ResponseEntity<Response<UserDetails>>> save(@RequestBody UserDetails product) {

		return userSvc.save(product)
				.map(user -> {
					return new ResponseEntity<Response<UserDetails>>(
					new Response<UserDetails>(true, "Record Saved Successully", user), HttpStatus.OK);})
				.defaultIfEmpty(new ResponseEntity<Response<UserDetails>>(new Response<UserDetails>(false, "Record Not Saved Successully"), HttpStatus.NOT_FOUND));

	}
	
	
	@PostMapping(value = "/login")
	public Mono<ResponseEntity<Response<AuthResponse>>> login(@RequestBody AuthRequest authReq) {

		return userSvc.login(authReq)
				.map(user -> {
					return new ResponseEntity<Response<AuthResponse>>(
					new Response<AuthResponse>(true, "User Authenticated Successfully", user), HttpStatus.OK);})
				.defaultIfEmpty(new ResponseEntity<Response<AuthResponse>>(
						new Response<AuthResponse>(false, "Invalid User Details"), HttpStatus.UNAUTHORIZED));

	}

}
