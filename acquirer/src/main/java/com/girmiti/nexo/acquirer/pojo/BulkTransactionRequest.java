package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;
import java.util.List;

import com.girmiti.nexo.acceptorbatchtransfer.TransactionTotals7;

public class BulkTransactionRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TransactionRequest> transactionRequest;
	
	private List<TransactionTotals7> transactionTotals;
	
	public List<TransactionRequest> getTransactionRequest() {
		return transactionRequest;
	}

	public void setTransactionRequest(List<TransactionRequest> transactionRequest) {
		this.transactionRequest = transactionRequest;
	}

	public List<TransactionTotals7> getTransactionTotals() {
		return transactionTotals;
	}

	public void setTransactionTotals(List<TransactionTotals7> transactionTotals) {
		this.transactionTotals = transactionTotals;
	}

}
