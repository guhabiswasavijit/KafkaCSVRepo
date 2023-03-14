package org.seven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.seven.model.AuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogRepository{
    @Autowired
	private DataSource dataSource;
	public void logAudit(AuditLog log) throws SQLException {
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pStmt = conn.prepareStatement("INSERT INTO AUDIT_LOG(file_processed) VALUES(?)");
		pStmt.setString(1, log.getFileProcessed());
		pStmt.execute();
		conn.commit();
	}
}
