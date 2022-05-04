package com.girmiti.nexo.acquirer.dao;


import java.util.List;

import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionReportResponse;

public interface TransactionDao {

	public NexoTxn updateTransactionResponse(NexoTxn nexoTxn);

	public NexoTxn saveTransactionRequest(NexoTxn nexoTxn);

	public TransactionReportResponse getTransactions(GetTranstionsRequest request);

	public NexoTxn findByPgTxRef(NexoTxn nexoTxn);

	public List<NexoTxn> findByCaptureStatus(String captureStatus);
}
