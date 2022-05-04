package com.girmiti.nexo.acquirer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;

import com.girmiti.nexo.acquirer.contorller.TransactionRestController;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.exception.RejectionException;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceRequest;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceResponse;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.DccRequest;
import com.girmiti.nexo.acquirer.pojo.DccResponse;
import com.girmiti.nexo.acquirer.pojo.ReconciliationRequest;
import com.girmiti.nexo.acquirer.pojo.ReconciliationResponse;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionResponse;
import com.girmiti.nexo.acquirer.service.TransactionService;
import com.girmiti.nexo.acquirer.service.impl.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionRestControllerTest {

	@InjectMocks
	TransactionRestController transactionRestController;

	@Mock
	HttpServletRequest requestToCache;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	BindingResult bindingResult;

	@Mock
	TransactionService transactionService;

	@Mock
	TransactionServiceImpl transactionServiceImpl;

	@Mock
	ApplicationContext applicationContext;
	@Mock
	private static ApplicationContext context;
	@Mock
	ApplicationContext appContext;

	@Value("${transaction.service.qualifier}")
	private String qualifierName = "girmiti";

	@Test
	public void testSaveRequest() {
		NexoTxn nexoTxn = new NexoTxn();
		NexoTxn response = new NexoTxn();
		Mockito.when(transactionService.updateTransactionResponse(nexoTxn)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.saveRequest(nexoTxn, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(response);
	}

	@Test
	public void testUpdateResponse() {
		NexoTxn nexoTxn = new NexoTxn();
		NexoTxn response = new NexoTxn();
		Mockito.when(transactionService.saveTransactionRequest(nexoTxn)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.updateResponse(nexoTxn, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(response);
	}

	@Test
	public void testGetDiagnosticStatus() {
		DaignosticTransactionResponse response = new DaignosticTransactionResponse();
		DaignosticTransactionRequest transactionRequest = new DaignosticTransactionRequest();
		Mockito.when(transactionService.getDiagnosticStatus(transactionRequest)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.getDiagnosticStatus(transactionRequest, bindingResult, requestToCache,
				httpResponse);
		Assert.assertNull(response);
	}

	@Test
	public void testgetCancellationAdviceStatus() {
		CancellationAdviceResponse response = null;
		CancellationAdviceRequest transactionRequest = null;
		Mockito.when(transactionService.getCancellationAdviceStatus(transactionRequest)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.getCancellationAdviceStatus(transactionRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(response);
	}

	@Test
	public void testProcessTransactionBasedOnStatus() {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processTransactionBasedOnStatus(transactionRequest))
				.thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		Assert.assertNull( transactionRestController.processTransactionBasedOnStatus(transactionRequest,
				requestToCache, bindingResult));
	}

	@Test
	public void testProcessSaleTransaction() {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processSaleTransaction(transactionRequest)).thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		Assert.assertNull(transactionRestController.processSaleTransaction(transactionRequest, bindingResult,
				requestToCache, httpResponse));
	}

	@Test
	public void testProcessAuthTransaction() throws RejectionException {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processAuthTransaction(transactionRequest)).thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		Assert.assertNull(transactionRestController.processAuthTransaction(transactionRequest, bindingResult,
				requestToCache, httpResponse));
	}

	@Test
	public void testProcessCaptureTransaction() {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processCaptureTransaction(transactionRequest)).thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		Assert.assertNull(transactionRestController.processCaptureTransaction(transactionRequest, bindingResult,
				requestToCache, httpResponse));
	}

	@Test
	public void testFindByPgTxRef() {
		NexoTxn nexoTxn = new NexoTxn();
		NexoTxn response = new NexoTxn();
		Mockito.when(transactionService.findByPgTxRef(nexoTxn)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.findByPgTxRef(nexoTxn, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(response);
	}

	@Test
	public void testBatchProcessTransaction() {
		BulkTransactionResponse response = new BulkTransactionResponse();
		BulkTransactionRequest request = new BulkTransactionRequest();
		Mockito.when(transactionService.batchProcessTransaction(request)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.batchProcessTransaction(request, requestToCache, httpResponse);
		Assert.assertNull(response);
	}

	@Test
	public void testProcessReconcialtionTransaction() {
		ReconciliationResponse response = new ReconciliationResponse();
		ReconciliationRequest reconciliationRequest = new ReconciliationRequest();
		Mockito.when(transactionService.processReconcialtionTransaction(reconciliationRequest)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		response = transactionRestController.processReconcialtionTransaction(reconciliationRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testGetCurrencyConversionRate() {
		DccRequest dccRequest = new DccRequest();
		DccResponse dccResponse = new DccResponse();
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionService.getCurrencyConversionRate(dccRequest)).thenReturn(dccResponse);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		dccResponse = transactionRestController.getCurrencyConversionRate(dccRequest, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(dccResponse);
	}
	
	@Test
	public void testUpdateCurrencyConversionTransaction() {
		DccRequest dccRequest = new DccRequest();
		DccResponse dccResponse = new DccResponse();
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionService.updateCurrencyConversionTransaction(dccRequest)).thenReturn(dccResponse);
		Mockito.when(transactionRestController.getTransactionServiceBean()).thenReturn(transactionServiceImpl);
		dccResponse = transactionRestController.updateCurrencyConversionTransaction(dccRequest, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(dccResponse);
	}
	
	@Test
	public void testGetDiagnosticStatusException() {
		DaignosticTransactionResponse response = new DaignosticTransactionResponse();
		DaignosticTransactionRequest transactionRequest = new DaignosticTransactionRequest();
		Mockito.when(transactionService.getDiagnosticStatus(transactionRequest)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.getDiagnosticStatus(transactionRequest, bindingResult, requestToCache,
				httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testgetCancellationAdviceStatusException() {
		CancellationAdviceResponse response = null;
		CancellationAdviceRequest transactionRequest = null;
		Mockito.when(transactionService.getCancellationAdviceStatus(transactionRequest)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.getCancellationAdviceStatus(transactionRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testProcessSaleTransactionException() {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processSaleTransaction(transactionRequest)).thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		transactionResponse = transactionRestController.processSaleTransaction(transactionRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(transactionResponse);
	}
	
	@Test
	public void testProcessAuthTransactionException() throws RejectionException {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processAuthTransaction(transactionRequest)).thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		transactionResponse = transactionRestController.processAuthTransaction(transactionRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(transactionResponse);
	}
	
	@Test
	public void testProcessCaptureTransactionException() {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processCaptureTransaction(transactionRequest)).thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		transactionResponse = transactionRestController.processCaptureTransaction(transactionRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(transactionResponse);
	}
	
	@Test
	public void testFindByPgTxRefException() {
		NexoTxn nexoTxn = new NexoTxn();
		NexoTxn response = new NexoTxn();
		Mockito.when(transactionService.findByPgTxRef(nexoTxn)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.findByPgTxRef(nexoTxn, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testBatchProcessTransactionException() {
		BulkTransactionResponse response = new BulkTransactionResponse();
		BulkTransactionRequest request = new BulkTransactionRequest();
		Mockito.when(transactionService.batchProcessTransaction(request)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.batchProcessTransaction(request, requestToCache, httpResponse);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testProcessReconcialtionTransactionException() {
		ReconciliationResponse response = new ReconciliationResponse();
		ReconciliationRequest reconciliationRequest = new ReconciliationRequest();
		Mockito.when(transactionService.processReconcialtionTransaction(reconciliationRequest)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.processReconcialtionTransaction(reconciliationRequest, bindingResult,
				requestToCache, httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testGetCurrencyConversionRateException() {
		DccRequest dccRequest = new DccRequest();
		DccResponse dccResponse = new DccResponse();
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionService.getCurrencyConversionRate(dccRequest)).thenReturn(dccResponse);
		dccResponse = transactionRestController.getCurrencyConversionRate(dccRequest, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(dccResponse);
	}
	
	@Test
	public void testUpdateCurrencyConversionTransactionException() {
		DccRequest dccRequest = new DccRequest();
		DccResponse dccResponse = new DccResponse();
		transactionRestController.setApplicationContext(appContext);
		Mockito.when(transactionService.updateCurrencyConversionTransaction(dccRequest)).thenReturn(dccResponse);
		dccResponse = transactionRestController.updateCurrencyConversionTransaction(dccRequest, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(dccResponse);
	}
	
	@Test
	public void testSaveRequestException() {
		NexoTxn nexoTxn = new NexoTxn();
		NexoTxn response = new NexoTxn();
		Mockito.when(transactionService.updateTransactionResponse(nexoTxn)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.saveRequest(nexoTxn, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testUpdateResponseException() {
		NexoTxn nexoTxn = new NexoTxn();
		NexoTxn response = new NexoTxn();
		Mockito.when(transactionService.saveTransactionRequest(nexoTxn)).thenReturn(response);
		transactionRestController.setApplicationContext(appContext);
		response = transactionRestController.updateResponse(nexoTxn, bindingResult, requestToCache, httpResponse);
		Assert.assertNull(response);
	}
	
	@Test
	public void testProcessTransactionBasedOnStatusException() {
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionRequest transactionRequest = new TransactionRequest();
		Mockito.when(transactionService.processTransactionBasedOnStatus(transactionRequest))
				.thenReturn(transactionResponse);
		transactionRestController.setApplicationContext(appContext);
		transactionResponse = transactionRestController.processTransactionBasedOnStatus(transactionRequest,
				requestToCache, bindingResult);
		Assert.assertNotNull(transactionResponse);
	}
}