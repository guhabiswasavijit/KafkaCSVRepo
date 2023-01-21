package org.seven;

import org.apache.camel.Exchange;
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
        AuditLog auditLog = new AuditLog();
        auditLog.setFileProcessed(fileProcessed);
        auditRepository.logAudit(auditLog);
        log.debug("successfully created audit record:{}",fileProcessed);
	}
}
