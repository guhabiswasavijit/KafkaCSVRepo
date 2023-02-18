package org.seven;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePropertyKey;
import org.apache.camel.NoTypeConversionAvailableException;
import org.apache.camel.TypeConversionException;
import org.apache.camel.TypeConverter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
@Component
public class PaytmKafkaRecord implements TypeConverter{

	private ProducerRecord<String,PaytmTransaction> toProducerRecord(Exchange exchange) {
		PaytmTransaction paytm = exchange.getIn().getBody(PaytmTransaction.class);
		String recordKey = exchange.getProperty(ExchangePropertyKey.SPLIT_INDEX,String.class);
	    if (paytm != null) {
	    	return new ProducerRecord<String,PaytmTransaction>(recordKey, paytm);
	    }
	    return null;
	}

	@Override
	public boolean allowNull() {
		return false;
	}

	@Override
	public <T> T convertTo(Class<T> type, Object value) throws TypeConversionException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
		return (T) toProducerRecord(exchange);
	}

	@Override
	public <T> T mandatoryConvertTo(Class<T> type, Object value)
			throws TypeConversionException, NoTypeConversionAvailableException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T mandatoryConvertTo(Class<T> type, Exchange exchange, Object value)
			throws TypeConversionException, NoTypeConversionAvailableException {
		return (T) toProducerRecord(exchange);
	}

	@Override
	public <T> T tryConvertTo(Class<T> type, Object value) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T tryConvertTo(Class<T> type, Exchange exchange, Object value) {
		return (T) toProducerRecord(exchange);
	}
}
