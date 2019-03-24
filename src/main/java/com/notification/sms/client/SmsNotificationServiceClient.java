package com.notification.sms.client;

import java.util.Iterator;

import javax.xml.bind.JAXBElement;
import javax.xml.soap.Detail;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.notification.sms.exception.SMSNotificationException;
import com.notification.sms.generated.ObjectFactory;
import com.notification.sms.generated.SendSms;
import com.notification.sms.generated.SendSmsResponse;
import com.notification.sms.generated.ServiceException;
import com.notification.sms.model.SendSmsRes;
import com.notification.sms.model.SmsNotificationRequest;
import com.notification.sms.model.SmsNotificationResponse;

public class SmsNotificationServiceClient extends WebServiceGatewaySupport {

	private static final Logger logger = LoggerFactory.getLogger( SmsNotificationServiceClient.class );

	@Value("${spring.ws.url}")
	private String url;

	@Value("${spring.ws.source.number}")
	private String sourceNumber;

	public SmsNotificationResponse sendSMS(SmsNotificationRequest smsNotificationRequest) throws SMSNotificationException {


		SmsNotificationResponse smsNotificationResponse = new SmsNotificationResponse();
		SendSmsRes sendSmsRes = new SendSmsRes();
		SendSms request = new SendSms();

		request.setAddresses(smsNotificationRequest.getSendSmsReq().getDestinationNumber());
		request.setMessage(smsNotificationRequest.getSendSmsReq().getMessageContent());
		request.setSenderName("tel:" + sourceNumber);

		logger.info("Destination No. is :  " + request.getAddresses().get(0));

		try {

			@SuppressWarnings("unchecked")
			JAXBElement<SendSmsResponse> jaxbResponse =(JAXBElement<SendSmsResponse>) getWebServiceTemplate()
			.marshalSendAndReceive(url, new ObjectFactory().createSendSms(request));
			SendSmsResponse response = jaxbResponse.getValue();

			if(response.getResult() != null) {
				sendSmsRes.setMessageId(response.getResult());
				sendSmsRes.setSmsDeliveryStatus("SUCCESS");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception :" + e.getMessage());
			if (e instanceof SoapFaultClientException) {
                //String faultString = ((SoapFaultClientException) e).getFaultStringOrReason();
				
				ServiceException serviceException = new ServiceException();
				SoapFaultDetail detail =  ((SoapFaultClientException) e).getSoapFault().getFaultDetail();
				
				Iterator<SoapFaultDetailElement> detailEntries = detail.getDetailEntries();
				
				String faultString = "FMW error : A service error occurred. Error code is %1";
                String faultCode = ((SoapFaultClientException) e).getFaultCode().toString();
                throw new SMSNotificationException(faultCode, faultString);
            }
			throw new SMSNotificationException(e.getClass().getName(),e.getMessage());
		}
		smsNotificationResponse.setSendSmsRes(sendSmsRes);

		return smsNotificationResponse;
	}
}
