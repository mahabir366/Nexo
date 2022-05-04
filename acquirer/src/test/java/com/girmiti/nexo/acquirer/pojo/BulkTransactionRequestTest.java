package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class BulkTransactionRequestTest {
	@InjectMocks
	BulkTransactionRequest bulkTransactionRequest;

	List<TransactionRequest> transactionRequest;

	@Before
	public void setUp() {
		bulkTransactionRequest = new BulkTransactionRequest();
		bulkTransactionRequest.setTransactionRequest(transactionRequest);
	}

	@Test
	public void testBulkTransactionResponse() {
		Assert.assertEquals(transactionRequest, bulkTransactionRequest.getTransactionRequest());
	}
}
