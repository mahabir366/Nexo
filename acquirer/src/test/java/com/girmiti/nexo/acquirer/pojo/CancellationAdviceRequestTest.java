package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CancellationAdviceRequestTest {

	@InjectMocks
	CancellationAdviceRequest cancellationAdviceRequest;
	
	public static final Boolean STATUS = true;
	
	public static final String MERCHANT_CODE = "322435354074338";
	
	public static final String TXN_REF_NUMBER = "899235";
	
	public static final Long TXN_AMOUNT = 1000l;
	
	@Before
	public void setUp() {
		cancellationAdviceRequest = new CancellationAdviceRequest();
		cancellationAdviceRequest.setStatus(STATUS);
		cancellationAdviceRequest.setMerchantCode(MERCHANT_CODE);
		cancellationAdviceRequest.setTxnAmount(TXN_AMOUNT);
		cancellationAdviceRequest.setTxnRefNumber(TXN_REF_NUMBER);
	}
	
	@Test
	public void testCancellationAdviceRespopnse() {
		Assert.assertEquals(STATUS, cancellationAdviceRequest.getStatus());
		Assert.assertEquals(MERCHANT_CODE, cancellationAdviceRequest.getMerchantCode());
		Assert.assertEquals(TXN_REF_NUMBER, cancellationAdviceRequest.getTxnRefNumber());
		Assert.assertEquals(TXN_AMOUNT, cancellationAdviceRequest.getTxnAmount());
	}
}

