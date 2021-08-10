package com.review.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.adapter.entities.ProductReview;
import com.review.adapter.repository.ReviewRepository;
import com.review.domainlayer.service.ReviewService;
import com.review.exceptions.DuplicateRecordException;
import com.review.exceptions.InvalidInputException;

import brave.propagation.ExtraFieldPropagation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewServiceImpl implements ReviewService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ReviewServiceImpl.class);

	@Autowired
	private ReviewRepository prdReviewRepo;
	

	@Override
	public Flux<ProductReview> reviewsByProduc(String _id){
		
		if(_id == null) {
			Flux<ProductReview> fallback = Flux.error( new InvalidInputException("Invalid Id="+_id));
			return fallback;
		}
		
		return prdReviewRepo.findByProductIdAndActive(_id, true);
	}

	@Override
	public Mono<ProductReview> save(ProductReview _prdReview) {
		
		if(_prdReview==null || !_prdReview.isValid()) {
			Mono<ProductReview> fallback = Mono.error( new InvalidInputException(ProductReview.invalidMsg()));
			return fallback;
		}
		
		return prdReviewRepo.save(_prdReview);
	}


	@Override
	public Mono<ProductReview> delete(String _id) {
		
		return prdReviewRepo.findById(_id)
				.map((prd) ->{
					prd.setActive(false);
					return prd;
				})
				.flatMap(prd ->{
					return prdReviewRepo.save(prd);
				});
	}
	
	

}
