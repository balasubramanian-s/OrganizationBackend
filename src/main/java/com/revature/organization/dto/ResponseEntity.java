package com.revature.organization.dto;



public class ResponseEntity {
	private int statusCode;
	private String description;
	private Object data;

	private String error;
	//private HttpStatus status;
	
	public ResponseEntity(int statusCode, String description, Object data) {
	
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
	}
	
	public ResponseEntity(int status,String error) {
		this.statusCode=status;
		this.error=error;
	}
	public ResponseEntity(int status,String description,String error) {
		this.statusCode=status;
		this.description = description;
		this.error=error;
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
	

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	
	
	

}
