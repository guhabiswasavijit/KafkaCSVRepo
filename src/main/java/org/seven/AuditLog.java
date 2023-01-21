package org.seven;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUDIT_LOG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {
   @Id
   private int id;
   @Column(name = "file_processed")
   private String fileProcessed;
   @Column(name = "run_date")
   private Date runDate;
}
