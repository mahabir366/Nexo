package com.girmiti.nexo.acquirer.enums;

public enum PaymentProcessTypeEnum {

	C("1"), T("2"), IB("3"), N("9");

	private final String value;

	PaymentProcessTypeEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static PaymentProcessTypeEnum fromValue(String v) {
		for (PaymentProcessTypeEnum c : PaymentProcessTypeEnum.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
