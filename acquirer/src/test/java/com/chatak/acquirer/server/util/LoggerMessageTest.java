package com.chatak.acquirer.server.util;

import org.junit.Assert;
import org.junit.Test;

import com.girmiti.nexo.util.LoggerMessage;

public class LoggerMessageTest {

	@Test
	public void getCallerName() {
		Assert.assertNotNull(LoggerMessage.getCallerName());
	}

	@Test
	public void testGetCallerName() {
		Assert.assertNotNull(LoggerMessage.testGetCallerName());
	}

}
