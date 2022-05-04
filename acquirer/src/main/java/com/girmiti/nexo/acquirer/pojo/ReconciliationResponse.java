package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReconciliationResponse extends Response{
	
	private static final long serialVersionUID = 90552331486485683L;
	
	private String txnStatus;
	
	private List<TransactionTotals7> transactionTotals7Res;

}
