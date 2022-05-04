package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DaignosticTransactionRequestTest {
	
	@InjectMocks
	DaignosticTransactionRequest daignosticTransactionRequest;
	
	private static final Boolean STATUS = true;

	@Before
	public void setUp() {
		daignosticTransactionRequest = new DaignosticTransactionRequest();
		daignosticTransactionRequest.setStatus(STATUS);
	}
	
	@Test
	public void testDaignosticTransactionRespopnse() {
		Assert.assertEquals(STATUS, daignosticTransactionRequest.getStatus());
	}
}
