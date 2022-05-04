package com.girmiti.nexo.acquirer.pojo;

import com.girmiti.nexo.acceptorrejection.RejectReason1Code;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionResponse extends Response {

	private static final long serialVersionUID = 332371718850372885L;

	private String txnRefNumber;

	private String authId;

	private PaymentDetails paymentDetails;

	private String cgRefNumber;

	private String merchantCode;

	private Long totalAmount;

	private Double totalTxnAmount;

	private String merchantName;

	private String customerBalance;

	private String currency;

	private String txnId;

	private String procTxnId;

	private String merchantId;

	private String terminalId;

	private String deviceLocalTxnTime;

	private String transactionType;

	private Boolean status;
	
	private String response;
	
	private String approvalStatus;
	
	private String rrn;

	private String responseReason;
	

	private RejectReason1Code rejectionCode;
	
	private String rejectionReason;

	private boolean cmpltnReqrd;
	
	private String authstnCd;
	
	private String invoiceNumber;


}
