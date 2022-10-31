package br.com.roberto2aj.apistarwars.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiStarWarsExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<Object> handleNotFound(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(CommunicationException.class)
	protected ResponseEntity<Object> handleCommunicationError(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}