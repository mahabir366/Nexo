package com.girmiti.nexo.acquirer.pojo;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class GetTranstionsRequestTest {

	@InjectMocks
	GetTranstionsRequest getTranstionsRequest;
	
	private static final Integer PAGE_SIZE = 10;

	private static final Integer PAGE_INDEX = 1;
	
	private static final String FROM_DATE = "23-02-02";

	private static final String TO_DATE = "24-02-02";
	
	private static final String REQUEST_TYPE = "ACCV";
	
	private static final String TX_REF = "1233435";
	
	private static final String MSG_FUN = "ACCV";

	private static final Timestamp CRE_DT_TM = null;
	
	private static final String ACQR_ID = "acq2";
	
	private static final String REQUESTED_DATA = "yui768";
	
	private static final String TX_RES = "try456";
	
	private static final String INITG_PTY_ID = "dgdgj";
	
	private static final String MRCHNT_ID = "mer55";
	
	private static final Long NEXO_TXN_ID = 32l;
	
	@Before
	public void setUp() {
		getTranstionsRequest = new GetTranstionsRequest();
		getTranstionsRequest.setAcqrId(ACQR_ID);
		getTranstionsRequest.setCreDtTm(CRE_DT_TM);
		getTranstionsRequest.setFromDate(FROM_DATE);
		getTranstionsRequest.setInitgPtyId(INITG_PTY_ID);
		getTranstionsRequest.setMrchntId(MRCHNT_ID);
		getTranstionsRequest.setMsgFctn(MSG_FUN);
		getTranstionsRequest.setNexoTxnId(NEXO_TXN_ID);
		getTranstionsRequest.setPageIndex(PAGE_INDEX);
		getTranstionsRequest.setPageSize(PAGE_SIZE);
		getTranstionsRequest.setTxRspn(TX_RES);
		getTranstionsRequest.setTxRef(TX_REF);
		getTranstionsRequest.setToDate(TO_DATE);
		getTranstionsRequest.setRequestType(REQUEST_TYPE);
		getTranstionsRequest.setRequestData(REQUESTED_DATA);
	}
	
	@Test
	public void testGetTranstionsRespopnse() {
		Assert.assertEquals(ACQR_ID, getTranstionsRequest.getAcqrId());
		Assert.assertEquals(CRE_DT_TM, getTranstionsRequest.getCreDtTm());
		Assert.assertEquals(FROM_DATE, getTranstionsRequest.getFromDate());
		Assert.assertEquals(INITG_PTY_ID, getTranstionsRequest.getInitgPtyId());
		Assert.assertEquals(MRCHNT_ID, getTranstionsRequest.getMrchntId());
		Assert.assertEquals(MSG_FUN, getTranstionsRequest.getMsgFctn());
		Assert.assertEquals(NEXO_TXN_ID, getTranstionsRequest.getNexoTxnId());
		Assert.assertEquals(PAGE_INDEX, getTranstionsRequest.getPageIndex());
		Assert.assertEquals(PAGE_SIZE, getTranstionsRequest.getPageSize());
		Assert.assertEquals(TX_RES, getTranstionsRequest.getTxRspn());
		Assert.assertEquals(TX_REF, getTranstionsRequest.getTxRef());
		Assert.assertEquals(TO_DATE, getTranstionsRequest.getToDate());
		Assert.assertEquals(REQUEST_TYPE, getTranstionsRequest.getRequestType());
		Assert.assertEquals(REQUESTED_DATA, getTranstionsRequest.getRequestData());
	}
}
