package com.user.adapter.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.user.adapter.entities.UserDetails;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDetails, String> {

	public Mono<UserDetails> findByIdAndActive(String id, boolean active);
	
	public Flux<UserDetails> findByActive(boolean active);

	public Mono<UserDetails> findByLoginIdAndActive(String loginId, boolean active);
}
