package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ReconciliationResponseTest {
	
	@InjectMocks
	ReconciliationResponse reconciliationResponse;
	
	private static final String TXN_STATUS = "APPR";
	
	@Before
	public void setUp() {
		reconciliationResponse = new ReconciliationResponse();
		reconciliationResponse.setTxnStatus(TXN_STATUS);
	}
	
	@Test
	public void testReconciliationRespopnses() {
		Assert.assertEquals(TXN_STATUS, reconciliationResponse.getTxnStatus());
	}

}
