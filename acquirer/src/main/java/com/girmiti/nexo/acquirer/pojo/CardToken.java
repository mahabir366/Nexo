package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardToken implements Serializable {

	private static final long serialVersionUID = 8833602466122869560L;

	private String token;

	private String cardLastFourDigit;

	private String cardType;

	private String cvv;

	private String email;

}
