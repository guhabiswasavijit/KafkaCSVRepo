package org.seven;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePropertyKey;
import org.apache.kafka.clients.producer.ProducerRecord;

@Converter
public class PaytmKafkaRecord {
	public static ProducerRecord<String,PaytmTransaction> toProducerRecord(Exchange exchange) {
		PaytmTransaction paytm = exchange.getIn().getBody(PaytmTransaction.class);
		String recordKey = exchange.getProperty(ExchangePropertyKey.SPLIT_INDEX,String.class);
	    if (paytm != null) {
	    	return new ProducerRecord<String,PaytmTransaction>(recordKey, paytm);
	    }
	    return null;
	}

}
