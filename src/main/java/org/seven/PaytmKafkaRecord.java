package org.seven;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePropertyKey;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Component("paytmKafkaRecord")
public class PaytmKafkaRecord extends TypeConverterSupport{
	private ProducerRecord<String,PaytmTransaction> toProducerRecord(Exchange exchange) {
		PaytmTransaction paytm = exchange.getIn().getBody(PaytmTransaction.class);
		String recordKey = exchange.getProperty(ExchangePropertyKey.SPLIT_INDEX,String.class);
	    if (paytm != null) {
	    	return new ProducerRecord<String,PaytmTransaction>(recordKey, paytm);
	    }
	    return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
		return (T)this.toProducerRecord(exchange);
	}

}
