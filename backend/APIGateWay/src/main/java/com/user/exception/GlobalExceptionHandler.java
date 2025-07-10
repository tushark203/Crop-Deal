package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleJwtExpiration(ExpiredJwtException ex) {
        return new ResponseEntity<>("You must be logged in to perform this action.", HttpStatus.BAD_REQUEST);
    }
	

	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleOtherExceptions(Exception ex) {
//	    Throwable cause = ex.getCause();
//
//	  
//	    if (cause instanceof org.springframework.web.reactive.function.client.WebClientResponseException) {
//	        var webEx = (org.springframework.web.reactive.function.client.WebClientResponseException) cause;
//	        return ResponseEntity
//	                .status(webEx.getStatusCode())
//	                .body(webEx.getResponseBodyAsString());
//	    }
//
//	    return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
