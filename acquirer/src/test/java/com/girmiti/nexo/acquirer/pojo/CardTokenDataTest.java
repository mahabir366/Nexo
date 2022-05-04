package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CardTokenDataTest {
	
	@InjectMocks
	CardTokenData cardTokenData;
	
	private static final String USRE_ID = "user089MK0991";

	private static final String PASSWORD = "dfbgk";
	
	@Before
	public void setUp() {
		cardTokenData = new CardTokenData();
		cardTokenData.setUserId(USRE_ID);
		cardTokenData.setPassword(PASSWORD);
	}
	
	@Test
	public void testCardTokenDataRespopnse() {
		Assert.assertEquals(USRE_ID, cardTokenData.getUserId());
		Assert.assertEquals(PASSWORD, cardTokenData.getPassword());
	}
}
