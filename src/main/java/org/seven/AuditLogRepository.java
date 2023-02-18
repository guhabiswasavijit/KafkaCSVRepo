package org.seven;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AuditLogRepository{
	@PersistenceContext(unitName="org.seven")
	private EntityManager entityManager;
	public void logAudit(AuditLog log) {
		entityManager.persist(log);
	}
}
