package com.mikxingu.microservices.camelmicroservicea.routes.b;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder{
	
	@Autowired
	private DeciderBean deciderBean;
	
	@Override
	public void configure() throws Exception{
		from("file:files/input")
		.routeId("Files-Input-Route")
		.transform().body(String.class)
		.choice()
			.when(simple("${file:ext} == 'xml'")) //USE OF SIMPLE LANGUAGE
				.log("XML FILE FOUND")
			//.when(simple("${body} contains 'USD'")) //USE OF SIMPLE LANGUAGE
				.when(method(deciderBean)) 
				.log("Not an XML file but contains USD on body")
			.otherwise()
				.log("NOT AN XML FILE... IGNORING.")
		.end()
//		.to("direct://log-file-values")
		.to("file:files/output");
		
		from("direct:log-file-values")
		.log("${messageHistory} ${file:absolute.path}")
		.log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
		.log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
		.log("${file:size} ${file:modified}")
		.log("${routeId} ${camelId} ${body}");
	}
	
}

@Component
class DeciderBean{
	
	Logger logger = LoggerFactory.getLogger(DeciderBean.class);
	
	public boolean isThisConditionMet(@Body String body,
			@Headers Map<String,String> headers) {
		logger.info("DeciderBean {} {}",
				body, headers);
		return true;
	}
}
