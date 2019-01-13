package com.notification.sms.service;

import com.notification.sms.model.SmsNotificationRequest;
import com.notification.sms.model.SmsNotificationResponse;

public interface SmsNotificationService {
	
	public SmsNotificationResponse sendSMS(SmsNotificationRequest smsNotificationRequest);
}
