package com.girmiti.nexo.processor.impl;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptordiagnosticrequest.AcceptorDiagnosticRequest7;
import com.girmiti.nexo.acceptordiagnosticrequest.Acquirer4;
import com.girmiti.nexo.acceptordiagnosticrequest.CardPaymentEnvironment71;
import com.girmiti.nexo.acceptordiagnosticresponse.AcceptorDiagnosticResponse5;
import com.girmiti.nexo.acceptordiagnosticresponse.AcceptorDiagnosticResponseV06;
import com.girmiti.nexo.acceptordiagnosticresponse.TMSContactLevel1Code;
import com.girmiti.nexo.acceptordiagnosticresponse.TMSTrigger1;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.exception.RejectionException;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionResponse;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;

public class AcceptorDiagnosticProcessor implements ITransactionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptorDiagnosticProcessor.class);
	
	public String process(String acceptorDiagnosticReqXmlRecr) throws Exception {
		com.girmiti.nexo.acceptordiagnosticrequest.Document accDiagnosticReqDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			if (!acceptorDiagnosticReqXmlRecr.contains("xsi:type") || !acceptorDiagnosticReqXmlRecr.contains("xmlns")
					|| !acceptorDiagnosticReqXmlRecr.contains(" xmlns:xsi")) {
				acceptorDiagnosticReqXmlRecr = acceptorDiagnosticReqXmlRecr.replace("<Document>",
						"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.013.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			}
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			accDiagnosticReqDocument = (com.girmiti.nexo.acceptordiagnosticrequest.Document) jaxbHelper.unmarshall(
					com.girmiti.nexo.acceptordiagnosticrequest.Document.class, acceptorDiagnosticReqXmlRecr,
					Constants.CAAA_013_001_07);

		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(), acceptorDiagnosticReqXmlRecr);
		}

		return processAuth(accDiagnosticReqDocument, acceptorDiagnosticReqXmlRecr);
	}

	private static String processAuth(com.girmiti.nexo.acceptordiagnosticrequest.Document accDiagnosticReqDocument,
			String acceptorDiagnosticReqXmlRecr) throws JAXBException, XMLStreamException, FactoryConfigurationError,
			javax.xml.parsers.FactoryConfigurationError, RejectionException {
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		XMLGregorianCalendar txtDate = accDiagnosticReqDocument.getAccptrDgnstcReq().getHdr().getCreDtTm();
		String protocolvrsn = accDiagnosticReqDocument.getAccptrDgnstcReq().getHdr().getPrtcolVrsn();
		rejectionMessageRequest.setTxnDate(txtDate);
		rejectionMessageRequest.setProtocolVersion(protocolvrsn);
		DaignosticTransactionResponse transactionResponse = null;
		if (Constants.PG_MOCK != null && Constants.PG_MOCK.equals("false")) {
			DaignosticTransactionRequest transactionRequest = new DaignosticTransactionRequest();
			transactionRequest.setMac(accDiagnosticReqDocument.getAccptrDgnstcReq().getSctyTrlr() != null
					? accDiagnosticReqDocument.getAccptrDgnstcReq().getSctyTrlr().getAuthntcdData().getMAC()
					: null);
			transactionResponse = JsonUtil.postServiceRequest(transactionRequest, DaignosticTransactionResponse.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"getDiagnosticStatus");
			
			if (transactionResponse.getRejectionCode() != null) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						transactionResponse.getRejectionCode(), transactionResponse.getRejectionReason(), acceptorDiagnosticReqXmlRecr);
			}
		}
		
		AcceptorDiagnosticRequest7 request = accDiagnosticReqDocument.getAccptrDgnstcReq().getDgnstcReq();
		CardPaymentEnvironment71 requestEnv = request.getEnvt();

		// -------------- PARSING REQUEST ---------------//
		
		String reqParamVrsn = requestEnv.getAcqrr().getParamsVrsn();
		Acquirer4 requestAcq = requestEnv.getAcqrr();
		String reqAcqID = getAcquirerId(requestAcq);
		
		com.girmiti.nexo.acceptordiagnosticrequest.GenericIdentification32 requestPoi = requestEnv.getPOIId();
		
		
		int milliSecond = txtDate.getMillisecond();
		if(milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorDiagnosticReqXmlRecr);
		}
		// validating Message Function
		if (! accDiagnosticReqDocument.getAccptrDgnstcReq().getHdr().getMsgFctn().toString()
				.equals(com.girmiti.nexo.acceptordiagnosticresponse.MessageFunction14Code.DGNP.toString())) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,acceptorDiagnosticReqXmlRecr);
		}
		
		// ------------ FORMATTING RESPONSE ------------//
		com.girmiti.nexo.acceptordiagnosticresponse.Document respDocument = new com.girmiti.nexo.acceptordiagnosticresponse.Document();
		AcceptorDiagnosticResponseV06 response = new AcceptorDiagnosticResponseV06();
		AcceptorDiagnosticResponse5 acceptorDiagnosticResponse5 = new AcceptorDiagnosticResponse5();
		
		// Set header
		com.girmiti.nexo.acceptordiagnosticrequest.Header35 reqHeader = accDiagnosticReqDocument.getAccptrDgnstcReq().getHdr();
		com.girmiti.nexo.acceptordiagnosticresponse.Header35 resHeader = new com.girmiti.nexo.acceptordiagnosticresponse.Header35();
		resHeader.setMsgFctn(com.girmiti.nexo.acceptordiagnosticresponse.MessageFunction14Code.DGNQ);
		resHeader.setPrtcolVrsn(reqHeader.getPrtcolVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		com.girmiti.nexo.acceptordiagnosticresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptordiagnosticresponse.GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		resHeader.setInitgPty(initigParty);
		
		// Set DiagnosticResponse - Envt
		com.girmiti.nexo.acceptordiagnosticresponse.CardPaymentEnvironment71 responseEnv = new com.girmiti.nexo.acceptordiagnosticresponse.CardPaymentEnvironment71();
		com.girmiti.nexo.acceptordiagnosticresponse.GenericIdentification32 responsePOIId = new com.girmiti.nexo.acceptordiagnosticresponse.GenericIdentification32();
		com.girmiti.nexo.acceptordiagnosticresponse.Acquirer4 responseAcq = new com.girmiti.nexo.acceptordiagnosticresponse.Acquirer4();
		com.girmiti.nexo.acceptordiagnosticresponse.GenericIdentification53 responseidentification = new com.girmiti.nexo.acceptordiagnosticresponse.GenericIdentification53();
		
		responsePOIId.setId(requestPoi.getId());
		
		responseidentification.setId(reqAcqID);
		responseAcq.setId(responseidentification);
		responseAcq.setParamsVrsn(reqParamVrsn);
		if (transactionResponse != null) {
			responseEnv.setAcqrrAvlbl(transactionResponse.getStatus());
		} else {
			responseEnv.setAcqrrAvlbl(false);
		}
		if (Constants.PG_MOCK != null && Constants.PG_MOCK.equals("true")) {
			responseEnv.setAcqrrAvlbl(true);
		}
		responseEnv.setAcqrr(responseAcq);
		responseEnv.setPOIId(responsePOIId);
		acceptorDiagnosticResponse5.setEnvt(responseEnv);
		TMSTrigger1 tmsTrigger1 = new TMSTrigger1();
		if (transactionResponse != null && transactionResponse.getTmsTrigger() != null
				&& transactionResponse.getTmsTrigger().equals(true)) {
			tmsTrigger1.setTMSCtctLvl(TMSContactLevel1Code.ASAP);
			acceptorDiagnosticResponse5.setTMSTrggr(tmsTrigger1);
		}
		
		response.setDgnstcRspn(acceptorDiagnosticResponse5);
		
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorDiagnostic");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setAcqrrId(reqAcqID);
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setMsgFctn(String.valueOf(
					com.girmiti.nexo.acceptordiagnosticresponse.MessageFunction14Code.valueOf(reqHeader.getMsgFctn().toString())));
			nexoTxn.setRequestData(acceptorDiagnosticReqXmlRecr);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorDiagnostic Requset", e);
		}
		
		response.setHdr(resHeader);
		respDocument.setAccptrDgnstcRspn(response);
		String responseXml = JaxbHelper.marshall(respDocument,response);
		if(nexoTxn != null) {
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;
	}

	private static String getAcquirerId(Acquirer4 requestAcq) {
		if(!ObjectUtils.isEmpty(requestAcq) && !ObjectUtils.isEmpty(requestAcq.getId())) {
			return requestAcq.getId().getId();
		}
		return null;
	}

}

