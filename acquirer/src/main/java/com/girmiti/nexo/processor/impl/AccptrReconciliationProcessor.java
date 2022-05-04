package com.girmiti.nexo.processor.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorreconciliationreqest.AcceptorReconciliationRequest7;
import com.girmiti.nexo.acceptorreconciliationreqest.Document;
import com.girmiti.nexo.acceptorreconciliationreqest.Header35;
import com.girmiti.nexo.acceptorreconciliationreqest.MessageFunction14Code;
import com.girmiti.nexo.acceptorreconciliationreqest.TransactionTotals7;
import com.girmiti.nexo.acceptorreconciliationresponse.AcceptorReconciliationResponse5;
import com.girmiti.nexo.acceptorreconciliationresponse.AcceptorReconciliationResponseV06;
import com.girmiti.nexo.acceptorreconciliationresponse.CardPaymentEnvironment69;
import com.girmiti.nexo.acceptorreconciliationresponse.GenericIdentification32;
import com.girmiti.nexo.acceptorreconciliationresponse.PartyType3Code;
import com.girmiti.nexo.acceptorreconciliationresponse.Response4Code;
import com.girmiti.nexo.acceptorreconciliationresponse.ResponseType5;
import com.girmiti.nexo.acceptorreconciliationresponse.TransactionIdentifier1;
import com.girmiti.nexo.acceptorreconciliationresponse.TransactionReconciliation4;
import com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.pojo.PaymentType;
import com.girmiti.nexo.acquirer.pojo.ReconciliationRequest;
import com.girmiti.nexo.acquirer.pojo.ReconciliationResponse;
import com.girmiti.nexo.acquirer.service.impl.TransactionSimulatorServiceImpl;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.CurrencyValidationUtil;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;

public class AccptrReconciliationProcessor implements ITransactionProcessor{

	@Autowired
	TransactionSimulatorServiceImpl transactionSimulatorServiceImpl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccptrReconciliationProcessor.class);
	
	public String process(String reqXml) throws Exception {
		com.girmiti.nexo.acceptorreconciliationreqest.Document reqDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			reqDocument = (com.girmiti.nexo.acceptorreconciliationreqest.Document)jaxbHelper.unmarshall(com.girmiti.nexo.acceptorreconciliationreqest.Document.class,reqXml,Constants.CAAA_009_001_07); 

		} catch(Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),reqXml);
		}
		return processCurrencyConversion(reqDocument ,reqXml);
	}

	private String processCurrencyConversion(Document reqDocument, String reqXml) throws JAXBException, XMLStreamException,
	FactoryConfigurationError, javax.xml.parsers.FactoryConfigurationError {
		AcceptorReconciliationRequest7 acceptorReconciliationRequest7 = reqDocument.getAccptrRcncltnReq().getRcncltnReq();
		Header35 header = reqDocument.getAccptrRcncltnReq().getHdr();
		MessageFunction14Code messageFunction = header.getMsgFctn();
		String initiatingParty = header.getInitgPty().getId();
		
		XMLGregorianCalendar txtDate = acceptorReconciliationRequest7.getTx().getRcncltnTxId().getTxDtTm();
        String txtRefId = acceptorReconciliationRequest7.getTx().getRcncltnTxId().getTxRef();
        String reconciliationId = acceptorReconciliationRequest7.getTx().getRcncltnId(); 
        
        RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		rejectionMessageRequest.setTxnDate(txtDate);
		
		int milliSecond = txtDate.getMillisecond();
		if (milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,reqXml);

		}
		
		// validating Message Function
		if (! reqDocument.getAccptrRcncltnReq().getHdr().getMsgFctn().toString()
				.equals(com.girmiti.nexo.acceptorreconciliationresponse.MessageFunction14Code.RCLQ.toString())) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,reqXml);
		}
		
		if (!ObjectUtils.isEmpty(acceptorReconciliationRequest7.getTx())
				&& !ObjectUtils.isEmpty(acceptorReconciliationRequest7.getTx().getTxTtls())
				&& CurrencyValidationUtil
						.isCurrencyNotNull(acceptorReconciliationRequest7.getTx().getTxTtls().get(0).getCcy())
				&& CurrencyValidationUtil
						.validateCurrency(acceptorReconciliationRequest7.getTx().getTxTtls().get(0).getCcy())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.CURRENCY_ERROR,reqXml);
		}

		List<PaymentType> paymentTypes = getTransactions(acceptorReconciliationRequest7);
		
		com.girmiti.nexo.acceptorreconciliationresponse.Document responseDoc = new com.girmiti.nexo.acceptorreconciliationresponse.Document();
		AcceptorReconciliationResponseV06 acceptorReconciliationResponseV06 = new AcceptorReconciliationResponseV06();
		com.girmiti.nexo.acceptorreconciliationresponse.Header35 resHeader = new com.girmiti.nexo.acceptorreconciliationresponse.Header35();
		resHeader.setMsgFctn(com.girmiti.nexo.acceptorreconciliationresponse.MessageFunction14Code.RCLP);		
		resHeader.setPrtcolVrsn(header.getPrtcolVrsn());
		resHeader.setXchgId(header.getXchgId());
		resHeader.setCreDtTm(header.getCreDtTm());
		
		int milliSeconds = header.getCreDtTm().getMillisecond();
		if (milliSeconds < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,reqXml);
		}		
		com.girmiti.nexo.acceptorreconciliationresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorreconciliationresponse.GenericIdentification53();
		initigParty.setId(initiatingParty);
		resHeader.setInitgPty(initigParty);
		acceptorReconciliationResponseV06.setHdr(resHeader);
		
		AcceptorReconciliationResponse5 acceptorReconciliationResponse5 = new AcceptorReconciliationResponse5(); 
		CardPaymentEnvironment69 cardPaymentEnvironment69 = new CardPaymentEnvironment69();
		GenericIdentification32 genericIdentification32 = new GenericIdentification32();
		genericIdentification32.setId("POI4567889");
		genericIdentification32.setTp(PartyType3Code.ACCP);
		cardPaymentEnvironment69.setPOIId(genericIdentification32);
		acceptorReconciliationResponse5.setEnvt(cardPaymentEnvironment69);
		
		TransactionReconciliation4 transactionReconciliation4 =new TransactionReconciliation4();
		TransactionIdentifier1 transactionIdentifier1 = new TransactionIdentifier1();
		transactionIdentifier1.setTxDtTm(txtDate);
		transactionIdentifier1.setTxRef(txtRefId);
		transactionReconciliation4.setRcncltnId(reconciliationId);
		transactionReconciliation4.setRcncltnTxId(transactionIdentifier1);
		ResponseType5 responseType5 = new ResponseType5();
		processReconcialtionTransaction(transactionReconciliation4, acceptorReconciliationRequest7, paymentTypes, responseType5, reconciliationId);
		acceptorReconciliationResponse5.setTx(transactionReconciliation4);
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorReconciliation");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setTxTxRef(txtRefId);
			nexoTxn.setMsgFctn(String.valueOf(
					com.girmiti.nexo.acceptorcurrencyconversionresponse.MessageFunction14Code.valueOf(messageFunction.value())));
			nexoTxn.setRequestData(reqXml);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorReconcilationion Requset", e);
		}
		
		acceptorReconciliationResponseV06.setHdr(resHeader);
		acceptorReconciliationResponse5.setTxRspn(responseType5);
		acceptorReconciliationResponseV06.setRcncltnRspn(acceptorReconciliationResponse5);
		responseDoc.setAccptrRcncltnRspn(acceptorReconciliationResponseV06);
		String responseXml = JaxbHelper.marshall(responseDoc,acceptorReconciliationResponseV06);
		if(nexoTxn != null) {
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;
	}

	private void processReconcialtionTransaction(TransactionReconciliation4 transactionReconciliation4,
			AcceptorReconciliationRequest7 acceptorReconciliationRequest7, List<PaymentType> paymentTypes,
			ResponseType5 responseType5, String reconciliationId) {
		if (Constants.PG_MOCK.equals("false")) {
			ReconciliationResponse reconciliationResponse = null;
			ReconciliationRequest reconciliationRequest = new ReconciliationRequest();
			reconciliationRequest.setPaymentType(paymentTypes);
			reconciliationRequest.setReconciliationId(reconciliationId);
			reconciliationRequest.setTransactionTotals7Req(acceptorReconciliationRequest7.getTx().getTxTtls());
			reconciliationResponse = JsonUtil.postServiceRequest(reconciliationRequest, ReconciliationResponse.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"processReconcialtionTransaction");
			if (!ObjectUtils.isEmpty(reconciliationResponse)
					&& !ObjectUtils.isEmpty(reconciliationResponse.getTxnStatus())
					&& reconciliationResponse.getTxnStatus().equals(Constants.APPROVED) || (!ObjectUtils.isEmpty(reconciliationResponse)
							&& !ObjectUtils.isEmpty(reconciliationResponse.getErrorMessage()))) {
				responseType5.setRspn(Response4Code.APPR);
				getReconciiationResponse(transactionReconciliation4, acceptorReconciliationRequest7, responseType5,
						reconciliationResponse);
			} 
			else {
				responseType5.setRspn(Response4Code.DECL);
			}
		} else {
			responseType5.setRspn(Response4Code.APPR);
		}
	}

	private void getReconciiationResponse(TransactionReconciliation4 transactionReconciliation4,
			AcceptorReconciliationRequest7 acceptorReconciliationRequest7, ResponseType5 responseType5,
			ReconciliationResponse reconciliationResponse) {
		if (!ObjectUtils.isEmpty(reconciliationResponse)
				&& !ObjectUtils.isEmpty(reconciliationResponse.getErrorCode())
				&& reconciliationResponse.getErrorCode().equals("00")) {
			transactionReconciliation4.getTxTtls()
					.addAll(getTotalTransactions(acceptorReconciliationRequest7));
		} else if (!ObjectUtils.isEmpty(reconciliationResponse)
				&& !ObjectUtils.isEmpty(reconciliationResponse.getErrorCode())
				&& reconciliationResponse.getErrorCode().equals("01")) {
			responseType5.setRspnRsn(reconciliationResponse.getErrorMessage());
		} else {
			transactionReconciliation4.getTxTtls()
					.addAll(getTotalTransactions(acceptorReconciliationRequest7));
		}
	}
	
	private List<com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7> getTotalTransactions(AcceptorReconciliationRequest7 acceptorReconciliationRequest7) {
		List<com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7> reconciliationRequests = new ArrayList<>();
		if (!ObjectUtils.isEmpty(acceptorReconciliationRequest7.getTx())
				&& !ObjectUtils.isEmpty(acceptorReconciliationRequest7.getTx().getTxTtls())) {
			List<TransactionTotals7> transactionTotals7s = acceptorReconciliationRequest7.getTx().getTxTtls();
			for (TransactionTotals7 transactionTotals7 : transactionTotals7s) {
				com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7 totals7 = new com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7();
				totals7.setCmltvAmt(transactionTotals7.getCmltvAmt());
				totals7.setTtlNb(transactionTotals7.getTtlNb());
				reconciliationRequests.add(totals7);
			}
		}
		return reconciliationRequests;
	}

	private List<PaymentType> getTransactions(AcceptorReconciliationRequest7 acceptorReconciliationRequest7) {
		List<PaymentType> reconciliationRequests = new ArrayList<>();
		if (!ObjectUtils.isEmpty(acceptorReconciliationRequest7.getTx())
				&& !ObjectUtils.isEmpty(acceptorReconciliationRequest7.getTx().getTxTtls())) {
			List<TransactionTotals7> transactionTotals7s = acceptorReconciliationRequest7.getTx().getTxTtls();
			for (TransactionTotals7 transactionTotals7 : transactionTotals7s) {
				PaymentType paymentType = new PaymentType();
				paymentType.setPaymentMethod(transactionTotals7.getTp().toString());
				if (transactionTotals7.getTp().toString().equals("DEBT")) {
					paymentType.setTxnTotalAmount(transactionTotals7.getCmltvAmt().multiply(BigDecimal.valueOf(100)));
				} else {
					paymentType.setTxnTotalAmount(transactionTotals7.getCmltvAmt());
				}
				paymentType.setTxnTotalCount(transactionTotals7.getTtlNb());
				reconciliationRequests.add(paymentType);
			}
		}
		return reconciliationRequests;
	}

}
