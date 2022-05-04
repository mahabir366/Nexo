package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import com.girmiti.nexo.acceptorreconciliationreqest.TransactionTotals7;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReconciliationRequest {
	
	private String reconciliationId;
	
	List<PaymentType> paymentType;
	
	private List<TransactionTotals7> transactionTotals7Req;
	
}
