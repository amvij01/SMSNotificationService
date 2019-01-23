package com.notification.sms.util;

import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.notification.sms.service.impl.MessageReceiverListenerImpl;

import java.io.IOException;

@Configuration
public class CreateSmppConnection {

	private static final Logger logger = LoggerFactory.getLogger( CreateSmppConnection.class );


	@Value( "${spring.smpp.server}" )
	private String host;

	@Value( "${spring.smpp.port}" )
	private int port;

	@Value( "${spring.smpp.system.id}" )
	private String systemId;

	@Value( "${spring.smpp.password}" )
	private String password;

	@Value( "${spring.smpp.server.type}" )
	private String serverType;
	
	public SMPPSession initConnection() {
		
		SMPPSession session = new SMPPSession();
		
		try {
			session.setMessageReceiverListener(new MessageReceiverListenerImpl());

			String systemNo = session.connectAndBind(host, port, new BindParameter(BindType.BIND_TRX, systemId, password, serverType,TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));

			logger.info("Connected with SMPP with system id {}" + systemNo);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("I/O error occured", e);
			session = null;
		}
		return session;
	}

}
