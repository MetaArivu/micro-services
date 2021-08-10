package com.review.adapter.controller.v1;

import static com.review.constants.APIConstant.PROD_REVIEW_V1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.review.adapter.entities.ProductReview;
import com.review.domainlayer.service.ReviewService;
import com.review.dto.Response;
import com.review.exceptions.InvalidInputException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PROD_REVIEW_V1)
public class ReviewController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	private ReviewService reviewSvc;
 
	

	@PostMapping(value = "/")
	public Mono<ResponseEntity<Response<ProductReview>>> save(@RequestBody ProductReview review) {
		
			return reviewSvc.save(review)
					.map(prd -> {
						return new ResponseEntity<Response<ProductReview>>(
								new Response<ProductReview>(true, "Record Saved Successully", prd), HttpStatus.OK);
					})
					.defaultIfEmpty(new ResponseEntity<Response<ProductReview>>(
							new Response<ProductReview>(false, "Record Not Saved Successully"), HttpStatus.NOT_FOUND));
			
	}


	@DeleteMapping(value = "/{id}")
	public Mono<ResponseEntity<Response<ProductReview>>> delete(@PathVariable("id") String id) {
		return reviewSvc.delete(id)
				.map(prd ->{
					return new ResponseEntity<Response<ProductReview>>(
							new Response<ProductReview>(true, "Record Deleted Successully", prd), HttpStatus.OK);
				})
				.defaultIfEmpty(new ResponseEntity<Response<ProductReview>>(
						new Response<ProductReview>(false, "Record Not Deleted Successully"), HttpStatus.NOT_FOUND));

	}

}
