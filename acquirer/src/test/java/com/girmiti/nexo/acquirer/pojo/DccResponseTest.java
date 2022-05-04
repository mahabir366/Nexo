package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DccResponseTest {
	@InjectMocks
	DccResponse dccResponse;

	 List<DccRequest> DCCS;
	private static final Integer TOTAL_NO_DCCS=123;
	private static final String CONVERSION_AMOUNT="1000";


	@Before
	public void setUp() {
	dccResponse = new DccResponse();
	dccResponse.setDccs(DCCS);
	dccResponse.setTotalNoDccs(TOTAL_NO_DCCS);
	dccResponse.setConversionAmount(CONVERSION_AMOUNT);
	}

	@Test
	public void testDccResponse(){
	Assert.assertEquals(DCCS,dccResponse.getDccs());
	Assert.assertEquals(TOTAL_NO_DCCS,dccResponse.getTotalNoDccs());
	Assert.assertEquals(CONVERSION_AMOUNT,dccResponse.getConversionAmount());
	}

}
