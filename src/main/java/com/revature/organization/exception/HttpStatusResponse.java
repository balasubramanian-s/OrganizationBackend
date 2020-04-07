package com.revature.organization.exception;

import java.util.List;

import com.revature.organization.model.Organization;

public class HttpStatusResponse {
	private int statusCode;
	private String description;
	private Object data;

	
	
	
	public HttpStatusResponse(int value, String string, List list) {
		this.statusCode=value;
		this.description=string;
		this.data=list;
	}
	public HttpStatusResponse(int value, String string, Object data) {
		this.statusCode=value;
		this.description=string;
		this.data=data;
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

}
