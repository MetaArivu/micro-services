package com.payment.adapter.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.payment.adapter.entities.Payment;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {

}
