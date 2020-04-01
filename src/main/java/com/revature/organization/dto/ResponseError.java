package com.revature.organization.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseError {

	private HttpStatus status;

	private List<String> errors;

	public ResponseError(HttpStatus status, List<String> errors) {
		super();

		this.status = status;

		this.errors = errors;
	}

	public ResponseError(HttpStatus status, String error) {
		super();

		this.status = status;

		errors = Arrays.asList(error);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
