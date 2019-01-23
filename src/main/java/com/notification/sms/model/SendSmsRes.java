package com.notification.sms.model;

public class SendSmsRes {

	private String smsDeliveryStatus;
	private String messageId;
	
	public SendSmsRes(String smsDeliveryStatus, String messageId) {
		super();
		this.smsDeliveryStatus = smsDeliveryStatus;
		this.messageId = messageId;
	}
	
	public SendSmsRes() {
		// TODO Auto-generated constructor stub
	}

	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSmsDeliveryStatus() {
		return smsDeliveryStatus;
	}
	public void setSmsDeliveryStatus(String smsDeliveryStatus) {
		this.smsDeliveryStatus = smsDeliveryStatus;
	}
}
