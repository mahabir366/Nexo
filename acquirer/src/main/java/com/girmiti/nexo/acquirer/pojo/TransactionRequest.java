package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;
import java.util.List;

import com.girmiti.nexo.acceptorauthorizationrequest.DisplayCapabilities4;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long txnAmount;

	private String merchantName;

	private String invoiceNumber;

	private String registerNumber;

	private String cardToken;

	private CardData cardData;

	private BillingData billingData;

	private String orderId;

	private String txnRefNumber;

	private String authId;

	private String ipPort;

	private String cgRefNumber;

	private Long merchantAmount;

	private Long feeAmount;

	private Long totalTxnAmount;

	private String description;

	private SplitTxnData splitTxnData;

	private String splitRefNumber;

	private String mobileNumber;

	private String accountNumber;

	private CardTokenData cardTokenData;

	private String qrCode;

	private String currencyCode;

	private String userName;

	private String timeZoneOffset;

	private String timeZoneRegion;
	
	private String pgTxnRef;
	
	private String reconciliationId;
	
	private String msgFunction;
	
	private String initgPty;
	
	private String PrtcolVrsn;
	
	private List<DisplayCapabilities4> reqMsgCpbltiesList;
}
