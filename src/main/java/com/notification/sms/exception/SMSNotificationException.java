package com.notification.sms.exception;


@SuppressWarnings("serial")
public class SMSNotificationException extends Exception {
	
	private String exceptiontype;
	private String exceptionMessage;



	public SMSNotificationException(String exceptiontype, String exceptionMessage) {
		super();
		this.exceptiontype = exceptiontype;
		this.exceptionMessage = exceptionMessage;
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
