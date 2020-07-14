package com.zahangirbd.server.excep;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DoiItemInputErrorException.class)
	public final ResponseEntity<ErrorDetails> handleDoiItemInputErrorException(DoiItemInputErrorException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}	
	
	@ExceptionHandler(InternalErrorException.class)
	public final ResponseEntity<ErrorDetails> handleInternalErrorException(InternalErrorException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	@ExceptionHandler(DoiItemNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleDoiItemNotFoundException(DoiItemNotFoundException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}	

}
