package com.example.springboot.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestfulExampleRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		configureRestAPI();
		
		simplesRestExample();
	}
	
	/**
	 * Configure the rest API service to enable camel to work with restful
	 */
	private void configureRestAPI() {
		restConfiguration().contextPath("/camel/*")
			.port(8080)
			.enableCORS(true) //Only for test purpose
			.bindingMode(RestBindingMode.json)
			.component("servlet")
			.apiContextPath("/api-doc").apiContextRouteId("doc-api");
	}
	
	/**
	 * Simple example using camel to expose a HTTP route
	 */
	private void simplesRestExample() {
		rest("/user").description("User rest service")
			.get("/hello").description("Say Hello")
				.to("direct:hello");
		
		from("direct:hello").transform()
			.constant("Hello!")
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}

}
