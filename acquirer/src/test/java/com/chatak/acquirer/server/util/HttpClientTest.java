package com.chatak.acquirer.server.util;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.girmiti.nexo.acquirer.exception.HttpClientException;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.util.AcquirerProperties;
import com.girmiti.nexo.util.HttpClient;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HttpClientTest {


	@Mock
	HttpServletRequest requestToCache;

	@Mock
	HttpServletResponse httpResponse;

	@Before
	public void setUp() {
		java.util.Properties properties = new Properties();
		properties.setProperty("thread.pool.size", "56");
		properties.setProperty("thread.max.per.route","23");
		properties.setProperty("http.client.connection.manager.maxtotal","7");
		properties.setProperty("http.client.connection.manager.defaultmaxperroute","77");
		properties.setProperty("http.client.connection.manager.maxperroute","8080");
		properties.setProperty("http.client.connection.manager.soc.timeout","10");
		properties.setProperty("chatak-merchant.consumer.client.id","id");
		properties.setProperty("chatak-merchant.consumer.client.secret","secrect");
		properties.setProperty("chatak-merchant.service.url","base Uri");
		com.girmiti.nexo.util.AcquirerProperties.mergeProperties(properties);
	}
	
	@Test
	public void testInvokeGet() throws IOException {
		RestTemplate restTemplet=null;
		HttpClient httpClient=new HttpClient("http://localhost:9090/acquirerService/transaction/1/0/", "saveTransactionRequest");
		Assert.assertNull(httpClient.invokeGet(TransactionRequest.class, "accessToken"));
	}
	
	@Test
	public void testInvokePost() throws HttpClientException, IOException {
		Header[] headers = null;
		HttpClient httpClient=new HttpClient("http://localhost:9090/acquirerService/transaction/1/0/", "saveTransactionRequest");
		Object object = new Object();
		httpClient.postRequest(object,TransactionRequest.class,"saveTransactionRequest");
		httpClient.invokeGetCommon(TransactionRequest.class,headers);
		httpClient.invokePost(object,TransactionRequest.class, "token");
		httpClient.invokePostRequest(object,TransactionRequest.class,"saveTransactionRequest");
		httpClient.invokePost(object, TransactionRequest.class,true,  "serviceToken");
		httpClient.invokePost(object, TransactionRequest.class,false,  "serviceToken");
		httpClient.invokeGet(TransactionRequest.class,headers);
		Assert.assertNull(httpClient.invokeGet(TransactionRequest.class, "accessToken"));
	}
}
