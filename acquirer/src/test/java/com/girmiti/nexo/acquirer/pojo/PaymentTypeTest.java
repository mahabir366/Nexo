package com.girmiti.nexo.acquirer.pojo;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PaymentTypeTest {

	@InjectMocks
	PaymentType paymentType;
	
	private static final String PAYMENT_METHOD = "cash";

	private static final BigDecimal TXN_TOTAL_COUNT = BigDecimal.ONE;

	private static final BigDecimal TXN_TOTAL_AMOUNT = BigDecimal.TEN;
	
	@Before
	public void setUp() {
		paymentType = new PaymentType();
		paymentType.setPaymentMethod(PAYMENT_METHOD);
		paymentType.setTxnTotalAmount(TXN_TOTAL_AMOUNT);
		paymentType.setTxnTotalCount(TXN_TOTAL_COUNT);
	}
	
	@Test
	public void testPaymentTypeRespopnse() {
		Assert.assertEquals(PAYMENT_METHOD, paymentType.getPaymentMethod());
		Assert.assertEquals(TXN_TOTAL_COUNT, paymentType.getTxnTotalCount());
		Assert.assertEquals(TXN_TOTAL_AMOUNT, paymentType.getTxnTotalAmount());
	}
}
