package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ResponseTest {
	
	@InjectMocks
	Response response;

	public static final String ERROR_MESSAGE = "APPR";
	
	public static final String ERROR_CODE = "00";
	
	private static final String ERROR_MESSAGE_EXT = "yoloM";

	private static final Long TXN_DATE_TIME = 2019l;

	private static final String TXN_REF_NUMBER = "2345567";
	
	private static final String STATUSMES="status";
	@Before
	public void setUp() {
		response = new Response();
		response.setErrorCode(ERROR_CODE);
		response.setErrorMessage(ERROR_MESSAGE);
		response.setErrorMessageExt(ERROR_MESSAGE_EXT);
		response.setTxnDateTime(TXN_DATE_TIME);
		response.setTxnRefNumber(TXN_REF_NUMBER);
		response.setStatusMessage(STATUSMES);
	}
	
	@Test
	public void testRespopnse() {
		Assert.assertEquals(ERROR_CODE, response.getErrorCode());
		Assert.assertEquals(ERROR_MESSAGE, response.getErrorMessage());
		Assert.assertEquals(ERROR_MESSAGE_EXT, response.getErrorMessageExt());
		Assert.assertEquals(TXN_DATE_TIME, response.getTxnDateTime());
		Assert.assertEquals(TXN_REF_NUMBER, response.getTxnRefNumber());
		Assert.assertEquals(STATUSMES, response.getStatusMessage());
	}
}
