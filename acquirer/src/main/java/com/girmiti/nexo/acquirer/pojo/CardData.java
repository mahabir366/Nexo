package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import com.girmiti.nexo.acquirer.enums.MethodOfPaymentTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cardNumber;

	private String expDate;

	private String cvv;

	private String cardHolderName;

	private MethodOfPaymentTypeEnum cardType;

	private String track1;

	private String track2;

	private String track3;

	private String track;

	private String keySerial;

	private String cardHolderEmail;

	private String emv;

	private String uid;
}
