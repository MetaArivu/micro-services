package com.product.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.product.dto.Response;
import com.product.exceptions.DuplicateRecordException;
import com.product.exceptions.InvalidInputException;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ControllerExceptionHandler.class);


	@ExceptionHandler
	public ResponseEntity<Response<String>> handleException(InvalidInputException e){
		log.warn("InvalidInputException: {}",e.getMessage());
		return new ResponseEntity<Response<String>>(new Response<String>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<Response<String>> handleException(DuplicateRecordException e){
		log.warn("DuplicateRecordException: {}",e.getMessage());
		return new ResponseEntity<Response<String>>(new Response<String>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<Response<String>> handleException(Exception e){
		log.error("Exception: {}"+e.getMessage());
		return new ResponseEntity<Response<String>>(new Response<String>(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
