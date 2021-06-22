package com.mikxingu.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {
	
	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		from("timer:first-timer")
		.log("${body}")
		.transform().constant("My constant Message")
		.log("${body}")
		//.transform().constant("My constant Message")
		//.transform().constant("Time now is " + LocalDateTime.now())
		//.bean("getCurrentTimeBean")
		//.bean(getCurrentTimeBean)
		.bean(getCurrentTimeBean)
		.log("${body}")
		.bean(loggingComponent)
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer");
	}
}

@Component
class GetCurrentTimeBean{
	public String getCurrentTime() {
		return "Time now is " + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent{
	
	public void process(String message) {
		
	}
}

class SimpleLoggingProcessor implements Processor {

	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimplelogginProcessingComponent {}", exchange.getMessage().getBody());
	}
}

