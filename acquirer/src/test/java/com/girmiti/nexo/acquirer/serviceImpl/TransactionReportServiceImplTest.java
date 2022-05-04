package com.girmiti.nexo.acquirer.serviceImpl;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.girmiti.nexo.acquirer.dao.TransactionDao;
import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.Response;
import com.girmiti.nexo.acquirer.pojo.TransactionReportResponse;
import com.girmiti.nexo.acquirer.service.impl.TransactionReportServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionReportServiceImplTest {

	@InjectMocks
	TransactionReportServiceImpl transactionReportServiceImpl;
	@Mock
	TransactionDao transtionDao;

	@Test
	public void testgetTransactions() {
		TransactionReportResponse response = new TransactionReportResponse();
		Response respons = new Response();
		GetTranstionsRequest request = new GetTranstionsRequest();
		request.setTxRef("txn");
		Mockito.when(transtionDao.getTransactions(request)).thenReturn(response);
		Mockito.when(transactionReportServiceImpl.getTransactions(request)).thenReturn(response);
		respons = transactionReportServiceImpl.getTransactions(request);
		Assert.assertNotNull(response);
	}

	@Test
	public void testgetTransactionsWithException() {
		TransactionReportResponse response = new TransactionReportResponse();
		Response respons = new Response();
		GetTranstionsRequest request = null;
		Mockito.when(transtionDao.getTransactions(request)).thenReturn(response);
		Mockito.when(transactionReportServiceImpl.getTransactions(request)).thenReturn(response);
		respons = transactionReportServiceImpl.getTransactions(request);
		Assert.assertNull(null);
	}

}
