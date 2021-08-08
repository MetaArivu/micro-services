package com.user.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.adapter.entities.UserDetails;
import com.user.adapter.repository.UserRepository;
import com.user.domainlayer.service.UserService;
import com.user.dto.AuthRequest;
import com.user.dto.AuthResponse;
import com.user.exceptions.DuplicateRecordException;
import com.user.exceptions.InvalidInputException;
import com.user.server.secutiry.JWTUtil;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JWTUtil jtwUtil;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Mono<AuthResponse> login(AuthRequest authReq) {
		return userRepo.findByLoginIdAndActive(authReq.getLoginId(), true)
					   .filter(user -> 
					   		{
					   			return user.getPassword().equals(authReq.getPassword());
					   		})
					   .map(user -> {
						   String token = jtwUtil.generateToken(user);
						   return new AuthResponse(token);
					   });
	}
	
	@Override
	public Mono<UserDetails> findById(String id) {
		if(id == null) {
			Mono<UserDetails> fallback = Mono.error( new InvalidInputException("Invalid Id: "+id));
			return fallback;
		}
		return userRepo.findByIdAndActive(id, true);
	}
	
	
	@Override
	public Mono<UserDetails> save(UserDetails _user) {
		
		if(_user==null || !_user.isValid()) {
			Mono<UserDetails> fallback = Mono.error( new InvalidInputException(UserDetails.invalidMsg()));
			return fallback;
		}
		
		return userRepo.findByLoginIdAndActive(_user.getLoginId(), true)
				.flatMap(prd ->{
				    Mono<UserDetails> fallback = Mono.error(new DuplicateRecordException("Duplicate LoginId " + _user.getLoginId()));
					return fallback;
				})
				.switchIfEmpty(userRepo.save(_user));
	} }
