package com.girmiti.nexo.acquirer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.girmiti.nexo.acquirer.contorller.NexoAcquirerRestController;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataRequest;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class NexoAcquirerRestControllerTest {
	
	@InjectMocks
	NexoAcquirerRestController nexoAcquirerRestController;
	
	@Mock
	HttpServletRequest requestToCache;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	BindingResult bindingResult;
	
	@Test
	public void testProcessTransactionRequest() {
		NexoAcquirerDataRequest nexoAcquirerData = new NexoAcquirerDataRequest();
		nexoAcquirerData.setRequest("request");
		NexoAcquirerDataResponse nexoAcquirerDataResponse = nexoAcquirerRestController.processTransactionRequest(nexoAcquirerData, bindingResult, requestToCache, httpResponse);
		Assert.assertNotNull(nexoAcquirerDataResponse);
	}
	
	@Test
	public void testProcessTransactionRequestException() {
		NexoAcquirerDataRequest nexoAcquirerData = new NexoAcquirerDataRequest();
		NexoAcquirerDataResponse nexoAcquirerDataResponse = nexoAcquirerRestController.processTransactionRequest(nexoAcquirerData, bindingResult, requestToCache, httpResponse);
		Assert.assertNotNull(nexoAcquirerDataResponse);
	}

}
