package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.girmiti.nexo.acquirer.enums.CardAssociationEnum;
import com.girmiti.nexo.acquirer.enums.CountryTypeEnum;
import com.girmiti.nexo.acquirer.enums.CurrencyCodeEnum;
import com.girmiti.nexo.acquirer.enums.PaymentProcessTypeEnum;
import com.girmiti.nexo.acquirer.enums.TransactionType;

public class PaymentDetailsTest {

	@InjectMocks
	PaymentDetails paymentDetails;

	private static final Long TRANSACTION_ID = 78L;
	private static final TransactionType TRANSACTION_TYPE = TransactionType.AUTH;
	private static final String ORDER_ID = "ORD09I900";
	private static final Long TOTAL_AMOUNT = 10000L;
	private static final Long MERCHANT_AMOUNT = 200L;
	private static final CardAssociationEnum CARD_ASSOCIATION_ENUM = CardAssociationEnum.DC;
	private static final String DESCRIPTION = "DECTXN009";
	private static final String BILLER_NAME = "DUMMY01";
	private static final String BILLER_EMAIL = "DUMMY@MAIL.COM";
	private static final String BILLER_CITY = "DUMM";
	private static final String BILLER_STATE = "DUM";
	private static final CountryTypeEnum BILLER_COUNTRY = CountryTypeEnum.ZW;
	private static final String BILLER_ZIP = "9099";
	private static final String ADDRESS = "VDUMMY";
	private static final String ADDERSS2 = "V2DUMMY";
	private static final String RETURN_URL = "V2DUMMY";
	private static final String MERCHANT_ID = "V2DUMMY";
	private static final String CLIENT_IP = "V2DUMMY";
	private static final Integer CLIENT_PORT = 69;
	private static final Long ORIGIN_TIME = 69L;
	private static final CurrencyCodeEnum CURRENCY_CODE = CurrencyCodeEnum.AUD;
	private static final String FORMATED_TOTAL_AMT = "8556";
	private static final PaymentProcessTypeEnum PAYMENT_PROCESS_TYPE_ENUM = PaymentProcessTypeEnum.N;
	private static final String TOKEN = "T009";
	private static final String ACCESS_TOKEN = "AOO096";
	private static final String MODE = "009MODE";
	private static final String PROCSSOR_MID = "PMID009";

	@Before
	public void setUp() {
		PaymentDetails details = new PaymentDetails(TRANSACTION_ID, TRANSACTION_TYPE, ORDER_ID, TOTAL_AMOUNT,
				MERCHANT_AMOUNT, CARD_ASSOCIATION_ENUM, DESCRIPTION, BILLER_NAME, BILLER_EMAIL, BILLER_CITY,
				BILLER_STATE, BILLER_COUNTRY, BILLER_ZIP, ADDRESS, ADDERSS2, MERCHANT_ID, RETURN_URL, CURRENCY_CODE,
				PAYMENT_PROCESS_TYPE_ENUM, TOKEN, ACCESS_TOKEN);
		paymentDetails = new PaymentDetails();
		paymentDetails.setAccessToken(ACCESS_TOKEN);
		paymentDetails.setAddress(ADDRESS);
		paymentDetails.setAddress2(ADDERSS2);
		paymentDetails.setBillerCity(BILLER_CITY);
		paymentDetails.setBillerCountry(BILLER_COUNTRY);
		paymentDetails.setBillerEmail(BILLER_EMAIL);
		paymentDetails.setBillerName(BILLER_NAME);
		paymentDetails.setBillerState(BILLER_STATE);
		paymentDetails.setBillerZip(BILLER_ZIP);
		paymentDetails.setCardAssociation(CARD_ASSOCIATION_ENUM);
		paymentDetails.setClientIP(CLIENT_IP);
		paymentDetails.setClientPort(CLIENT_PORT);
		paymentDetails.setCurrencyCode(CURRENCY_CODE);
		paymentDetails.setTransactionId(TRANSACTION_ID);
		paymentDetails.setToken(TOKEN);
		paymentDetails.setTotalAmount(TOTAL_AMOUNT);
		paymentDetails.setReturnURL(RETURN_URL);
		paymentDetails.setProcessorMid(PROCSSOR_MID);
		paymentDetails.setPaymentProcessTypeEnum(PAYMENT_PROCESS_TYPE_ENUM);
		paymentDetails.setOriginTime(ORIGIN_TIME);
		paymentDetails.setOrderId(ORDER_ID);
		paymentDetails.setMode(MODE);
		paymentDetails.setDescription(DESCRIPTION);
		paymentDetails.setMerchantId(MERCHANT_ID);
		paymentDetails.setMerchantAmount(MERCHANT_AMOUNT);
		paymentDetails.setFormatedTotalAmt(FORMATED_TOTAL_AMT);
		paymentDetails.setTransactionType(TRANSACTION_TYPE);
	}

	@Test
	public void testPaymentDetailsRespopnse() {
		Assert.assertEquals(TRANSACTION_TYPE, paymentDetails.getTransactionType());
		Assert.assertEquals(FORMATED_TOTAL_AMT, paymentDetails.getFormatedTotalAmt());
		Assert.assertEquals(MERCHANT_AMOUNT, paymentDetails.getMerchantAmount());
		Assert.assertEquals(MERCHANT_ID, paymentDetails.getMerchantId());
		Assert.assertEquals(DESCRIPTION, paymentDetails.getDescription());
		Assert.assertEquals(MODE, paymentDetails.getMode());
		Assert.assertEquals(ORDER_ID, paymentDetails.getOrderId());
		Assert.assertEquals(ORIGIN_TIME, paymentDetails.getOriginTime());
		Assert.assertEquals(PAYMENT_PROCESS_TYPE_ENUM, paymentDetails.getPaymentProcessTypeEnum());
		Assert.assertEquals(PROCSSOR_MID, paymentDetails.getProcessorMid());
		Assert.assertEquals(RETURN_URL, paymentDetails.getReturnURL());
		Assert.assertEquals(TOTAL_AMOUNT, paymentDetails.getTotalAmount());
		Assert.assertEquals(TOKEN, paymentDetails.getToken());
		Assert.assertEquals(TRANSACTION_ID, paymentDetails.getTransactionId());
		Assert.assertEquals(CURRENCY_CODE, paymentDetails.getCurrencyCode());
		Assert.assertEquals(CLIENT_PORT, paymentDetails.getClientPort());
		Assert.assertEquals(CLIENT_IP, paymentDetails.getClientIP());
		Assert.assertEquals(CARD_ASSOCIATION_ENUM, paymentDetails.getCardAssociation());
		Assert.assertEquals(BILLER_ZIP, paymentDetails.getBillerZip());
		Assert.assertEquals(BILLER_STATE, paymentDetails.getBillerState());
		Assert.assertEquals(BILLER_NAME, paymentDetails.getBillerName());
		Assert.assertEquals(BILLER_EMAIL, paymentDetails.getBillerEmail());
		Assert.assertEquals(BILLER_COUNTRY, paymentDetails.getBillerCountry());
		Assert.assertEquals(BILLER_CITY, paymentDetails.getBillerCity());
		Assert.assertEquals(ADDERSS2, paymentDetails.getAddress2());
		Assert.assertEquals(ADDRESS, paymentDetails.getAddress());
		Assert.assertEquals(ACCESS_TOKEN, paymentDetails.getAccessToken());
	}
}
