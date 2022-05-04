package com.girmiti.nexo.acquirer.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "currencyCodeEnum")
@XmlEnum
public enum CurrencyCodeEnum {

	AUD, CAD, CHF, DKK, EUR, GBP, HKD, JPY, NOK, NZD, SEK, SGD, USD;

	public String value() {
		return name();
	}

	public static CurrencyCodeEnum fromValue(String v) {
		return valueOf(v);
	}

}
