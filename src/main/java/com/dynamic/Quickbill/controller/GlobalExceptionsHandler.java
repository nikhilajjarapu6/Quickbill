package com.dynamic.Quickbill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dynamic.Quickbill.exceptions.BadrequestException;
import com.dynamic.Quickbill.exceptions.BillerAuthenticationException;
import com.dynamic.Quickbill.exceptions.DuplicateResourceException;
import com.dynamic.Quickbill.exceptions.InvalidOrderException;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionsHandler {

	@ExceptionHandler(BadrequestException.class)
	public ResponseEntity<String> handleBadrequestException(BadrequestException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400_BAD_REQUEST "+exception.getMessage());
	}
	
	@ExceptionHandler(BillerAuthenticationException.class)
	public ResponseEntity<String> handleBillerAuthenticationException(BillerAuthenticationException exception){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401_UNAUTHORIZED "+exception.getMessage());
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException exception){
		return ResponseEntity.status(HttpStatus.CONFLICT).body("409_DUPLICATE_RESOURCE "+exception.getMessage());
	}
	
	@ExceptionHandler(InvalidOrderException.class)
	public ResponseEntity<String> handleInvalidOrderException(InvalidOrderException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400_INVALID_ORDER "+exception.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404_RESOURCE_NOT_FOUND "+exception.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	 public ResponseEntity<String> handleException(Exception exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred 500_INTERNAL_SERVER_ERROR"); 
	 }
}
