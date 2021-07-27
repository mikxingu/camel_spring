package com.mikxingu.microservices.camelmicroservicea.routes.patterns;

import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter extends RouteBuilder{

	@Autowired
	SplitterComponent splitter;
	
	@Autowired
	DynamicRouterBean dynamicRouterBean;

	@Override
	public void configure() throws Exception {
		
// MULTICAST PATTERN ROUTE
//		from ("timer:multicast?period=10000")
//		.multicast()
//		.to("log:something1", "log:something2");
		
//SPLIT PATTERN ROUTE
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("activemq:split-queue");
		
		//Message, Message2, Message3
//		from("file:files/csv")
//		.convertBodyTo(String.class)
////		.split(body(), ",")
//		.split(method(splitter))
//		.to("activemq:split-queue");
		
		//AGGREGATE
//		MESSAGES -> AGGREGATE => ENDPOINT
//		from("file:files/aggregate-json")
//		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//		.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
//		.completionSize(3)
////		.completionTimeout(null)
//		.to("log:aggregate-json");
		
		String routeSlip = "direct:endpoint1, direct:endpoint2";
//		String routeSplip = "direct:endpoint1, direct:endpoint2, direct:endpoint3";
		
//		ROUTING SLIP PATTERN ROUTE
//		from("timer:routingSlip:period=100000")
//		.transform().constant("My message is hardcoded")
//		.routingSlip(simple(routeSlip));
//		
//		from ("direct:endpoint1")
//		.to("log:directendpoint1");
//		
//		from ("direct:endpoint2")
//		.to("log:directendpoint2");
//		
//		from ("direct:endpoint3")
//		.to("log:directendpoint3");
		
		//DYNAMIC ROUTING
		//step 1, step2, step3
		
		from("timer:dynamicRouting?period=10000")
		.transform().constant("My message is hardcoded")
		.dynamicRouter(method(dynamicRouterBean));
		
		//endpoint1
		
		//endpoint2
		
		//endpoint3
	}

}

@Component
class SplitterComponent{
	public List<String> splitInput(String body){
		return List.of("ABC", "DEF", "GHI");
	}
}

@Component
class DynamicRouterBean{
	
	Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);
	
	int invocations;
	
	public String decideTheNextEndpoint(
			@ExchangeProperties Map<String, String> properties,
			@Headers Map<String, String> headers,
			@Body String body) {
		logger.info("{} {} {}", properties, headers, body);
		invocations++;
		
		if (invocations%3==0)
			return "direct:endpoint1";
		
		if (invocations%3==1)
			return "direct:endpoint2";
		
		return null;
	}
}
