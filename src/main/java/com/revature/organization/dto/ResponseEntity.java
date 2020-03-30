package com.revature.organization.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseEntity {
	private int statusCode;
	private String description;
	private Object data;
	private List<String> errors;
	
	
	public ResponseEntity(int statusCode, String description, Object data) {
	
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
	}
	public ResponseEntity(int status,List<String> errors) {
		this.statusCode=status;
		this.errors=errors;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}


	
	
	

}
