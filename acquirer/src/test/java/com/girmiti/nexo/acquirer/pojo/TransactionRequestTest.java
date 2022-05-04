package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.girmiti.nexo.acquirer.enums.MethodOfPaymentTypeEnum;

public class TransactionRequestTest {
	
	@InjectMocks
	TransactionRequest transactionRequest;

	@Mock
	CardData cardData;
	
	@Mock
	BillingData billingData;
	
	@Mock
	CardTokenData cardTokenData;
	
	@Mock
	SplitTxnData splitTxnData;
	
	private static final Long TXN_AMOUNT = 9900l;

	private static final String MERCHANT_NAME = "merchant_kane";

	private static final String INVOICE_NUMBER = "9878641538647987";

	private static final String REGISTER_NUMBER = "r0756543678789";

	private static final String CARD_TOKEN = "cdtkn009";

	private static final String ORDER_ID = "order09856586";

	private static final String TXN_REF_NUMBER = "435345435435";

	private static final String AUTH_ID = "243fg";

	private static final String IP_PORT = "1998";

	private static final String CG_REF_NUMBER = "ce-ref-978890-90";

	private static final Long MERCHANT_AMOUNT = 100l;

	private static final Long FEE_AMOUNT = 700l;

	private static final Long TOTAL_TXN_AMOUNT = 80999l;

	private static final String DESCRIPTION = "txn0090";

	private static final String SPLIT_REF_NUMBER = "ref789890";

	private static final String MOBILE_NUMBER = "545656569847";

	private static final String ACCOUNT_NUMBER = "9875641328784";

	private static final String QR_CODE = "sdfghjkly9/715342ouih";

	private static final String CURRENCY_CODE = "usd";

	private static final String USER_NAME = "xioih";

	private static final String TIME_ZONE_OFF_SET = "5:30";

	private static final String TIME_ZONE_REGION = "BLR";
	
	private static final String PG_TXN_REF = "srefghhkjl7";
	
	private static final String RECONCILIATION_ID = "564";
	
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
	
	public static final String ADDRESS1 = "Brdway";
	
	public static final String ADDRESS2 = "Brdway";
	
	public static final String CITY = "NY";
	
	public static final String COUNTRY = "US";
	
	public static final String STATE = "CAL";
	
	public static final String ZIP_CODE = "230119";
	
	public static final String EMAIL = "NYC@mail.com";
	
	private static final String USRE_ID = "user089MK0991";

	private static final String PASSWORD = "dfbgk";
	
	private static final Long SPLIT_AMOUNT = 630l;

	private static final String REF_MASKED_PAN = "9963X-xxx-xxx";

	private static final Long REF_MOBILE_NUMBER = 986456145698l;
	
	@Before
	public void setUp() {
		cardData = new CardData();
		billingData = new BillingData();
		cardTokenData = new CardTokenData(); 
		splitTxnData = new SplitTxnData();
		transactionRequest = new TransactionRequest();
		transactionRequest.setAccountNumber(ACCOUNT_NUMBER);
		transactionRequest.setAuthId(AUTH_ID);
		transactionRequest.setCgRefNumber(CG_REF_NUMBER);
		transactionRequest.setCurrencyCode(CURRENCY_CODE);
		transactionRequest.setCardToken(CARD_TOKEN);
		transactionRequest.setDescription(DESCRIPTION);
		transactionRequest.setFeeAmount(FEE_AMOUNT);
		transactionRequest.setUserName(USER_NAME);
		transactionRequest.setInvoiceNumber(INVOICE_NUMBER);
		transactionRequest.setIpPort(IP_PORT);
		transactionRequest.setMerchantAmount(MERCHANT_AMOUNT);
		transactionRequest.setMerchantName(MERCHANT_NAME);
		transactionRequest.setMobileNumber(MOBILE_NUMBER);
		transactionRequest.setOrderId(ORDER_ID);
		transactionRequest.setTxnRefNumber(TXN_REF_NUMBER);
		transactionRequest.setTxnAmount(TXN_AMOUNT);
		transactionRequest.setTimeZoneRegion(TIME_ZONE_REGION);
		transactionRequest.setTimeZoneOffset(TIME_ZONE_OFF_SET);
		transactionRequest.setQrCode(QR_CODE);
		transactionRequest.setSplitRefNumber(SPLIT_REF_NUMBER);
		transactionRequest.setRegisterNumber(REGISTER_NUMBER);
		transactionRequest.setPgTxnRef(PG_TXN_REF);
		transactionRequest.setReconciliationId(RECONCILIATION_ID);
		transactionRequest.setTotalTxnAmount(TOTAL_TXN_AMOUNT);
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
		billingData = new BillingData();
		billingData.setAddress1(ADDRESS1);
		billingData.setAddress2(ADDRESS2);
		billingData.setCity(CITY);
		billingData.setCountry(COUNTRY);
		billingData.setEmail(EMAIL);
		billingData.setState(STATE);
		billingData.setZipCode(ZIP_CODE);
		cardTokenData.setUserId(USRE_ID);
		cardTokenData.setPassword(PASSWORD);
		splitTxnData.setRefMaskedPAN(REF_MASKED_PAN);
		splitTxnData.setRefMobileNumber(REF_MOBILE_NUMBER);
		splitTxnData.setSplitAmount(SPLIT_AMOUNT);
		transactionRequest.setCardData(cardData);
		transactionRequest.setCardTokenData(cardTokenData);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setSplitTxnData(splitTxnData);
	}
	
	@Test
	public void testTransactionRespopnse() {
		Assert.assertEquals(ACCOUNT_NUMBER, transactionRequest.getAccountNumber());
		Assert.assertEquals(RECONCILIATION_ID, transactionRequest.getReconciliationId());
		Assert.assertEquals(PG_TXN_REF, transactionRequest.getPgTxnRef());
		Assert.assertEquals(TIME_ZONE_REGION, transactionRequest.getTimeZoneRegion());
		Assert.assertEquals(TIME_ZONE_OFF_SET, transactionRequest.getTimeZoneOffset());
		Assert.assertEquals(USER_NAME, transactionRequest.getUserName());
		Assert.assertEquals(CURRENCY_CODE, transactionRequest.getCurrencyCode());
		Assert.assertEquals(QR_CODE, transactionRequest.getQrCode());
		Assert.assertEquals(MOBILE_NUMBER, transactionRequest.getMobileNumber());
		Assert.assertEquals(SPLIT_REF_NUMBER, transactionRequest.getSplitRefNumber());
		Assert.assertEquals(DESCRIPTION, transactionRequest.getDescription());
		Assert.assertEquals(TOTAL_TXN_AMOUNT, transactionRequest.getTotalTxnAmount());
		Assert.assertEquals(FEE_AMOUNT, transactionRequest.getFeeAmount());
		Assert.assertEquals(MERCHANT_AMOUNT, transactionRequest.getMerchantAmount());
		Assert.assertEquals(CG_REF_NUMBER, transactionRequest.getCgRefNumber());
		Assert.assertEquals(IP_PORT, transactionRequest.getIpPort());
		Assert.assertEquals(AUTH_ID, transactionRequest.getAuthId());
		Assert.assertEquals(ORDER_ID, transactionRequest.getOrderId());
		Assert.assertEquals(CARD_TOKEN, transactionRequest.getCardToken());
		Assert.assertEquals(REGISTER_NUMBER, transactionRequest.getRegisterNumber());
		Assert.assertEquals(TXN_REF_NUMBER, transactionRequest.getTxnRefNumber());
		Assert.assertEquals(INVOICE_NUMBER, transactionRequest.getInvoiceNumber());
		Assert.assertEquals(MERCHANT_NAME, transactionRequest.getMerchantName());
		Assert.assertEquals(TXN_AMOUNT, transactionRequest.getTxnAmount());
		Assert.assertEquals(cardData, transactionRequest.getCardData());
		Assert.assertEquals(billingData, transactionRequest.getBillingData());
		Assert.assertEquals(cardTokenData, transactionRequest.getCardTokenData());
		Assert.assertEquals(splitTxnData, transactionRequest.getSplitTxnData());
	}
}
