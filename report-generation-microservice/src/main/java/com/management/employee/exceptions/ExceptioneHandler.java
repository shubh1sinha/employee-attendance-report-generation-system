package com.management.employee.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptioneHandler {
	
	@ExceptionHandler(InvalidSearchException.class)
	public final ResponseEntity<Object> handleInvalidSearchException(InvalidSearchException ex , WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				ExceptionMessageConstants.INVALID_SEARCH_EXCEPTION, ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public final ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex , WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				ExceptionMessageConstants.INVALID_Request_EXCEPTION, ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

}
