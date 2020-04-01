package com.revature.organization.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.organization.dto.ResponseError;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	// Handling Invalid Input Exceptions
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, errors);
		

		return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
	}
	
	
	//Handling Not readable Json Data
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error ="Invalid Json Data";
		return buildResponseEntity(new ResponseError(HttpStatus.BAD_REQUEST,error) );
	}
	  private ResponseEntity<Object> buildResponseEntity(ResponseError responseError) {
	       return new ResponseEntity<>(responseError, responseError.getStatus());
	   }



	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation( // This exception reports the result of constraint
																// violations
			ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<Object>(responseError, new HttpHeaders(), responseError.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(responseError, new HttpHeaders(), responseError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("path", request.getContextPath());

		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
	}

	 @ExceptionHandler(EntityNotFoundException.class)
	 protected ResponseEntity<Object> handleEntityNotFound(
	           EntityNotFoundException ex) {
		 ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, "No Record");
		
	       return buildResponseEntity(responseError);
	   }
	
	
	
	// Default Handler

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		 ResponseError responseError = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED, "Request Method Not Supported");
		return buildResponseEntity(responseError);
	}
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex,WebRequest request){
		ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		return new ResponseEntity<Object>(responseError, new HttpHeaders(), responseError.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong");
		return new ResponseEntity<Object>(responseError, new HttpHeaders(), responseError.getStatus());
	}







}




