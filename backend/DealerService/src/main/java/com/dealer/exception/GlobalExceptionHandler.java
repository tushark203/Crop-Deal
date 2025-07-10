package com.dealer.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException ex) {
    	 int statusCode = ex.status(); 
    	    String message;

    	    try {
    	        message = ex.contentUTF8();
    	        
    	        if (message == null || message.isEmpty()) {
    	            message = "Unknown error occurred from remote service.";
    	        }

    	    } catch (Exception e) {
    	        message = "An error occurred while contacting the crop service.";
    	    }

    	    String response = message;
    	    return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }

 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
