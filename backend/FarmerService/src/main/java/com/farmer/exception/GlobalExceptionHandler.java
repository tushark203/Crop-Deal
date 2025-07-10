package com.farmer.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException ex) {
      
//        HttpStatus status = HttpStatus.resolve(ex.status());
//        if (status == null) {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        String message = ex.getMessage();
//
//
//        if (message == null || message.isEmpty()) {
//            message = "You have not published any crops yet " + ex.status();
//        }

     
        return new ResponseEntity<>("You have not published crops yet",HttpStatus.NOT_FOUND);
    }

 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	

}
