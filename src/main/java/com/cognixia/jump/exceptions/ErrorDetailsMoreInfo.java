package com.cognixia.jump.exceptions;

import java.util.Date;

public class ErrorDetailsMoreInfo {

	private Date timeStamp;
	private String message;
	private String details;
	private Integer status;
	public ErrorDetailsMoreInfo(Date timeStamp, String message, String details, Integer status) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
		this.status = status;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	public Integer getStatus() {
		return status;
	}
	
	
}
