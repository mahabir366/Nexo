package com.girmiti.nexo.processor;

import java.nio.charset.StandardCharsets;

import com.girmiti.nexo.processor.impl.AcceptorBatchTransferProcessor;
import com.girmiti.nexo.processor.impl.AcceptorCancellationAdviceProcessor;
import com.girmiti.nexo.processor.impl.AcceptorCancellationProcessor;
import com.girmiti.nexo.processor.impl.AcceptorDiagnosticProcessor;
import com.girmiti.nexo.processor.impl.AccptrCurrencyConversionProcessor;
import com.girmiti.nexo.processor.impl.AccptrReconciliationProcessor;
import com.girmiti.nexo.processor.impl.AuthTransactionProcessor;
import com.girmiti.nexo.processor.impl.CompletionTransactionProcessor;
import com.girmiti.nexo.processor.impl.CurrencyConversionAdviceProcessor;

public class TransactionHandler {
	
	private TransactionHandler() {
		//Do nothing
	}

	public static String process(byte[] txnData) throws Exception {
		String requestXml = new String(txnData, StandardCharsets.UTF_8);
		String responseXml = null;
		ITransactionProcessor processor = getTransactionProcessor(requestXml);
		if (processor != null) {
			responseXml = processor.process(requestXml);
		}
		return responseXml;
	}

	private static ITransactionProcessor getTransactionProcessor(String xmlMsg) {
		if (xmlMsg.contains("AccptrAuthstnReq")) {
			return new AuthTransactionProcessor();
		} else if (xmlMsg.contains("AccptrCcyConvsReq")) {
			return new AccptrCurrencyConversionProcessor();
		} else if (xmlMsg.contains("AccptrCmpltnAdvc")) {
			return new CompletionTransactionProcessor();
		} else if (xmlMsg.contains("AccptrRcncltnReq")) {
			return new AccptrReconciliationProcessor();
		} else if (xmlMsg.contains("AccptrCcyConvsAdvc")) {
			return new CurrencyConversionAdviceProcessor();
		} else if (xmlMsg.contains("AccptrDgnstcReq")) {
			return new AcceptorDiagnosticProcessor();
		}  else if (xmlMsg.contains("AccptrCxlReq")) {
			return new AcceptorCancellationProcessor();
		} else if (xmlMsg.contains("AccptrBtchTrf")) {
			return new AcceptorBatchTransferProcessor();
		} else if (xmlMsg.contains("AccptrCxlAdvc")) {
			return new AcceptorCancellationAdviceProcessor();
		} else {
			return null;
		}
	}
}
