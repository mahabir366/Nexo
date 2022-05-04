package com.girmiti.nexo.acquirer.pojo;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DccRequestTest {
	@InjectMocks
	DccRequest dccRequest;

	private static final Long SL_NO=10l;
	private static final String SOURCE_CURRENCY="USD";
	private static final String TARGET_CURRENCY="INR";
	private static final String REASON="test";
	private static final String STATUS="Active";
	private static final Timestamp CREATE_DATE=null;
	private static final Timestamp UPDATE_DATE=null;
	private static final String CONVERSION_RATE="73.6";
	private static final String MERCHANT_CODE="123456789";
	private static final String CONVERSION_AMOUNT="200";
	private static final String TXN_AMOUNT="210";
	private static final String TXN_REF_NUMBER="dsaffrda";


	@Before
	public void setUp() {
	dccRequest = new DccRequest();
	dccRequest.setSlNo(SL_NO);
	dccRequest.setSourceCurrency(SOURCE_CURRENCY);
	dccRequest.setTargetCurrency(TARGET_CURRENCY);
	dccRequest.setReason(REASON);
	dccRequest.setStatus(STATUS);
	dccRequest.setCreateDate(CREATE_DATE);
	dccRequest.setUpdateDate(UPDATE_DATE);
	dccRequest.setConversionRate(CONVERSION_RATE);
	dccRequest.setMerchantCode(MERCHANT_CODE);
	dccRequest.setConversionAmount(CONVERSION_AMOUNT);
	dccRequest.setTxnAmount(TXN_AMOUNT);
	dccRequest.setTxnRefNumber(TXN_REF_NUMBER);
	}

	@Test
	public void testDccRequest(){
	Assert.assertEquals(SL_NO,dccRequest.getSlNo());
	Assert.assertEquals(SOURCE_CURRENCY,dccRequest.getSourceCurrency());
	Assert.assertEquals(TARGET_CURRENCY,dccRequest.getTargetCurrency());
	Assert.assertEquals(REASON,dccRequest.getReason());
	Assert.assertEquals(STATUS,dccRequest.getStatus());
	Assert.assertEquals(CREATE_DATE,dccRequest.getCreateDate());
	Assert.assertEquals(UPDATE_DATE,dccRequest.getUpdateDate());
	Assert.assertEquals(CONVERSION_RATE,dccRequest.getConversionRate());
	Assert.assertEquals(MERCHANT_CODE,dccRequest.getMerchantCode());
	Assert.assertEquals(CONVERSION_AMOUNT,dccRequest.getConversionAmount());
	Assert.assertEquals(TXN_AMOUNT,dccRequest.getTxnAmount());
	Assert.assertEquals(TXN_REF_NUMBER,dccRequest.getTxnRefNumber());
	}

	
}
