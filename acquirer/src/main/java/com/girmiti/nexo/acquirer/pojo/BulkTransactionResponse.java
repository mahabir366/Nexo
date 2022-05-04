
package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkTransactionResponse extends Response {

	private static final long serialVersionUID = 1L;

	private List<TransactionResponse> transactionResponse;
	
	private List<TransactionTotals7> transactionTotals;

	public List<TransactionResponse> getTransactionResponse() {
		return transactionResponse;
	}

	public void setTransactionResponse(List<TransactionResponse> transactionResponse) {
		this.transactionResponse = transactionResponse;
	}

	public List<TransactionTotals7> getTransactionTotals() {
		return transactionTotals;
	}

	public void setTransactionTotals(List<TransactionTotals7> transactionTotals) {
		this.transactionTotals = transactionTotals;
	}
}
