package com.girmiti.nexo.acquirer.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HttpClientExceptionTest {

	@Test
	public void test() {
		String httpErrorCode = "exception";
		int statusCode = 123;
		HttpClientException httpClientException = new HttpClientException(httpErrorCode, statusCode);
		httpClientException.setHttpErrorCode(httpErrorCode);
		httpClientException.setStatusCode(statusCode);
		httpClientException.getHttpErrorCode();
		Assert.assertNotNull(httpClientException.getStatusCode());
	}
}
