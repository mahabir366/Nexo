package com.girmiti.nexo.acquirer.dao.model;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
public class NexoTxnTest {
	@InjectMocks
	NexoTxn nexoTxn;

	private static final Long NEXO_TXN_ID=10l;
	private static final String REQUEST_TYPE="request type";
	private static final String MSG_FCTN="message function";
	private static final Timestamp CRE_DT_TM=null;
	private static final String ACQRR_ID="acqrr id";
	private static final String INITG_PTY_ID="pty id";
	private static final String MRCHNT_ID="mechny id";
	private static final String TX_TX_REF="tx ref";
	private static final String REQUEST_DATA="request data";
	private static final String RESPONSE_DATA="response data";
	private static final String TX_RSPN="txn response";
	private static final String PG_TXN_REF="pg txn ref";
	private static final String CAPTURE_STATUS="capture status";
	private static final String CG_TXN_REF="txn ref";


	@Before
	public void setUp() {
	nexoTxn = new NexoTxn();
	nexoTxn.setNexoTxnId(NEXO_TXN_ID);
	nexoTxn.setRequestType(REQUEST_TYPE);
	nexoTxn.setMsgFctn(MSG_FCTN);
	nexoTxn.setCreDtTm(CRE_DT_TM);
	nexoTxn.setAcqrrId(ACQRR_ID);
	nexoTxn.setInitgPtyId(INITG_PTY_ID);
	nexoTxn.setMrchntId(MRCHNT_ID);
	nexoTxn.setTxTxRef(TX_TX_REF);
	nexoTxn.setRequestData(REQUEST_DATA);
	nexoTxn.setResponseData(RESPONSE_DATA);
	nexoTxn.setTxRspn(TX_RSPN);
	nexoTxn.setPgTxnRef(PG_TXN_REF);
	nexoTxn.setCaptureStatus(CAPTURE_STATUS);
	nexoTxn.setCgTxnRef(CG_TXN_REF);
	}

	@Test
	public void testNexoTxn(){
	Assert.assertEquals(NEXO_TXN_ID,nexoTxn.getNexoTxnId());
	Assert.assertEquals(REQUEST_TYPE,nexoTxn.getRequestType());
	Assert.assertEquals(MSG_FCTN,nexoTxn.getMsgFctn());
	Assert.assertEquals(CRE_DT_TM,nexoTxn.getCreDtTm());
	Assert.assertEquals(ACQRR_ID,nexoTxn.getAcqrrId());
	Assert.assertEquals(INITG_PTY_ID,nexoTxn.getInitgPtyId());
	Assert.assertEquals(MRCHNT_ID,nexoTxn.getMrchntId());
	Assert.assertEquals(TX_TX_REF,nexoTxn.getTxTxRef());
	Assert.assertEquals(REQUEST_DATA,nexoTxn.getRequestData());
	Assert.assertEquals(RESPONSE_DATA,nexoTxn.getResponseData());
	Assert.assertEquals(TX_RSPN,nexoTxn.getTxRspn());
	Assert.assertEquals(PG_TXN_REF,nexoTxn.getPgTxnRef());
	Assert.assertEquals(CAPTURE_STATUS,nexoTxn.getCaptureStatus());
	Assert.assertEquals(CG_TXN_REF,nexoTxn.getCgTxnRef());
	}

}
