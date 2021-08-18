package com.user.domainlayer.service;

import com.user.adapter.entities.UserDetails;
import com.user.dto.AuthRequest;
import com.user.dto.AuthResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	public Flux<UserDetails> allUsers();

	public Mono<UserDetails> findById(String id);
	
	public Mono<UserDetails> save(UserDetails _user);
	
	public Mono<AuthResponse> login(AuthRequest authReq);
}
