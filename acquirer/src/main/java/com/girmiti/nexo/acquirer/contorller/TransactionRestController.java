package com.girmiti.nexo.acquirer.contorller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceRequest;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceResponse;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.DccRequest;
import com.girmiti.nexo.acquirer.pojo.DccResponse;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataRequest;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataResponse;
import com.girmiti.nexo.acquirer.pojo.ReconciliationRequest;
import com.girmiti.nexo.acquirer.pojo.ReconciliationResponse;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionResponse;
import com.girmiti.nexo.acquirer.service.TransactionService;
import com.girmiti.nexo.processor.TransactionHandler;
import com.girmiti.nexo.util.Constants;

@RestController
@Component
@RequestMapping("/acquirerService/transaction")
public class TransactionRestController implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRestController.class);

	@Value("${transaction.service.qualifier}")
	private String qualifierName;

	@Autowired
	private static ApplicationContext context;

	@Autowired
	private TransactionService transactionService;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		context = appContext;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	public TransactionService getTransactionServiceBean() {
		ApplicationContext applicationContext = getApplicationContext();
		return (TransactionService) applicationContext.getBean(qualifierName);
	}

	@PostMapping(path = "/1/0" + "/saveTransactionRequest", consumes = "application/json")
	public NexoTxn saveRequest(@RequestBody @Validated NexoTxn nexoTxn, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		NexoTxn response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.saveTransactionRequest(nexoTxn);
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionRestController :: method saveRequest unable to process your request ", e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/updateTransactionResponse", consumes = "application/json")
	public NexoTxn updateResponse(@RequestBody @Validated NexoTxn nexoTxn, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		NexoTxn response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.updateTransactionResponse(nexoTxn);
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionRestController :: method updateResponse unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/getDiagnosticStatus", consumes = "application/json")
	public DaignosticTransactionResponse getDiagnosticStatus(
			@RequestBody @Validated DaignosticTransactionRequest transactionRequest, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		DaignosticTransactionResponse response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.getDiagnosticStatus(transactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method getDiagnosticStatus unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/getCancellationAdviceStatus", consumes = "application/json")
	public CancellationAdviceResponse getCancellationAdviceStatus(
			@RequestBody @Validated CancellationAdviceRequest transactionRequest, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		CancellationAdviceResponse response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.getCancellationAdviceStatus(transactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method getDiagnosticStatus unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/processTransactionBasedOnStatus", consumes = "application/json")
	public TransactionResponse processTransactionBasedOnStatus(
			@RequestBody @Validated TransactionRequest transactionRequest, HttpServletRequest request,
			BindingResult bindingResult) {
		LOGGER.debug("Entering :: TransactionRestController :: processTransactionBasedOnStatus method");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			transactionService = getTransactionServiceBean();
			transactionResponse = transactionService.processTransactionBasedOnStatus(transactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method processTransactionBasedOnStatus unable to process your request",
					e);
		}
		LOGGER.debug("Exiting :: TransactionRestController :: processTransactionBasedOnStatus method");
		return transactionResponse;
	}

	@PostMapping(path = "/1/0" + "/processSaleTransaction", consumes = "application/json")
	public TransactionResponse processSaleTransaction(@RequestBody @Validated TransactionRequest transactionRequest,
			BindingResult bindingResult, HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		TransactionResponse response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.processSaleTransaction(transactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method processSaleTransaction unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/processAuthTransaction", consumes = "application/json")
	public TransactionResponse processAuthTransaction(@RequestBody @Validated TransactionRequest transactionRequest,
			BindingResult bindingResult, HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		TransactionResponse response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.processAuthTransaction(transactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method processAuthTransaction unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/processCaptureTransaction", consumes = "application/json")
	public TransactionResponse processCaptureTransaction(@RequestBody @Validated TransactionRequest transactionRequest,
			BindingResult bindingResult, HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		TransactionResponse response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.processCaptureTransaction(transactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method processAuthTransaction unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/findByPgTxRef", consumes = "application/json")
	public NexoTxn findByPgTxRef(@RequestBody @Validated NexoTxn nexoTxn, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		NexoTxn response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.findByPgTxRef(nexoTxn);
		} catch (Exception e) {
			LOGGER.error("Error :: TransactionRestController :: method findByPgTxRef unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/batchProcessTransaction", consumes = "application/json")
	public BulkTransactionResponse batchProcessTransaction(
			@RequestBody @Validated BulkTransactionRequest bulkTransactionRequest, HttpServletRequest request,
			HttpServletResponse response) {
		BulkTransactionResponse bulkTransactionResponse = new BulkTransactionResponse();
		try {
			transactionService = getTransactionServiceBean();
			bulkTransactionResponse = transactionService.batchProcessTransaction(bulkTransactionRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method batchProcessTransaction unable to process your request",
					e);
		}
		return bulkTransactionResponse;
	}

	@PostMapping(path = "/1/0" + "/processReconcialtionTransaction", consumes = "application/json")
	public ReconciliationResponse processReconcialtionTransaction(
			@RequestBody @Validated ReconciliationRequest reconciliationRequest, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		ReconciliationResponse response = null;
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.processReconcialtionTransaction(reconciliationRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method processReconcialtionTransaction unable to process your request ",
					e);
		}
		return response;
	}

	@PostMapping(path = "/1/0" + "/getCurrencyConversionRate", consumes = "application/json")
	public DccResponse getCurrencyConversionRate(@RequestBody @Validated DccRequest dccRequest,
			BindingResult bindingResult, HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		DccResponse dccResponse = null;
		try {
			transactionService = getTransactionServiceBean();
			dccResponse = transactionService.getCurrencyConversionRate(dccRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method getCurrencyConversionRate unable to process your request ",
					e);
		}
		return dccResponse;
	}

	@PostMapping(path = "/1/0" + "/updateCurrencyConversionTransaction", consumes = "application/json")
	public DccResponse updateCurrencyConversionTransaction(@RequestBody @Validated DccRequest dccRequest,
			BindingResult bindingResult, HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		DccResponse dccResponse = null;
		try {
			transactionService = getTransactionServiceBean();
			dccResponse = transactionService.updateCurrencyConversionTransaction(dccRequest);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method updateCurrencyConversionTransaction unable to process your request ",
					e);
		}
		return dccResponse;
	}

	@PostMapping(path = Constants.VERSION + Constants.FIND_BY_CAPTURE_STATUS , consumes = Constants.CONSUMES)
	public NexoAcquirerDataResponse findByCaptureStatus(
			@RequestBody @Validated NexoAcquirerDataRequest acquirerDataRequest, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		List<NexoTxn> response = null;
		NexoAcquirerDataResponse acquirerDataResponse = new NexoAcquirerDataResponse();
		try {
			transactionService = getTransactionServiceBean();
			response = transactionService.findByCaptureStatus(acquirerDataRequest.getCaptureStatus());
			acquirerDataResponse.setNexoTxns(response);
		} catch (Exception e) {
			LOGGER.error(
					"Error :: TransactionRestController :: method findByCaptureStatus unable to process your request ",
					e);
		}
		return acquirerDataResponse;
	}
	
	@PostMapping(path = "/1/0" + "/process",consumes = "application/xml")
	public String processTxRequestXml(@RequestBody String nexoAcquirerData, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		String responseXml = null;
		try {
			LOGGER.info("Entering :: NexoAcquirerRestController :: method processRequest");
			byte[] result = nexoAcquirerData.getBytes();
			 responseXml = TransactionHandler.process(result);
		} catch (Exception e) {
			LOGGER.error("Error :: NexoAcquirerRestController :: method processRequest unable to process your request ", e);
		}
		LOGGER.info("Exiting :: NexoAcquirerRestController :: method processRequest");
		return responseXml;
	}
}
