package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class SplitTxnDataTest {
	
	@InjectMocks
	SplitTxnData splitTxnData;
	
	private static final Long SPLIT_AMOUNT = 630l;

	private static final String REF_MASKED_PAN = "9963X-xxx-xxx";

	private static final Long REF_MOBILE_NUMBER = 986456145698l;
	
	@Before
	public void setUp() {
		splitTxnData = new SplitTxnData();
		splitTxnData.setRefMaskedPAN(REF_MASKED_PAN);
		splitTxnData.setRefMobileNumber(REF_MOBILE_NUMBER);
		splitTxnData.setSplitAmount(SPLIT_AMOUNT);
	}

	@Test
	public void testSplitTxnDataRespopnse() {
		Assert.assertEquals(REF_MASKED_PAN, splitTxnData.getRefMaskedPAN());
		Assert.assertEquals(REF_MOBILE_NUMBER, splitTxnData.getRefMobileNumber());
		Assert.assertEquals(SPLIT_AMOUNT, splitTxnData.getSplitAmount());
	}
}
