package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CancellationAdviceResponseTest {

	@InjectMocks
	CancellationAdviceResponse cancellationAdviceResponse;
	
	public static final Boolean STATUS = true;
	
	public static final String ERROR_MESSAGE = "APP";
	
	public static final String TXN_REF_NUMBER = "899235";
	
	public static final String ERROR_CODE = "00";
	
	@Before
	public void setUp() {
		cancellationAdviceResponse = new CancellationAdviceResponse();
		cancellationAdviceResponse.setStatus(STATUS);
		cancellationAdviceResponse.setErrorCode(ERROR_CODE);
		cancellationAdviceResponse.setErrorMessage(ERROR_MESSAGE);
		cancellationAdviceResponse.setTxnRefNumber(TXN_REF_NUMBER);
	}
	
	@Test
	public void testCancellationAdviceRespopnse() {
		Assert.assertEquals(STATUS, cancellationAdviceResponse.getStatus());
		Assert.assertEquals(ERROR_CODE, cancellationAdviceResponse.getErrorCode());
		Assert.assertEquals(TXN_REF_NUMBER, cancellationAdviceResponse.getTxnRefNumber());
		Assert.assertEquals(ERROR_MESSAGE, cancellationAdviceResponse.getErrorMessage());
	}
}
