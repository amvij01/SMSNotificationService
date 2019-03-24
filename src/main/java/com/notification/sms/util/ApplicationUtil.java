package com.notification.sms.util;

import com.notification.sms.exception.SMSNotificationException;
import com.notification.sms.exception.SendSmsError;
import com.notification.sms.model.SmsNotificationRequest;

public class ApplicationUtil {
	
	public static SendSmsError validateRequest(SmsNotificationRequest smsNotificationRequest) {
		SendSmsError sendSmsError = new SendSmsError();
		if(smsNotificationRequest.getSendSmsReq().getDestinationNumber().isEmpty()) {
			
			sendSmsError.setErrorType("Validation Error");
			sendSmsError.setErrorMessage("Destination Number is empty");
			return sendSmsError;
		}
		
		/*else if(smsNotificationRequest.getSendSmsReq().getDestinationNumber().get(0).length() != 11) {
		
			sendSmsError.setErrorType("Validation Error");
			sendSmsError.setErrorMessage("Destination Number is invalid");
			return sendSmsError;
		}*/
		
		else if(smsNotificationRequest.getSendSmsReq().getMessageContent().length() > 160) {
			
			sendSmsError.setErrorType("Validation Error");
			sendSmsError.setErrorMessage("Message Content length Exceeded");
			return sendSmsError;
		}
			
		else {
			return null;
		}
			
		
	}

	public static SendSmsError createException(SMSNotificationException e) {
		SendSmsError sendSmsException = new SendSmsError();
		sendSmsException.setErrorType(e.getExceptiontype());
		sendSmsException.setErrorMessage(e.getExceptionMessage());
		return sendSmsException;
	}

}
