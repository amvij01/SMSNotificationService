package com.notification.sms.model;

public class SendSmsRes {

	private String smsDeliveryStatus;
	private String messageId;
	
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
