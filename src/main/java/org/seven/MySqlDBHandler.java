package org.seven;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePropertyKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component("mySqlDBHandler")
@Slf4j
public class MySqlDBHandler {
	@Autowired
	private AuditLogRepository auditRepository;
	
	public void execute(Exchange exchange) {
		String fileProcessed = exchange.getIn().getHeader("CamelFileNameOnly",String.class);
		log.debug("successfully processed file:{}",fileProcessed);

	}
}
