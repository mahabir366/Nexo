package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import com.girmiti.nexo.acquirer.enums.CardAssociationEnum;
import com.girmiti.nexo.acquirer.enums.CountryTypeEnum;
import com.girmiti.nexo.acquirer.enums.CurrencyCodeEnum;
import com.girmiti.nexo.acquirer.enums.PaymentProcessTypeEnum;
import com.girmiti.nexo.acquirer.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public PaymentDetails() {

	}

	public PaymentDetails(Long transactionId, TransactionType transactionType, String orderId, Long totalAmount,
			Long merchantAmount, CardAssociationEnum cardAssociation, String description, String billerName,
			String billerEmail, String billerCity, String billerState, CountryTypeEnum billerCountry, String billerZip,
			String address, String address2, String merchantId, String returnURL, CurrencyCodeEnum currencyCode,
			PaymentProcessTypeEnum paymentProcessTypeEnum, String token, String accessToken) {
		super();
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.merchantAmount = merchantAmount;
		this.cardAssociation = cardAssociation;
		this.description = description;
		this.billerName = billerName;
		this.billerEmail = billerEmail;
		this.billerCity = billerCity;
		this.billerState = billerState;
		this.billerCountry = billerCountry;
		this.billerZip = billerZip;
		this.address = address;
		this.address2 = address2;
		this.returnURL = returnURL;
		this.merchantId = merchantId;
		this.formatedTotalAmt = String.valueOf(this.totalAmount / 100);
		this.currencyCode = currencyCode;
		this.paymentProcessTypeEnum = paymentProcessTypeEnum;
		this.token = token;
		this.accessToken = accessToken;
	}

	private Long transactionId;
	private TransactionType transactionType;
	private String orderId;
	private Long totalAmount;
	private Long merchantAmount;
	private CardAssociationEnum cardAssociation;
	private String description;
	private String billerName;
	private String billerEmail;
	private String billerCity;
	private String billerState;
	private CountryTypeEnum billerCountry;
	private String billerZip;
	private String address;
	private String address2;
	private String returnURL;
	private String merchantId;
	private String clientIP;
	private Integer clientPort;
	private Long originTime;
	private CurrencyCodeEnum currencyCode;
	private String formatedTotalAmt;
	private PaymentProcessTypeEnum paymentProcessTypeEnum;
	private String token;
	private String accessToken;
	private String mode;
	private String processorMid;

}
