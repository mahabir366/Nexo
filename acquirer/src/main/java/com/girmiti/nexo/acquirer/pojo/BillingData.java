package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillingData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address1;

	private String city;

	private String address2;

	private String country;

	private String state;

	private String email;

	private String zipCode;

}
