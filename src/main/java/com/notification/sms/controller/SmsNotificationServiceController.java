package com.notification.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.notification.sms.model.SmsNotificationRequest;
import com.notification.sms.model.SmsNotificationResponse;
import com.notification.sms.service.SmsNotificationService;

@RestController
@RequestMapping("/notification")
public class SmsNotificationServiceController {
	
	@Autowired
	private SmsNotificationService smsNotificatonService;
	
	@RequestMapping(value = "/sms/send", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> getSMS(@RequestBody SmsNotificationRequest smsNotificationRequest) {
		
		SmsNotificationResponse smsNotificationResponse = smsNotificatonService.sendSMS(smsNotificationRequest);
		
		return new ResponseEntity<Object>(smsNotificationResponse,HttpStatus.OK);
	}

}
