package com.notification.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("SmsNotificationRequest")
public class SmsNotificationRequest {
	
	@JsonProperty("SendSmsReq")
	private SendSmsReq sendSmsReq;

	public SendSmsReq getSendSmsReq() {
		return sendSmsReq;
	}

	public void setSendSmsReq(SendSmsReq sendSmsReq) {
		this.sendSmsReq = sendSmsReq;
	}


}
