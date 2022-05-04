package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.girmiti.nexo.acquirer.enums.MethodOfPaymentTypeEnum;

public class CardDataTest {
	
	@InjectMocks
	CardData cardData;
	
	
	private static final String CARD_NUMBER = "84798143145648646";

	private static final String EXP_DATE = "2023-12-12";

	private static final String CVV = "727";

	private static final String CARD_HOLDER_NAME = "Max";

	private static final MethodOfPaymentTypeEnum CARD_TYPE = MethodOfPaymentTypeEnum.AX;

	private static final String TRACK1 = "x";

	private static final String TRACK2 = "y";

	private static final String TRACK3 = "z";

	private static final String TRACK = "xyz";

	private static final String KEY_SERIAL = "8753";

	private static final String CARD_HOLDER_EMAIL = "max@getmail.com";

	private static final String EMV = "erw";

	private static final String UID = "dfs52y";
	
	@Before
	public void setUp() {
		cardData = new CardData();
		cardData.setCardNumber(CARD_NUMBER);
		cardData.setCardHolderEmail(CARD_HOLDER_EMAIL);
		cardData.setCardHolderName(CARD_HOLDER_NAME);
		cardData.setCardType(CARD_TYPE);
		cardData.setCvv(CVV);
		cardData.setEmv(EMV);
		cardData.setExpDate(EXP_DATE);
		cardData.setKeySerial(KEY_SERIAL);
		cardData.setTrack(TRACK);
		cardData.setTrack1(TRACK1);
		cardData.setTrack2(TRACK2);
		cardData.setTrack3(TRACK3);
		cardData.setUid(UID);
	}
	
	@Test
	public void testCardDataRespopnse() {
		Assert.assertEquals(CARD_NUMBER, cardData.getCardNumber());
		Assert.assertEquals(CARD_HOLDER_EMAIL, cardData.getCardHolderEmail());
		Assert.assertEquals(CARD_HOLDER_NAME, cardData.getCardHolderName());
		Assert.assertEquals(EXP_DATE, cardData.getExpDate());
		Assert.assertEquals(CVV, cardData.getCvv());
		Assert.assertEquals(TRACK, cardData.getTrack());
		Assert.assertEquals(TRACK1, cardData.getTrack1());
		Assert.assertEquals(TRACK2, cardData.getTrack2());
		Assert.assertEquals(TRACK3, cardData.getTrack3());
		Assert.assertEquals(CARD_TYPE, cardData.getCardType());
		Assert.assertEquals(EMV, cardData.getEmv());
		Assert.assertEquals(KEY_SERIAL, cardData.getKeySerial());
		Assert.assertEquals(UID, cardData.getUid());
	}
}
