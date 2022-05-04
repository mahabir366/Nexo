package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CardTokenTest {
	@InjectMocks
	CardToken cardToken;

	private static final String TOKEN = "token";
	private static final String CARD_LAST_FOUR_DIGIT = "1234";
	private static final String CARD_TYPE = "rupay";
	private static final String CVV = "cvv";
	private static final String EMAIL = "girmiti@girmiti.com";

	@Before
	public void setUp() {
		cardToken = new CardToken();
		cardToken.setToken(TOKEN);
		cardToken.setCardLastFourDigit(CARD_LAST_FOUR_DIGIT);
		cardToken.setCardType(CARD_TYPE);
		cardToken.setCvv(CVV);
		cardToken.setEmail(EMAIL);
	}

	@Test
	public void testCardToken() {
		Assert.assertEquals(TOKEN, cardToken.getToken());
		Assert.assertEquals(CARD_LAST_FOUR_DIGIT, cardToken.getCardLastFourDigit());
		Assert.assertEquals(CARD_TYPE, cardToken.getCardType());
		Assert.assertEquals(CVV, cardToken.getCvv());
		Assert.assertEquals(EMAIL, cardToken.getEmail());
	}

}
