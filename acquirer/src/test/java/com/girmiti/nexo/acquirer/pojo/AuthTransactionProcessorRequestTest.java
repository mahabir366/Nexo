package com.girmiti.nexo.acquirer.pojo;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class AuthTransactionProcessorRequestTest {
	
	@InjectMocks
	AuthTransactionProcessorRequest authTransactionProcessorRequest; 

	public static final String REQ_TX_REF = "230119";
	
	public static final String PAN = "206693445246693";
	
	public static final String CVV = "discountname";
	
	public static final String EXPIRY_DATE = "2023-12-12";
	
	public static final BigDecimal REQ_TOTAL_AMOUNT = BigDecimal.TEN;
	
	@Before
	public void setUp() {
		authTransactionProcessorRequest = new AuthTransactionProcessorRequest();
		authTransactionProcessorRequest.setReqTxRef(REQ_TX_REF);
		authTransactionProcessorRequest.setPan(PAN);
		authTransactionProcessorRequest.setExpiryDate(EXPIRY_DATE);
		authTransactionProcessorRequest.setReqTotalAmt(REQ_TOTAL_AMOUNT);
		authTransactionProcessorRequest.setCvv(CVV);
	}
	
	@Test
	public void testAuthTransactionProcessorRespopnse() {
		Assert.assertEquals(REQ_TX_REF, authTransactionProcessorRequest.getReqTxRef());
		Assert.assertEquals(PAN, authTransactionProcessorRequest.getPan());
		Assert.assertEquals(EXPIRY_DATE, authTransactionProcessorRequest.getExpiryDate());
		Assert.assertEquals(CVV, authTransactionProcessorRequest.getCvv());
		Assert.assertEquals(REQ_TOTAL_AMOUNT, authTransactionProcessorRequest.getReqTotalAmt());
	}
}
