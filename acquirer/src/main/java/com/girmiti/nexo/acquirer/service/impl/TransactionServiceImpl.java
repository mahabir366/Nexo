package com.girmiti.nexo.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.girmiti.nexo.acquirer.dao.TransactionDao;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
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
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.HttpClient;
import com.girmiti.nexo.util.MaskingData;

@Service("transactionService")
@Qualifier("transactionService")
@Primary
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired(required = true)
	TransactionDao transactionDao;

	@Value("${transaction.service.daignostic}")
	private String pgServiceUrl;
	
	@Value("${transaction.process.service}")
	private String process;
	
	@Value("${transaction.reconciliation.process.service}")
	private String reconciliationProcess;

	public NexoTxn saveTransactionRequest(NexoTxn nexoTxn) {
		String requestData = nexoTxn.getRequestData();
		String maskedData = null;
		try {
			maskedData = MaskingData.getMaskedData(requestData);
		} catch (Exception e) {
			LOGGER.info("Error in Masking the Request");
		}
		nexoTxn.setRequestData(maskedData);
		return transactionDao.saveTransactionRequest(nexoTxn);
	}

	public NexoTxn updateTransactionResponse(NexoTxn nexoTxn) {
		String responseData = nexoTxn.getResponseData();
		String maskedData = null;
		try {
			maskedData = MaskingData.getMaskedData(responseData);
		} catch (Exception e) {
			LOGGER.info("Error in Masking the Reponse");
		}
		nexoTxn.setResponseData(maskedData);
		return transactionDao.updateTransactionResponse(nexoTxn);
	}

	public DaignosticTransactionResponse getDiagnosticStatus(DaignosticTransactionRequest transactionRequest) {
		DaignosticTransactionResponse transactionResponse = new DaignosticTransactionResponse();
		try {
			transactionResponse = HttpClient.postRequest(transactionRequest, DaignosticTransactionResponse.class,
					pgServiceUrl + "heartbeat");
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionService :: method getDiagnosticStatus unable to process your request ",
					e);
		}
		return transactionResponse;
	}
	
	public CancellationAdviceResponse getCancellationAdviceStatus(CancellationAdviceRequest transactionRequest) {
		CancellationAdviceResponse transactionResponse = new CancellationAdviceResponse();
		try {
			transactionResponse = HttpClient.postRequest(transactionRequest, CancellationAdviceResponse.class,
					pgServiceUrl + "trasactionStatusCheck");
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionService :: method getDiagnosticStatus unable to process your request ",
					e);
		}
		return transactionResponse;
	}
	
	public TransactionResponse processTransactionBasedOnStatus(TransactionRequest transactionRequest) {
		LOGGER.debug("Entering :: TransactionService :: processTransactionBasedOnStatus method");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			transactionResponse = HttpClient.postRequest(transactionRequest, TransactionResponse.class, "/transaction/process");
		} catch(Exception e) {
			LOGGER.error("Error :: TransactionService :: method processTransactionBasedOnStatus unable to process your request", e);
		}
		LOGGER.debug("Exiting :: TransactionService :: processTransactionBasedOnStatus method");
		return transactionResponse;
	}
	
	public NexoTxn findByPgTxRef(NexoTxn nexoTxn) {
		return transactionDao.findByPgTxRef(nexoTxn);
	}

	public TransactionResponse processSaleTransaction(TransactionRequest transactionRequest) {
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			transactionResponse = HttpClient.postRequest(transactionRequest, TransactionResponse.class,
					pgServiceUrl + Constants.PROCESS );
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionService :: method processSaleTransaction unable to process your request ",
					e);
		}
		return transactionResponse;
	}

	public TransactionResponse processAuthTransaction(TransactionRequest transactionRequest) {
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			transactionResponse = HttpClient.postRequest(transactionRequest, TransactionResponse.class,
					pgServiceUrl + Constants.PROCESS );
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionService :: method processAuthTransaction unable to process your request ",
					e);
		}
		return transactionResponse;
	}

	public TransactionResponse processCaptureTransaction(TransactionRequest transactionRequest) {
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			transactionResponse = HttpClient.postRequest(transactionRequest, TransactionResponse.class,
					pgServiceUrl + Constants.PROCESS );
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionService :: method processCaptureTransaction unable to process your request ",
					e);
		}
		return transactionResponse;
	}

	public ReconciliationResponse processReconcialtionTransaction(ReconciliationRequest reconciliationRequest) {
		ReconciliationResponse reconciliationResponse = new ReconciliationResponse();
		try {
			reconciliationResponse = HttpClient.postRequest(reconciliationRequest, ReconciliationResponse.class,
					pgServiceUrl + "reconciliationProcess");
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionService :: method processReconcialtionTransaction unable to process your request ",
					e);
		}
		return reconciliationResponse;
	}

	public BulkTransactionResponse batchProcessTransaction(BulkTransactionRequest bulkTransactionRequest) {
		BulkTransactionResponse bulkTransactionResponse = new BulkTransactionResponse();
		try {
			bulkTransactionResponse = HttpClient.postRequest(bulkTransactionRequest, BulkTransactionResponse.class, "/transaction/bulkProcess");
		} catch(Exception e) {
			LOGGER.error("Error :: TransactionService :: method batchProcessTransaction unable to process your request", e);
		}
		return bulkTransactionResponse;
	}

	public DccResponse getCurrencyConversionRate(DccRequest dccRequest) {
		DccResponse dccResponse = new DccResponse();
		try {
			dccResponse = HttpClient.postRequest(dccRequest, DccResponse.class, "/transaction/getCurrencyConversionRate");
		}catch(Exception e) {
			LOGGER.error("Error :: TransactionService :: method getCurrencyConversionRate unable to process your request", e);
		}
		return dccResponse;
	}

	public DccResponse updateCurrencyConversionTransaction(DccRequest dccRequest) {
		DccResponse dccResponse = new DccResponse();
		try {
			dccResponse = HttpClient.postRequest(dccRequest, DccResponse.class, "/transaction/updateCurrencyConversionTransaction");
		}catch(Exception e) {
			LOGGER.error("Error :: TransactionService :: method updateCurrencyConversionTransaction unable to process your request", e);
		}
		return dccResponse;
	}

	@Override
	public List<NexoTxn> findByCaptureStatus(String captrueStatus) {
		return transactionDao.findByCaptureStatus(captrueStatus);
	}

}
