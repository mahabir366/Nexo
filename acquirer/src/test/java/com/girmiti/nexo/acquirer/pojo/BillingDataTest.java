package com.girmiti.nexo.acquirer.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class BillingDataTest {
	
	@InjectMocks
	BillingData billingData;
	
	public static final String ADDRESS1 = "Brdway";
	
	public static final String ADDRESS2 = "Brdway";
	
	public static final String CITY = "NY";
	
	public static final String COUNTRY = "US";
	
	public static final String STATE = "CAL";
	
	public static final String ZIP_CODE = "230119";
	
	public static final String EMAIL = "NYC@mail.com";
	
	@Before
	public void setUp() {
		billingData = new BillingData();
		billingData.setAddress1(ADDRESS1);
		billingData.setAddress2(ADDRESS2);
		billingData.setCity(CITY);
		billingData.setCountry(COUNTRY);
		billingData.setEmail(EMAIL);
		billingData.setState(STATE);
		billingData.setZipCode(ZIP_CODE);
	}
	
	@Test
	public void testBillingDataRespopnse() {
		Assert.assertEquals(ADDRESS1,billingData.getAddress1());
		Assert.assertEquals(ADDRESS2,billingData.getAddress2());
		Assert.assertEquals(CITY,billingData.getCity());
		Assert.assertEquals(STATE,billingData.getState());
		Assert.assertEquals(COUNTRY,billingData.getCountry());
		Assert.assertEquals(EMAIL,billingData.getEmail());
		Assert.assertEquals(ZIP_CODE,billingData.getZipCode());
	}
}
