package com.notification.sms.model;

public class SendSmsReq {
	
	private String destinationNumber;
	private String messageContent;
	private String deliveryRecieptIndicator;
	
	public String getDestinationNumber() {
		return destinationNumber;
	}
	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}
	public String getDeliveryRecieptIndicator() {
		return deliveryRecieptIndicator;
	}
	public void setDeliveryRecieptIndicator(String deliveryRecieptIndicator) {
		this.deliveryRecieptIndicator = deliveryRecieptIndicator;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	
	
	

}
