package com.girmiti.nexo.acquirer.pojo;

import java.math.BigDecimal;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthTransactionProcessorRequest {
	private String reqTxRef;
	private String pan;
	private String cvv;
	private String expiryDate;
	private BigDecimal reqTotalAmt;
	private String msgFunction;
	private String initgPty;
	private String PrtcolVrsn;

}
