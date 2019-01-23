package com.notification.sms.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Address;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.SubmitMultiResult;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notification.sms.model.DeliveryReceiptState;
import com.notification.sms.model.SendSmsError;
import com.notification.sms.model.SendSmsRes;
import com.notification.sms.model.SmsNotificationRequest;
import com.notification.sms.model.SmsNotificationResponse;
import com.notification.sms.service.SmsNotificationService;
import com.notification.sms.util.CreateSmppConnection;



@Service
public class SmsNotificationServiceImpl implements SmsNotificationService {

	private static final Logger logger = LoggerFactory.getLogger( SmsNotificationServiceImpl.class );

	private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();



	@Value( "${spring.smpp.source.number}" )
	private String sourceNumber;

	private String messageId;
	@Autowired
	CreateSmppConnection createSmppConnection;

	@Override
	public SmsNotificationResponse sendSMS(SmsNotificationRequest smsNotificationRequest) {
		// TODO Auto-generated method stub

		final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
		registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);
		SMPPSession smppSession = createSmppConnection.initConnection();

		SmsNotificationResponse smsNotificationResponse = new SmsNotificationResponse();
		SendSmsRes sendSmsRes = new SendSmsRes(); 
		final String destinationNumber = smsNotificationRequest.getSendSmsReq().getDestinationNumber();

		logger.info( "BEGIN : Inside Implementation : " + destinationNumber );
		
		if(smppSession != null) {
			try {

				smppSession.setMessageReceiverListener(new MessageReceiverListenerImpl());

				messageId = smppSession.submitShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
						NumberingPlanIndicator.UNKNOWN, sourceNumber, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN,
						smsNotificationRequest.getSendSmsReq().getDestinationNumber(), new ESMClass(), (byte)0, (byte)1, timeFormatter.format(new Date()), null,
						registeredDelivery, (byte)0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1,
								false), (byte)0, smsNotificationRequest.getSendSmsReq().getMessageContent().getBytes());
				

				
//				DataSmResult result = smppSession.dataShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
//						NumberingPlanIndicator.UNKNOWN, sourceNumber, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN,
//						smsNotificationRequest.getSendSmsReq().getDestinationNumber(), new ESMClass(),
//						registeredDelivery, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1,
//								false),null);

				logger.info( "Message submitted, message_id is " + messageId);

				if(messageId.isEmpty() || messageId.equals("") || messageId.equals(null)) {
					logger.error("Pushed message to broker failed");			
				} else {
					logger.info("Pushed message to broker successfully");
					sendSmsRes.setMessageId(messageId);
					sendSmsRes.setSmsDeliveryStatus("Success");
					smsNotificationResponse.setSendSmsRes(sendSmsRes);
				}

			}  catch (PDUException e) {
				logger.error("Invalid PDU parameter", e);
			} catch (ResponseTimeoutException e) {
				logger.error("Response timeout", e);
			} catch (InvalidResponseException e) {
				logger.error("Receive invalid response", e);
			} catch (NegativeResponseException e) {
				logger.error("Receive negative response", e);
			} catch (IOException e) {
				logger.error("I/O error occured", e);
			} catch (IllegalArgumentException e) {
				logger.error("Exception occured submitting SMPP request", e);
			}
		}
		else {
			logger.error("Session creation failed with SMPP broker.");
		}

//		if(smppSession != null) {
//			smppSession.unbindAndClose();
//			logger.info("!finish");
//		}

		return smsNotificationResponse;
	}

	public SendSmsError validateRequest(SmsNotificationRequest smsNotificationRequest) {
		SendSmsError sendSmsError = new SendSmsError();
		if(smsNotificationRequest.getSendSmsReq().getDestinationNumber().isEmpty()) {
			
			sendSmsError.setErrorType("Validation Error");
			sendSmsError.setErrorMessage("Destination Number is empty");
			return sendSmsError;
		}
		
		else if(smsNotificationRequest.getSendSmsReq().getDestinationNumber().length() != 11) {
		
			sendSmsError.setErrorType("Validation Error");
			sendSmsError.setErrorMessage("Destination Number is invalid");
			return sendSmsError;
		}
		
		else if(smsNotificationRequest.getSendSmsReq().getMessageContent().length() > 160) {
			
			sendSmsError.setErrorType("Validation Error");
			sendSmsError.setErrorMessage("Message Content length Exceeded");
			return sendSmsError;
		}
			
		else {
			return null;
		}
			
		
	}
}
