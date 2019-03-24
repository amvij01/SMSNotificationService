package com.notification.sms.model;

import java.util.ArrayList;

public class SendSmsReq {
	
	private ArrayList<String> destinationNumber;
	private String messageContent;
	private String deliveryRecieptIndicator;
	

	public ArrayList<String> getDestinationNumber() {
		return destinationNumber;
	}
	public void setDestinationNumber(ArrayList<String> destinationNumber) {
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
