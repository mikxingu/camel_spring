package com.mikxingu.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.mikxingu.microservices.camelmicroserviceb.CurrencyExchange;

//@Component
public class ActiveMqXmlRecieverRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from ("activemq:my-activemq-xml-queue")
		.unmarshal()
		.jacksonxml(CurrencyExchange.class)
//		.log("recieved message from xml queue")
		.to("log:recieved-xml-message");
		
	}
	
	

}
