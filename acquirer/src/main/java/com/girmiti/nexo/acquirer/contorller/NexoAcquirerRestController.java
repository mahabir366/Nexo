package com.girmiti.nexo.acquirer.contorller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataRequest;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataResponse;
import com.girmiti.nexo.processor.TransactionHandler;

@RestController
@Component
@RequestMapping("/nexoAcquirer/transaction")
public class NexoAcquirerRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NexoAcquirerRestController.class);
	
	@PostMapping(path = "/1/0" + "/processTransactionRequest", consumes = "application/json")
	public NexoAcquirerDataResponse processTransactionRequest(@RequestBody NexoAcquirerDataRequest nexoAcquirerData, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		NexoAcquirerDataResponse nexoAcquirerDataResponse = new NexoAcquirerDataResponse();
		try {
			LOGGER.info("Entering :: NexoAcquirerRestController :: method processRequest");
			byte[] result = nexoAcquirerData.getRequest().getBytes();
			String responseXml = TransactionHandler.process(result);
			responseXml = responseXml.replaceAll("\n", "");
			responseXml = responseXml.replaceAll("xmlns:ns2", "xmlns");
			nexoAcquirerDataResponse.setResponse(responseXml);
		} catch (Exception e) {
			LOGGER.error("Error :: NexoAcquirerRestController :: method processRequest unable to process your request ", e);
		}
		LOGGER.info("Exiting :: NexoAcquirerRestController :: method processRequest");
		return nexoAcquirerDataResponse;
	}
}
