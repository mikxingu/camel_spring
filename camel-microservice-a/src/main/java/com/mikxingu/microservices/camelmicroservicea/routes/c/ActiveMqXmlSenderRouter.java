package com.mikxingu.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqXmlSenderRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from ("file:files/xml")
		.log("${body}")
		.to("activemq:my-activemq-xml-queue");
		
	}
}
