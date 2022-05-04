package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.girmiti.nexo.acquirer.enums.EntryModeEnum;
import com.girmiti.nexo.acquirer.enums.ShareModeEnum;
import com.girmiti.nexo.acquirer.enums.TransactionType;

public class RequestTest {
	
	@InjectMocks	
	Request request;
	
	private  static final String CREATED_BY = "admin";

	private  static final String ORIGINAL_CHANNEL = "pg";

	private  static final String MERCHANT_CODE = "75489679814456466";

	private  static final String TREMINAL_ID = "ter45677";

	private  static final TransactionType TRANSACTION_TYPE = TransactionType.AUTH;

	private  static final EntryModeEnum ENTRY_MODE = EntryModeEnum.CASH;

	private  static final ShareModeEnum SHARE_MODE = ShareModeEnum.PAY_SOMEONE;

	private  static final String POS_ENTRY_MODE = "yes";

	private  static final String MODE = "loop";

	private  static final String PROCESSOR_MID = "p00098908";

	@Before
	public void setUp() {
		request = new Request();
		request.setCreatedBy(CREATED_BY);
		request.setEntryMode(ENTRY_MODE);
		request.setMerchantCode(MERCHANT_CODE);
		request.setMode(MODE);
		request.setOriginChannel(ORIGINAL_CHANNEL);
		request.setPosEntryMode(POS_ENTRY_MODE);
		request.setProcessorMid(PROCESSOR_MID);
		request.setShareMode(SHARE_MODE);
		request.setTerminalId(TREMINAL_ID);
		request.setTransactionType(TRANSACTION_TYPE);
	}
	
	@Test
	public void testRespopnse() {
		Assert.assertEquals(CREATED_BY, request.getCreatedBy());
		Assert.assertEquals(ENTRY_MODE, request.getEntryMode());
		Assert.assertEquals(MERCHANT_CODE, request.getMerchantCode());
		Assert.assertEquals(MODE, request.getMode());
		Assert.assertEquals(ORIGINAL_CHANNEL, request.getOriginChannel());
		Assert.assertEquals(POS_ENTRY_MODE, request.getPosEntryMode());
		Assert.assertEquals(PROCESSOR_MID, request.getProcessorMid());
		Assert.assertEquals(SHARE_MODE, request.getShareMode());
		Assert.assertEquals(TREMINAL_ID, request.getTerminalId());
		Assert.assertEquals(TRANSACTION_TYPE, request.getTransactionType());
	}
}
