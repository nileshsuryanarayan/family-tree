package com.tree.family.suryanarayan.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ErrorResponse implements Serializable {

	private String errCode;
	private String errMessage;
	private String description;
	
	public ErrorResponse() {}
	
	public ErrorResponse(String errCode, String errMessage) {
		super();
		this.errCode = errCode;
		this.errMessage = errMessage;
	}
	
	public ErrorResponse(String errCode, String errMessage, String description) {
		super();
		this.errCode = errCode;
		this.errMessage = errMessage;
		this.description = description;
	}
}
