package com.girmiti.nexo.util;

import java.util.Currency;

public class CurrencyValidationUtil {
	
	private CurrencyValidationUtil() {
		//Do nothing
	}

	public static boolean validateCurrency(String code) {
		try {
			Currency currencyCode = Currency.getInstance(code);
			if (currencyCode == null) {
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public static boolean isCurrencyNotNull(String ccy) {
		return (ccy != null);
	}
}
