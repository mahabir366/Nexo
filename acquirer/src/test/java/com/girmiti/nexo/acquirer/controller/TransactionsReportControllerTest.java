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
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;

import com.girmiti.nexo.acquirer.contorller.TransactionsReportController;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.Response;
import com.girmiti.nexo.acquirer.service.TransactionsReportService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionsReportControllerTest {
	@InjectMocks
	TransactionsReportController transactionReportController;
	
	@Mock
	HttpServletRequest requestToCache;

	@Mock
	HttpServletResponse httpResponse;
	
	@Mock
	BindingResult bindingResult;
	
	@Mock
	TransactionsReportService transactionService;
	
	@Mock
	GetTranstionsRequest request;
	
	@Mock
	ApplicationContext applicationContext;
	@Test
	public void testSaveRequest(){
		
		NexoTxn nexoTxn=new NexoTxn();
		NexoTxn response=new NexoTxn();
		Response respons =new Response();
		Mockito.when(transactionService.getTransactions(request)).thenReturn(respons);
		respons=transactionReportController.getTransactions(request, bindingResult, requestToCache, httpResponse);
		Assert.assertNotNull(response);
	}
	

}
