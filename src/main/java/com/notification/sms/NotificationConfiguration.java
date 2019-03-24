package com.notification.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import com.notification.sms.client.SmsNotificationServiceClient;


import org.apache.http.auth.UsernamePasswordCredentials;

@Configuration
public class NotificationConfiguration {
	
	@Value("${spring.ws.url}")
    private String url;
	
	@Value("${spring.http.basic-auth.user}")
    private String httpBasicAuthUser;
	
	@Value("${spring.http.basic-auth.password}")
    private String httpBasicAuthPassword;
	
	@Value("${spring.ws.security.user}")
    private String wsSecurityUser;
	
	@Value("${spring.ws.security.password}")
    private String wsSecurityPassword;

		@Bean
		public Jaxb2Marshaller marshaller() {
			Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
			marshaller.setContextPath("com.notification.sms.generated");
			return marshaller;
		}

		@Bean
		public SmsNotificationServiceClient smsNotificationServiceClient(Jaxb2Marshaller marshaller) {
			SmsNotificationServiceClient client = new SmsNotificationServiceClient();
			client.setDefaultUri(url);
			client.setMarshaller(marshaller);
			client.setUnmarshaller(marshaller);
			client.setMessageSender(httpComponentsMessageSender());
			ClientInterceptor[] interceptors = {securityInterceptor()};
			client.setInterceptors(interceptors);
			return client;
		}
		
		  @Bean
		  public HttpComponentsMessageSender httpComponentsMessageSender() {
		    HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
		    // set the basic authorization credentials
		    httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());
		    return httpComponentsMessageSender;
		  }

		  @Bean
		  public UsernamePasswordCredentials usernamePasswordCredentials() {
		    // pass the user name and password to be used
			  UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(httpBasicAuthUser, httpBasicAuthPassword);
		    return usernamePasswordCredentials;
		  }
		  
		    @Bean
		    public Wss4jSecurityInterceptor securityInterceptor() {
		    Wss4jSecurityInterceptor securityInterceptor = new   Wss4jSecurityInterceptor();
		    securityInterceptor.setSecurementActions("UsernameToken");
		    securityInterceptor.setSecurementUsername(wsSecurityUser);
		    securityInterceptor.setSecurementPassword(wsSecurityPassword);
		    securityInterceptor.setSecurementPasswordType("PasswordText");
		    securityInterceptor.setSecurementUsernameTokenCreated(false);
		    return securityInterceptor;
	}
}
