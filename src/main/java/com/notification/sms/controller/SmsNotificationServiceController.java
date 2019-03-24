package com.notification.sms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.notification.sms.client.SmsNotificationServiceClient;
import com.notification.sms.exception.SMSNotificationException;
import com.notification.sms.exception.SendSmsError;
import com.notification.sms.model.SmsNotificationRequest;
import com.notification.sms.model.SmsNotificationResponse;
import com.notification.sms.util.ApplicationUtil;

@RestController
@RequestMapping("/notification")
public class SmsNotificationServiceController {

	private static final Logger logger = LoggerFactory.getLogger( SmsNotificationServiceController.class );

	@Autowired
	private SmsNotificationServiceClient smsNotificationServiceClient;

	@RequestMapping(value = "/sms/send", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> getSMS(@RequestBody SmsNotificationRequest smsNotificationRequest) {

		logger.info("BEGIN : Inside Controller : getSMS()");

		SendSmsError sendSmsError = ApplicationUtil.validateRequest(smsNotificationRequest);

		if(sendSmsError == null) {

			SmsNotificationResponse smsNotificationResponse;
			try {
				smsNotificationResponse = smsNotificationServiceClient.sendSMS(smsNotificationRequest);
			} catch (SMSNotificationException e) {
				SendSmsError sendSmsException  = ApplicationUtil.createException(e);
				return new ResponseEntity<Object>(sendSmsException,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<Object>(smsNotificationResponse,HttpStatus.OK);
		}
		else {	
			return new ResponseEntity<Object>(sendSmsError,HttpStatus.BAD_REQUEST);				
		}
	}

}
