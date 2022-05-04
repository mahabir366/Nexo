package com.girmiti.nexo.acquirer.enums;

public enum EntryModeEnum {
	UNSPECIFIED("00"), MANUAL("01"), MAGNETIC_STRIP("02"), BARCODE("03"), OCR("04"), ICC("05"), MANUAL_KEY_ENTRY(
			"06"), PAN_AUTO_ENTRY_CONTACTLESS_M_CHIP("07"), PAN_AUTO_ENTRY_ECOMMERCE("09"), PAN_MANUAL_ENTRY_CHIP(
					"79"), PAN_SWIPE_CHIP("80"), PAN_MANUAL_ENTRY_ECOMMERCE("81"), PAN_SWIPE_CONTACTLESS(
							"91"), PAN_MANUAL_ENTRY_CONTACTLESS("92"), CARD_TOKEN("19"), PAN_TAP_NFC(
									"61"), PAN_SCAN_BAR("62"), PAN_SCAN_QR("63"), PAN_SCAN_BLE(
											"64"), CASH("99"), QR_SALE("33"), CARD_TAP("34"), ACCOUNT_PAY("35");

	private final String value;

	EntryModeEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static EntryModeEnum fromValue(String v) {
		for (EntryModeEnum c : EntryModeEnum.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		return UNSPECIFIED;
	}

	public static String getValue(String v) {
		if (null != v && v.length() > Integer.parseInt("2")) {
			v = v.substring(0, Integer.parseInt("2"));
		}
		for (EntryModeEnum c : EntryModeEnum.values()) {
			if (c.value.equals(v)) {
				return c.name();
			}
		}
		return CASH.name();
	}
}
