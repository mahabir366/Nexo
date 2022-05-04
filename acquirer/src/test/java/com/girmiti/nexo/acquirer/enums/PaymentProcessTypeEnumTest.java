package com.girmiti.nexo.acquirer.enums;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessTypeEnumTest {

	InjectMocks PaymentProcessTypeEnum;
	PaymentProcessTypeEnum ex;

	private static final Logger LOGGER = LoggerFactory.getLogger(EntryModeEnumTest.class);

	@Test
	public void test() throws Exception{

		try {
			Assert.assertNotNull(ex.fromValue("from"));
		} catch (Exception e) {
			LOGGER.error("ERROR :: EntryModeEnumTest method test", e);
		}
		
		
	}
}
