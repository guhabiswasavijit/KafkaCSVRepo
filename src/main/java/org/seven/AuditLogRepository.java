package org.seven;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

@Repository
public class AuditLogRepository {
	public void logAudit(AuditLog log) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.seven");
	    EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
	    em.persist(log);
	    em.getTransaction().commit();
	}
}
