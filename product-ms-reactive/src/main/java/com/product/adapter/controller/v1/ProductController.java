package com.product.adapter.controller.v1;

import static com.product.constants.APIConstant.PROD_V1;

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

import com.product.adapter.entities.Products;
import com.product.domainlayer.service.ProductService;
import com.product.dto.Response;
import com.product.exceptions.InvalidInputException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PROD_V1)
public class ProductController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService prdSvc;
 
	@GetMapping(value = "/")
	public Mono<ResponseEntity<Response<List<Products>>>> allProducts() {
		log.debug("Executing get all products");
		return prdSvc.allProducts()
				.collectList()
				.map(prds -> new ResponseEntity<Response<List<Products>>>(
						new Response<List<Products>>(true, "Record retrieved successully", prds), HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<Response<List<Products>>>(
						new Response<List<Products>>(false, "Record not found"), HttpStatus.NOT_FOUND));
	}

	@GetMapping(value = "/{id}")
	public Mono<ResponseEntity<Response<Products>>> findById(@PathVariable("id") String id) {
		
			return prdSvc.findById(id)
					.map(prd -> new ResponseEntity<Response<Products>>(
							new Response<Products>(true, "Record retrieved successully", prd), HttpStatus.OK))
					.defaultIfEmpty(new ResponseEntity<Response<Products>>(
							new Response<Products>(false, "Record not found"), HttpStatus.NOT_FOUND));
		
	}

	@PostMapping(value = "/")
	public Mono<ResponseEntity<Response<Products>>> save(@RequestBody Products product) {
		
			return prdSvc.save(product)
					.map(prd -> {
						return new ResponseEntity<Response<Products>>(
								new Response<Products>(true, "Record Saved Successully", prd), HttpStatus.OK);
					})
					.defaultIfEmpty(new ResponseEntity<Response<Products>>(
							new Response<Products>(false, "Record Not Saved Successully"), HttpStatus.NOT_FOUND));
			
	}

	@PutMapping(value = "/{id}")
	public Mono<ResponseEntity<Response<Products>>> update(@PathVariable("id") String id, @RequestBody Products product) {
		return prdSvc.update(id, product)
					.map(prd ->{
						return new ResponseEntity<Response<Products>>(
								new Response<Products>(true, "Record Updated Successully", prd), HttpStatus.OK);
					})
					.defaultIfEmpty(new ResponseEntity<Response<Products>>(
							new Response<Products>(false, "Record Not Updated Successully"), HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(value = "/{id}")
	public Mono<ResponseEntity<Response<Products>>> delete(@PathVariable("id") String id) {
		return prdSvc.delete(id)
				.map(prd ->{
					return new ResponseEntity<Response<Products>>(
							new Response<Products>(true, "Record Deleted Successully", prd), HttpStatus.OK);
				})
				.defaultIfEmpty(new ResponseEntity<Response<Products>>(
						new Response<Products>(false, "Record Not Deleted Successully"), HttpStatus.NOT_FOUND));

	}

}
