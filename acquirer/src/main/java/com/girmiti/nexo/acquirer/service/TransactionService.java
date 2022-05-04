package com.girmiti.nexo.acquirer.service;

import java.util.List;

import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.exception.RejectionException;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceRequest;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceResponse;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.DccRequest;
import com.girmiti.nexo.acquirer.pojo.DccResponse;
import com.girmiti.nexo.acquirer.pojo.ReconciliationRequest;
import com.girmiti.nexo.acquirer.pojo.ReconciliationResponse;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionResponse;

public interface TransactionService {
	
	public NexoTxn saveTransactionRequest(NexoTxn nexoTxn);
	
	public NexoTxn updateTransactionResponse(NexoTxn nexoTxn);
	
	public DaignosticTransactionResponse getDiagnosticStatus(DaignosticTransactionRequest transactionRequest);
	
	public CancellationAdviceResponse getCancellationAdviceStatus(CancellationAdviceRequest transactionRequest);
	
	public TransactionResponse processTransactionBasedOnStatus(TransactionRequest transactionRequest);
	
	public NexoTxn findByPgTxRef(NexoTxn nexoTxn);
	
	public TransactionResponse processSaleTransaction(TransactionRequest transactionRequest);
	
	public TransactionResponse processAuthTransaction(TransactionRequest transactionRequest) throws RejectionException;
	
	public TransactionResponse processCaptureTransaction(TransactionRequest transactionRequest);
	
	public ReconciliationResponse processReconcialtionTransaction(ReconciliationRequest reconciliationRequest);
	
	public BulkTransactionResponse batchProcessTransaction(BulkTransactionRequest bulkTransactionRequest);
	
	public DccResponse getCurrencyConversionRate(DccRequest dccRequest);
	
	public DccResponse updateCurrencyConversionTransaction(DccRequest dccRequest);

	public List<NexoTxn> findByCaptureStatus(String captrueStatus);

}
