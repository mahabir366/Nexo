package com.girmiti.nexo.acquirer.enums;

public enum CardAssociationEnum {

	MC("MC"), VI("VI"), AX("AX"), DC("DC"), DI("DI"), PP("PP"), JC("JC"), BL("BL"), EC("EC"), GC("GC"), BLANK("");
	private final String value;

	CardAssociationEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static CardAssociationEnum fromValue(String v) {
		for (CardAssociationEnum c : CardAssociationEnum.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
