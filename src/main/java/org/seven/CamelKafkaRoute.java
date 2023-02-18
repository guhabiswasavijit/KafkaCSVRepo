package org.seven;

import org.apache.camel.ExchangePropertyKey;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.converter.CoreTypeConverterRegistry;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("CamelKafkaRoute")
@Slf4j
public class CamelKafkaRoute extends RouteBuilder {
	@Autowired
	private PaytmKafkaRecord paytmKafkaRecord;
	
	public void configure() throws Exception {                
        log.info("About to start route: Kafka Server -> Log ");
        onException(IllegalArgumentException.class)
        .handled(true)
        .to("bean:mySqlDBHandler?method=logBadRecords");

        BindyCsvDataFormat bindy = new BindyCsvDataFormat(PaytmTransaction.class);
        CoreTypeConverterRegistry typeConverterRegistry = (CoreTypeConverterRegistry) this.getContext().getTypeConverterRegistry();
        typeConverterRegistry.addTypeConverter(ProducerRecord.class,PaytmTransaction.class,paytmKafkaRecord);
        PaytmKafkaRecord typeConverter = (PaytmKafkaRecord)typeConverterRegistry.lookup(ProducerRecord.class,PaytmTransaction.class);
        log.debug("got type converter {}",typeConverter);
        from("direct:split")
	 	  .routeId("kafkaProducerRecordRoute")
		  .log("Split line ${body}")
		  .log("Insert ${header.CamelSplitIndex} of ${header.CamelSplitSize}")
		  .setProperty(ExchangePropertyKey.SPLIT_INDEX.getName(),simple("${header.CamelSplitIndex}"))
		  .choice()
		   	.when(simple("${exchangeProperty.CamelSplitIndex} != 0"))
		   	    .unmarshal(bindy)
		   	    .convertBodyTo(ProducerRecord.class)
		   		.to("kafka:{{paytm.topic}}?brokers={{kafka.host}}:{{kafka.port}}")		   	  
				.log("Finished Transformation:"+body())
		   	.otherwise().log("Got body:"+body())
		  .endChoice()			  
	    .end();
        
        from("file://{{input_directory}}/?idempotent=true&move=backup/$simple{date:now:yyyyMMdd}/$simple{file:name}&moveFailed=error/$simple{date:now:yyyyMMdd}/$simple{file:name}")
        .routeId("kafkaProducerRoute")
	    .onCompletion().onCompleteOnly()
	        .to("bean:mySqlDBHandler?method=execute")
	    	.setBody(constant("file upload complete"))
	    	.end()
	    .log("Processing ${header.CamelFileNameOnly} "+ Thread.currentThread().getName())
 	    .split(body().tokenize("\\n")).to("direct:split").end()	;
    }
        
}
