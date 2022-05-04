package com.girmiti.nexo.acquirer.enums;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class EntryModeEnumTest {

	InjectMocks EntryModeEnum;
	EntryModeEnum ex;

	private static final Logger LOGGER = LoggerFactory.getLogger(EntryModeEnumTest.class);

	@Test
	public void test() {

		try {
			ex = null;
		} catch (Exception e) {
			LOGGER.error("ERROR :: EntryModeEnumTest method test", e);
		}
		ex.fromValue("from");
		Assert.assertNotNull(ex.getValue("enum"));
	}
}
