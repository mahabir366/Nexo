package com.chatak.acquirer.server.util;

import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.girmiti.nexo.util.LoggersUtil;


@RunWith(MockitoJUnitRunner.class)
public class LoggersUtilTest {

	@Test
	public void testlogIpUrlRequest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("ipAddress", "someValue");
		Assert.assertNotNull(LoggersUtil.logIpUrlRequest(request));
	}
	@Test
	public void testlogIpUrlRequestForElse() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Assert.assertNotNull(LoggersUtil.logIpUrlRequest(request));
	}
	@Test
	public void testlogIpUrl() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("ipAddress", "someValue");
		Assert.assertNotNull(LoggersUtil.logIpUrl(request));
	}
	@Test
	public void testlogIpUrlForElse() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Assert.assertNotNull(LoggersUtil.logIpUrl(request));
	}
	
}