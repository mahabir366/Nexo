package com.girmiti.nexo.acquirer.pojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
public class TranstionsResponseTest {
	@InjectMocks
	TransactionResponse transactionResponse;

	private static final String TXN_REF_NUMBER="1234566789";
	private static final String AUTH_ID="auth id";
	private static final PaymentDetails PAYMENT_DETAILS=null;
	private static final String CG_REF_NUMBER="987648743";
	private static final String MERCHANT_CODE="vds4642lg";
	private static final Long TOTAL_AMOUNT=100l;
	private static final Double TOTAL_TXN_AMOUNT=200.0;
	private static final String MERCHANT_NAME="merchant name";
	private static final String CUSTOMER_BALANCE="100";
	private static final String CURRENCY="INR";
	private static final String TXN_ID="txn id";
	private static final String PROC_TXN_ID="txn id";
	private static final String MERCHANT_ID="merchant id";
	private static final String TERMINAL_ID="terminal id";
	private static final String DEVICE_LOCAL_TXN_TIME="local time";
	private static final String TRANSACTION_TYPE="cash";
	private static final Boolean STATUS=true;
	private static final String RESPONSE="response";
	private static final String APPROVAL_STATUS="sucess";


	@Before
	public void setUp() {
	transactionResponse = new TransactionResponse();
	transactionResponse.setTxnRefNumber(TXN_REF_NUMBER);
	transactionResponse.setAuthId(AUTH_ID);
	transactionResponse.setPaymentDetails(PAYMENT_DETAILS);
	transactionResponse.setCgRefNumber(CG_REF_NUMBER);
	transactionResponse.setMerchantCode(MERCHANT_CODE);
	transactionResponse.setTotalAmount(TOTAL_AMOUNT);
	transactionResponse.setTotalTxnAmount(TOTAL_TXN_AMOUNT);
	transactionResponse.setMerchantName(MERCHANT_NAME);
	transactionResponse.setCustomerBalance(CUSTOMER_BALANCE);
	transactionResponse.setCurrency(CURRENCY);
	transactionResponse.setTxnId(TXN_ID);
	transactionResponse.setProcTxnId(PROC_TXN_ID);
	transactionResponse.setMerchantId(MERCHANT_ID);
	transactionResponse.setTerminalId(TERMINAL_ID);
	transactionResponse.setDeviceLocalTxnTime(DEVICE_LOCAL_TXN_TIME);
	transactionResponse.setTransactionType(TRANSACTION_TYPE);
	transactionResponse.setStatus(STATUS);
	transactionResponse.setResponse(RESPONSE);
	transactionResponse.setApprovalStatus(APPROVAL_STATUS);
	}

	@Test
	public void testTransactionResponse(){
	Assert.assertEquals(TXN_REF_NUMBER,transactionResponse.getTxnRefNumber());
	Assert.assertEquals(AUTH_ID,transactionResponse.getAuthId());
	Assert.assertEquals(PAYMENT_DETAILS,transactionResponse.getPaymentDetails());
	Assert.assertEquals(CG_REF_NUMBER,transactionResponse.getCgRefNumber());
	Assert.assertEquals(MERCHANT_CODE,transactionResponse.getMerchantCode());
	Assert.assertEquals(TOTAL_AMOUNT,transactionResponse.getTotalAmount());
	Assert.assertEquals(TOTAL_TXN_AMOUNT,transactionResponse.getTotalTxnAmount());
	Assert.assertEquals(MERCHANT_NAME,transactionResponse.getMerchantName());
	Assert.assertEquals(CUSTOMER_BALANCE,transactionResponse.getCustomerBalance());
	Assert.assertEquals(CURRENCY,transactionResponse.getCurrency());
	Assert.assertEquals(TXN_ID,transactionResponse.getTxnId());
	Assert.assertEquals(PROC_TXN_ID,transactionResponse.getProcTxnId());
	Assert.assertEquals(MERCHANT_ID,transactionResponse.getMerchantId());
	Assert.assertEquals(TERMINAL_ID,transactionResponse.getTerminalId());
	Assert.assertEquals(DEVICE_LOCAL_TXN_TIME,transactionResponse.getDeviceLocalTxnTime());
	Assert.assertEquals(TRANSACTION_TYPE,transactionResponse.getTransactionType());
	Assert.assertEquals(STATUS,transactionResponse.getStatus());
	Assert.assertEquals(RESPONSE,transactionResponse.getResponse());
	Assert.assertEquals(APPROVAL_STATUS,transactionResponse.getApprovalStatus());
	}


}
