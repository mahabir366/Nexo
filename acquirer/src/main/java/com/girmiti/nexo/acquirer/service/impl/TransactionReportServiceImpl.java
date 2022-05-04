package com.girmiti.nexo.acquirer.service.impl;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.girmiti.nexo.acquirer.dao.TransactionDao;
import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.Response;
import com.girmiti.nexo.acquirer.pojo.TransactionReportResponse;
import com.girmiti.nexo.acquirer.service.TransactionsReportService;
import com.girmiti.nexo.util.Constants;

@Service
public class TransactionReportServiceImpl implements TransactionsReportService {
	private static Logger logger = LoggerFactory.getLogger(TransactionReportServiceImpl.class);

	@Autowired
	TransactionDao transtionDao;

	@Override
	public Response getTransactions(GetTranstionsRequest request) {
		logger.info("STRART :: TransactionServiceImpl :: getTransactions method");
		TransactionReportResponse response = new TransactionReportResponse();
		try {
			response = transtionDao.getTransactions(request);
			response.setErrorCode(Constants.ZERO);
			response.setErrorMessage(Constants.SUCCESS);
			logger.info("End :: TransactionServiceImpl :: getTransactions method");
		} catch (Exception e) {
			logger.error("TransactionServiceImpl class :: getTransactions method :: exception", e);
		}
		return response;
	}

}
