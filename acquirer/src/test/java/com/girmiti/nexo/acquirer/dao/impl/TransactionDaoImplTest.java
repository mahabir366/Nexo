package com.girmiti.nexo.acquirer.dao.impl;

import static org.mockito.Mockito.spy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.dao.repository.TransactionRepository;
import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionReportResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionDaoImplTest {

	@InjectMocks
	TransactionDaoImpl transactionDaoImpl;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	private EntityManagerFactory entityManagerFactory;

	@Mock
	EntityManager entityManager;

	@Mock
	GetTranstionsRequest getTranstionsRequest;

	@Mock
	NexoTxn nexoTxn;

	@Mock
	TransactionReportResponse transactionReportResponse;

	@Mock
	javax.persistence.Query query;

	private static final Integer PAGE_SIZE = 10;

	private static final Integer PAGE_INDEX = 1;
	
	private static final Integer COUNT = 10;
	
	private static final String FROM_DATE = "2019/02/02";

	private static final String TO_DATE = "2019/12/02";

	private static final Long NEXO_TXN_ID = 123l;
	
	private static final String REQUEST_TYPE = "AUTH";
	
	private static final String MSG_FUNC = "AUTH" ;

	private static final String ACQRR_ID = "a0";
	
	private static final String INITG_PTY_ID = "initgPtyId";
	
	private static final String MRCHNT_ID = "m11";
	
	private static final String TX_TX_REF = "txTxRef";

	private static final String REQUEST_DATA = "request";

	private static final String RESPONSE_DATA = "response";

	private static final String TX_RSPN = "res";
	
	private static final String PG_TXN_REF = "86412";
	
	private static final String CAPTURE_STATUS = "true";
	
	private static final String CG_TXN_REF = "cg789";
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testsaveTransactionRequest() {
		nexoTxn = new NexoTxn();
		Mockito.when(transactionRepository.save(ArgumentMatchers.any(NexoTxn.class))).thenReturn(new NexoTxn());
		nexoTxn = transactionDaoImpl.saveTransactionRequest(nexoTxn);
		Assert.assertNotNull(nexoTxn);
	}

	@Test
	public void testfindByPgTxRef() {
		nexoTxn.setPgTxnRef(ArgumentMatchers.anyString());
		transactionDaoImpl.findByPgTxRef(nexoTxn);
		Assert.assertNotNull(transactionDaoImpl);
	}

	@Test
	public void testupdateTransactionResponse() {
		nexoTxn.setNexoTxnId(ArgumentMatchers.anyLong());
		nexoTxn = transactionDaoImpl.updateTransactionResponse(nexoTxn);
		Assert.assertNotNull(nexoTxn);
	}

	@Test
	public void testgetTransactionsResponse() throws Exception {
		Long a = 45l;
		GetTranstionsRequest getTranstionsRequest = new GetTranstionsRequest();
		getTranstionsRequest.setPageIndex(PAGE_INDEX);
		getTranstionsRequest.setPageSize(PAGE_SIZE);
		getTranstionsRequest.setFromDate(FROM_DATE);
		getTranstionsRequest.setToDate(TO_DATE);
		List<GetTranstionsRequest> transactions = new ArrayList<>();
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[9];
		objects[0] = new Long(a);
		objects[1] = new String("girmiti");
		objects[2] = new String("message function");
		objects[3] = new Timestamp(System.currentTimeMillis());
		objects[4] = new String("initgPtyId");
		objects[5] = new String("acqrrId");
		objects[6] = new String("mrchntId");
		objects[7] = new String("txTXRef");
		objects[8] = new String("txRspn");
		tuplelist.add(objects);
		transactions.add(getTranstionsRequest);
		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(ArgumentMatchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(entityManagerFactory);
		Mockito.when(query.getResultList()).thenReturn(tuplelist);
		Mockito.when(query.getSingleResult()).thenReturn(Long.valueOf(COUNT));
		transactionReportResponse = transactionDaoImpl.getTransactions(getTranstionsRequest);
		Assert.assertNotNull(transactionReportResponse);
	}
	
}
