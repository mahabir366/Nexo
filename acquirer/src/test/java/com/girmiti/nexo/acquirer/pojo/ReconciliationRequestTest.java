package com.girmiti.nexo.acquirer.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ReconciliationRequestTest {
	
	@InjectMocks
	ReconciliationRequest reconciliationRequest;
	
	@Mock
	PaymentType paymentType;

	List<PaymentType> PAYMENT_TYPES = new ArrayList<>();
	
	private static final String RECONCILATION_ID = "r099";
	
	private static final String PAYMENT_METHOD = "cash";

	private static final BigDecimal TXN_TOTAL_COUNT = BigDecimal.ONE;

	private static final BigDecimal TXN_TOTAL_AMOUNT = BigDecimal.TEN;
	
	@Before
	public void setUp() {
		reconciliationRequest = new ReconciliationRequest();
		paymentType = new PaymentType();
		reconciliationRequest.setReconciliationId(RECONCILATION_ID);
		paymentType.setPaymentMethod(PAYMENT_METHOD);
		paymentType.setTxnTotalAmount(TXN_TOTAL_AMOUNT);
		paymentType.setTxnTotalCount(TXN_TOTAL_COUNT);
		PAYMENT_TYPES.add(paymentType);
		reconciliationRequest.setPaymentType(PAYMENT_TYPES);
	}
	
	@Test
	public void testReconciliationRespopnse() {
		Assert.assertEquals(RECONCILATION_ID, reconciliationRequest.getReconciliationId());
		Assert.assertEquals(PAYMENT_TYPES, reconciliationRequest.getPaymentType());
	}
}
