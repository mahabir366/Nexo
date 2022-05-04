package com.girmiti.nexo.util;

import com.neovisionaries.i18n.CountryCode;

public class CountryValidationUtil {
	
	public static final String REGEX = "\\d+";
	
	private CountryValidationUtil() {
		//Do nothing
	}

	public static boolean validateCountry(String cardCtryCd) {
		CountryCode countryNumericCode = null;
		
		CountryCode countryCode = null;
		
		if (cardCtryCd.matches(REGEX)) {
			countryNumericCode = CountryCode.getByCode(Integer.valueOf(cardCtryCd));
		} else {
			countryCode = CountryCode.getByCodeIgnoreCase(cardCtryCd);
		}
	return	(countryCode == null && countryNumericCode == null); 
	}

	public static boolean isCountryNotNull(String cardCtryCd) {
		
		return (cardCtryCd != null);
	}
}
