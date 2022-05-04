package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class BulkTransactionResponseTest {

	@InjectMocks
	BulkTransactionResponse bulkTransactionResponse;

	List<TransactionResponse> transactionResponse;

	@Before
	public void setUp() {
		bulkTransactionResponse = new BulkTransactionResponse();
		bulkTransactionResponse.setTransactionResponse(transactionResponse);
	}

	@Test
	public void testBulkTransactionResponse() {
		Assert.assertEquals(transactionResponse, bulkTransactionResponse.getTransactionResponse());
	}
}
