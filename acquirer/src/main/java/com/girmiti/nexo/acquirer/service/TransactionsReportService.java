package com.girmiti.nexo.acquirer.service;

import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.Response;

public interface TransactionsReportService {
	
	public Response getTransactions(GetTranstionsRequest request);

}
