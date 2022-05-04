package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DaignosticTransactionResponseTest {

	@InjectMocks
	DaignosticTransactionResponse daignosticTransactionResponse;
	
	public static final Boolean STATUS = true;
	
	public static final String ERROR_MESSAGE = "APPR";
	
	public static final String ERROR_CODE = "00";
	
	@Before
	public void setUp() {
		daignosticTransactionResponse = new DaignosticTransactionResponse();
		daignosticTransactionResponse.setStatus(STATUS);
		daignosticTransactionResponse.setErrorCode(ERROR_CODE);
		daignosticTransactionResponse.setErrorMessage(ERROR_MESSAGE);
	}
	
	@Test
	public void testCancellationAdviceRespopnse() {
		Assert.assertEquals(STATUS, daignosticTransactionResponse.getStatus());
		Assert.assertEquals(ERROR_CODE, daignosticTransactionResponse.getErrorCode());
		Assert.assertEquals(ERROR_MESSAGE, daignosticTransactionResponse.getErrorMessage());
	}
}
