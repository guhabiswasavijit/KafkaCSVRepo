package org.seven;

import org.apache.camel.ExchangePropertyKey;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component("CamelKafkaRoute")
@Slf4j
public class CamelKafkaRoute extends RouteBuilder {

	public void configure() throws Exception {                
        log.info("About to start route: Kafka Server -> Log ");
        BindyCsvDataFormat bindy = new BindyCsvDataFormat(PaytmTransaction.class);
        from("file://{{input_directory}}/?idempotent=true&move=backup/$simple{date:now:yyyyMMdd}/$simple{file:name}")
        .routeId("kafkaProducerRoute")
	    .onCompletion().onCompleteOnly()
	        .to("bean:mySqlDBHandler?method=onSuccess")
	    	.setBody(constant("file upload complete"))
	    	.end()
        .onException(Exception.class)
	    	.to("bean:mySqlDBHandler?method=onFailure")
	    	.setBody(constant("file upload failed"))
	    .log("Processing ${header.CamelFileNameOnly} "+ Thread.currentThread().getName())
 	    .split(body().tokenize("\\n")).to("direct:split").end()	;
	  
	 	from("direct:split")
	 	  .routeId("kafkaProducerRecordRoute")
		  .log("Split line ${body}")
		  .log("Insert ${header.CamelSplitIndex} of ${header.CamelSplitSize}")
		  .setProperty(ExchangePropertyKey.SPLIT_INDEX.getName(),simple("${header.CamelSplitIndex}"))
		  .choice()
		   	.when(simple("${exchangeProperty.CamelSplitIndex} != 0"))
		   	    .unmarshal(bindy)
		   	    .convertBodyTo(PaytmKafkaRecord.class)
		   		.to("kafka:{{paytm.topic}}?brokers={{kafka.host}}:{{kafka.port}}"
	                + "&keySerializer="+ org.apache.kafka.common.serialization.StringSerializer.class.getName() 
	                + "&valueSerializer="+ org.seven.ObjectSerializer.class.getName() 
	               )		   	  
				.log("Finished Transformation:"+body())
		   	.otherwise().log("Got body:"+body())
		  .endChoice()			  
	    .end();

    }
        
}
