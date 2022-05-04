package com.girmiti.nexo.acquirer.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionReportResponseTest {
	
	@InjectMocks
	TransactionReportResponse transactionReportResponse;
	
	@Mock
	GetTranstionsRequest getTranstionsRequest;
	
	List<GetTranstionsRequest> getTranstionsRequests = new ArrayList<>();

	private static final Integer TOTAL_NO_OF_RECORDS = 99;
	
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
		transactionReportResponse = new TransactionReportResponse(); 
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
		getTranstionsRequests.add(getTranstionsRequest);
		transactionReportResponse.setTotalNoOfRecords(TOTAL_NO_OF_RECORDS);
		transactionReportResponse.setTransactionList(getTranstionsRequests);
	}
	
	
	@Test
	public void testReconciliationRespopnse() {
		Assert.assertEquals(TOTAL_NO_OF_RECORDS, transactionReportResponse.getTotalNoOfRecords());
		Assert.assertEquals(getTranstionsRequests, transactionReportResponse.getTransactionList());
	}
}
