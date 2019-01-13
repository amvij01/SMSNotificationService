package com.notification.sms.service.impl;

import java.io.IOException;
import java.util.Date;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.QuerySmResult;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notification.sms.model.SendSmsRes;
import com.notification.sms.model.SmsNotificationRequest;
import com.notification.sms.model.SmsNotificationResponse;
import com.notification.sms.service.SmsNotificationService;
import com.notification.sms.util.CreateSmppConnection;



@Service
public class SmsNotificationServiceImpl implements SmsNotificationService {

	private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();

	@Value( "${spring.smpp.source.number}" )
	private String sourceNumber;
	private String messageId;
	

	@Autowired
	CreateSmppConnection createSmppConnection;

	@Override
	public SmsNotificationResponse sendSMS(SmsNotificationRequest smsNotificationRequest) {
		// TODO Auto-generated method stub

		final String deliveryRecieptIndicator = smsNotificationRequest.getSendSmsReq().getDeliveryRecieptIndicator();
		final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
		
		if (deliveryRecieptIndicator == "Y") {			
			registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);
		}
		else if (deliveryRecieptIndicator == "N") {			
			registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.DEFAULT);
		}
		
		SMPPSession smppSession = createSmppConnection.initConnection();
		SmsNotificationResponse smsNotificationResponse = new SmsNotificationResponse();
		SendSmsRes sendSmsRes = new SendSmsRes(); 
		
		try {
			System.out.println("Inside Implementation");
			messageId = smppSession.submitShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
					NumberingPlanIndicator.UNKNOWN, sourceNumber, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN,
					smsNotificationRequest.getSendSmsReq().getDestinationNumber(), new ESMClass(), (byte)0, (byte)1, timeFormatter.format(new Date()), null,
					registeredDelivery, (byte)0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1,
							false), (byte)0, smsNotificationRequest.getSendSmsReq().getMessageContent().getBytes());
			System.out.println("Message submitted, message_id is " + messageId);
			
			//QuerySmResult message =  smppSession.queryShortMessage(messageId, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, "27820000209");
			//System.out.println("Message retrieved, message is " + message.toString());
			
			
			
		} catch (IllegalArgumentException | PDUException | ResponseTimeoutException | InvalidResponseException
				| NegativeResponseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			smppSession.unbindAndClose();
			System.out.println("finish!");
		}
		
		sendSmsRes.setMessageId(messageId);
		sendSmsRes.setSmsDeliveryStatus("Success");
		smsNotificationResponse.setSendSmsRes(sendSmsRes);
		return smsNotificationResponse;
	}



}
