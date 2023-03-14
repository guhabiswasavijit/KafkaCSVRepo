package org.seven;

import java.sql.SQLException;

import org.apache.camel.Exchange;
import org.seven.model.AuditLog;
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
        try {
			auditRepository.logAudit(auditLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        log.debug("successfully created audit record:{}",fileProcessed);
	}
	public void logBadRecords(Exchange exchange) {
		String badRecord = exchange.getIn().getBody(String.class);
		log.debug("erroring record:{}",badRecord);
	}
}
