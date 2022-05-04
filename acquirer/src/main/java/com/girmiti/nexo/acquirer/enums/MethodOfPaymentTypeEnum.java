package com.girmiti.nexo.acquirer.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "methodOfPaymentTypeEnum")
@XmlEnum
public enum MethodOfPaymentTypeEnum {

	MC("MC"), VI("VI"), AX("AX"), DC("DC"), DI("DI"), PP("PP"), JC("JC"), BL("BL"), EC("EC"), GC("GC"), ME("ME"), IP(
			"IP"), @XmlEnumValue("")
	BLANK("");
	private final String value;

	MethodOfPaymentTypeEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static MethodOfPaymentTypeEnum fromValue(String v) {
		for (MethodOfPaymentTypeEnum c : MethodOfPaymentTypeEnum.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
