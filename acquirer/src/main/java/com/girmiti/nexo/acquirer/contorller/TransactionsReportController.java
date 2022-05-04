package com.girmiti.nexo.acquirer.contorller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.Response;
import com.girmiti.nexo.acquirer.service.TransactionsReportService;
import io.swagger.annotations.ApiOperation;

@RestController
@Component
@RequestMapping("/acquirerService/transaction")
public class TransactionsReportController {

	private static Logger logger = LoggerFactory.getLogger(TransactionsReportController.class);

	@Autowired(required = true)
	TransactionsReportService transactionService;

	@ApiOperation(value = "Get Transaction Details", notes = "Get Transaction Details")
	@PostMapping(path = "/1/0" + "/getTransactionDetails", consumes = "application/json")
	public Response getTransactions(@RequestBody @Validated GetTranstionsRequest request, BindingResult bindingResult,
			HttpServletRequest requestToCache, HttpServletResponse httpResponse) {
		logger.info("START :: TRansactionsReportController :: createUser method");
		Response response = null;
		response = transactionService.getTransactions(request);
		logger.info("End :: TRansactionsReportController :: getTransactions method");
		return response;
	}

}
