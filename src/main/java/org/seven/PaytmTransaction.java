package org.seven;

import java.io.Serializable;
import java.util.Date;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CsvRecord(separator = ",", crlf=",\\n")
public class PaytmTransaction implements Serializable{
	private static final long serialVersionUID = 1L;
	@DataField(pos = 1, pattern = "DD/MM/YYYY HH24:MI:SS")
	private Date txDate;
	@DataField(pos = 2)
	private String activity;
	@DataField(pos = 3)
	private String sourceDestination;
	@DataField(pos = 4)
	private String walletTxId;
	@DataField(pos = 5)
	private String usr_comment;
	@DataField(pos = 6)
	private Integer debit;
	@DataField(pos = 7)
	private Integer credit;
	@DataField(pos = 8)
	private String transaction_breakup;
	@DataField(pos = 9)
	private String status;

}
