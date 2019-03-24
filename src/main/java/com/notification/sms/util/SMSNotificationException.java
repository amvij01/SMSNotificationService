package com.notification.sms.util;


@SuppressWarnings("serial")
public class SMSNotificationException extends Exception {
	
	private String exceptiontype;
	private String exceptionMessage;

	public SMSNotificationException(Exception e) {
		exceptiontype = e.getClass().getName();
		exceptionMessage = e.getMessage();
		
	}

	public String getExceptiontype() {
		return exceptiontype;
	}

	public void setExceptiontype(String exceptiontype) {
		this.exceptiontype = exceptiontype;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	

}
