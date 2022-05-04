package com.girmiti.nexo.acquirer.pojo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentType {

	private String paymentMethod;

	private BigDecimal txnTotalCount;

	private BigDecimal txnTotalAmount;

}
