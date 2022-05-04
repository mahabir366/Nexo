package com.chatak.acquirer.server.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import com.girmiti.nexo.util.LogHelper;

@RunWith(MockitoJUnitRunner.class)
public class LogHelperTest {
	@Mock
	Logger logger;
	
	@Mock
	Throwable e;
	
	@Test
	public void testlogEntry() {
		String classMethodInfo = "classMethodInfo";
		LogHelper.logEntry(logger, classMethodInfo);
		Assert.assertNotNull(classMethodInfo);
	}
	@Test
	public void testlogExit() {
		String classMethodInfo = "classMethodInfo";
		LogHelper.logExit(logger, classMethodInfo);
		Assert.assertNotNull(classMethodInfo);
	}
	@Test
	public void testlogPerformance() {
		String classMethodInfo = "classMethodInfo";
		Long startTs = 56l;
		Long endTs =97l;
		LogHelper.logPerformance(logger, classMethodInfo, startTs, endTs);
		Assert.assertNotNull(classMethodInfo);
	}
	@Test
	public void testlogInfo() {
		String classMethodInfo = "classMethodInfo";
		String message ="messag";
		LogHelper.logInfo(logger, classMethodInfo, message);
		Assert.assertNotNull(classMethodInfo);
	}
	@Test
	public void testlogDebug() {
		String classMethodInfo = "classMethodInfo";
		String message ="messag";
		LogHelper.logDebug(logger, classMethodInfo, message);
		Assert.assertNotNull(classMethodInfo);
	}
	@Test
	public void testlogError() {
		String classMethodInfo = "classMethodInfo";
		String message ="messag";
		LogHelper.logError(logger, classMethodInfo, message);
		Assert.assertNotNull(classMethodInfo);
	}
	@Test
	public void testlogErrorWithErrorValue() {
		String classMethodInfo = "classMethodInfo";
		String message ="messag";
		LogHelper.logError(logger, classMethodInfo,e, message);
		Assert.assertNotNull(classMethodInfo);
	}

}
