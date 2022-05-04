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

import com.girmiti.nexo.acceptorcurrencyconversionadvice.AcceptorCurrencyConversionAdvice3;
import com.girmiti.nexo.acceptorcurrencyconversionadvice.AcceptorCurrencyConversionAdviceV03;
import com.girmiti.nexo.acceptorcurrencyconversionadvice.CardPaymentEnvironment69;
import com.girmiti.nexo.acceptorcurrencyconversionadvice.CardPaymentTransaction88;
import com.girmiti.nexo.acceptorcurrencyconversionadvice.GenericIdentification32;
import com.girmiti.nexo.acceptorcurrencyconversionadvice.PlainCardData15;
import com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.AcceptorCancellationAdviceResponse7;
import com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.AcceptorCurrencyConversionAdviceResponseV02;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.pojo.DccRequest;
import com.girmiti.nexo.acquirer.pojo.DccResponse;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.CountryValidationUtil;
import com.girmiti.nexo.util.CurrencyValidationUtil;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;

public class CurrencyConversionAdviceProcessor implements ITransactionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionAdviceProcessor.class);
	
	public String process(String acceptorCurrConAdvReqXmlRecr) throws Exception {
		com.girmiti.nexo.acceptorcurrencyconversionadvice.Document currConAdvReqDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			currConAdvReqDocument = (com.girmiti.nexo.acceptorcurrencyconversionadvice.Document) jaxbHelper.unmarshall(
					com.girmiti.nexo.acceptorcurrencyconversionadvice.Document.class, acceptorCurrConAdvReqXmlRecr,
					Constants.CAAA_018_001_03);
		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),acceptorCurrConAdvReqXmlRecr);
		}
		return processCurrencyConversionAdvice(currConAdvReqDocument, acceptorCurrConAdvReqXmlRecr);
	}

	private static String processCurrencyConversionAdvice(
			com.girmiti.nexo.acceptorcurrencyconversionadvice.Document currConAdvReqDocument,
			String acceptorCurrConAdvReqXmlRecr) throws JAXBException, XMLStreamException, FactoryConfigurationError,
			javax.xml.parsers.FactoryConfigurationError {
		AcceptorCurrencyConversionAdviceV03 request = currConAdvReqDocument.getAccptrCcyConvsAdvc();
		AcceptorCurrencyConversionAdvice3 acceptorCurrencyConversionAdvice3 = request.getAccptrCcyConvsAdvc();
		CardPaymentEnvironment69 requestEnv = acceptorCurrencyConversionAdvice3.getEnvt();
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
		
		// POI data
		GenericIdentification32 requestPoi = requestEnv.getPOIId();
		PlainCardData15 plainCardData15 = getPlainCardData(requestEnv);
		String pan = null;
		String expiryDate = null;
		if(plainCardData15 != null) {
			pan = plainCardData15.getPAN();
			expiryDate = plainCardData15.getXpryDt();
		}
		
		// Card payment related info
		CardPaymentTransaction88 reqTransaction = acceptorCurrencyConversionAdvice3.getTx();
		XMLGregorianCalendar reqTxDtTm = reqTransaction.getTxId().getTxDtTm();
		String reqTxRef = reqTransaction.getTxId().getTxRef();
		String reqCurrency = reqTransaction.getTxDtls().getCcy();
		reqTransaction.getTxDtls().getTtlAmt();
		rejectionMessageRequest.setTxnDate(reqTxDtTm);
		
		int milliSecond = reqTxDtTm.getMillisecond();
		if (milliSecond < 0) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorCurrConAdvReqXmlRecr);

		}
		
		// ------------ FORMATTING RESPONSE ------------//
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.Document respDocument = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.Document();
		AcceptorCurrencyConversionAdviceResponseV02 response = new AcceptorCurrencyConversionAdviceResponseV02();
		AcceptorCancellationAdviceResponse7 acceptorCancellationAdviceResponse7 = new AcceptorCancellationAdviceResponse7();
		
		// Set header
		com.girmiti.nexo.acceptorcurrencyconversionadvice.Header36 reqHeader = currConAdvReqDocument.getAccptrCcyConvsAdvc().getHdr();
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.Header36 resHeader = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.Header36();
		resHeader.setMsgFctn(com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.MessageFunction14Code.DCRR);
		resHeader.setPrtcolVrsn(reqHeader.getPrtcolVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		
		int milliSeconds = reqHeader.getCreDtTm().getMillisecond();
		if (milliSeconds < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorCurrConAdvReqXmlRecr);
		}	
		
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		resHeader.setInitgPty(initigParty);
		
		// Set AuthorizationResponse - Envt
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.CardPaymentEnvironment69 responseEnv = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.CardPaymentEnvironment69();
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.GenericIdentification32 responsePOIId = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.GenericIdentification32();
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.PaymentCard28 cardData =new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.PaymentCard28();
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.PlainCardData15 plainCardData = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.PlainCardData15();
		plainCardData.setPAN(pan);
		plainCardData.setXpryDt(expiryDate);
		cardData.setPlainCardData(plainCardData);
		setPoi(requestPoi, responsePOIId);
		responseEnv.setPOIId(responsePOIId);
		responseEnv.setCard(cardData);
		if (ObjectUtils.isEmpty(requestEnv) && ObjectUtils.isEmpty(requestEnv.getCard())
				&& CountryValidationUtil.isCountryNotNull(requestEnv.getCard().getCardCtryCd()) && 
				CountryValidationUtil.validateCountry(requestEnv.getCard().getCardCtryCd())) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.COUNTRY_ERROR,acceptorCurrConAdvReqXmlRecr);
		}
		
		if (CurrencyValidationUtil.isCurrencyNotNull(reqCurrency) && CurrencyValidationUtil.validateCurrency(reqCurrency)) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CURRENCY_ERROR,acceptorCurrConAdvReqXmlRecr);
		}
		acceptorCancellationAdviceResponse7.setEnvt(responseEnv);
		
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorCurrencyConversionAdvice");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setTxTxRef(reqTxRef);
			nexoTxn.setMsgFctn(String.valueOf(reqHeader.getMsgFctn().toString()));
			nexoTxn.setRequestData(acceptorCurrConAdvReqXmlRecr);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorCurrencyConversionAdive Requset", e);
		}

		
		// Set AuthorizationResponse - Set Tx
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.CardPaymentTransactionAdviceResponse6 cardPaymentTxn = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.CardPaymentTransactionAdviceResponse6();
		
		com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.TransactionIdentifier1 transactionIdentifier = new com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.TransactionIdentifier1();
		transactionIdentifier.setTxDtTm(reqTxDtTm);
		transactionIdentifier.setTxRef(reqTxRef);
		cardPaymentTxn.setTxId(transactionIdentifier);
		DccRequest dccRequest = new DccRequest();
		dccRequest.setTxnRefNumber(reqTxRef);
		
		if(expiryDate != null && !expiryDate.matches(Constants.EXPIRY_DATE_REGEX)) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.EXPIRY_DATE_ERROR,acceptorCurrConAdvReqXmlRecr);
		}

		String dccResponse = getDccResponse(rejectionMessageRequest, cardPaymentTxn, dccRequest,acceptorCurrConAdvReqXmlRecr);
		if(dccResponse != Constants.SUCCESS_CODE) {
			return dccResponse;
		}
		acceptorCancellationAdviceResponse7.setTx(cardPaymentTxn);
		response.setCcyConvsAdvcRspn(acceptorCancellationAdviceResponse7);
		response.setHdr(resHeader);
		respDocument.setAccptrCcyConvsAdvcRspn(response);
		String responseXml = JaxbHelper.marshall(respDocument,response);
		JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
				"updateTransactionResponse");
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;
	}

	private static String getDccResponse(RejectionMessageRequest rejectionMessageRequest,
			com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.CardPaymentTransactionAdviceResponse6 cardPaymentTxn,
			DccRequest dccRequest,String acceptorCurrConAdvReqXmlRecr) throws javax.xml.parsers.FactoryConfigurationError, JAXBException,
			XMLStreamException, FactoryConfigurationError {
		if (Constants.PG_MOCK != null) {
			if (Constants.PG_MOCK.equals("false")) {
				DccResponse dccResponse = JsonUtil.postServiceRequest(dccRequest, DccResponse.class,
						Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
						"updateCurrencyConversionTransaction");
				if (dccResponse != null) {
					if (dccResponse.getErrorCode().equals("00")) {
						cardPaymentTxn.setRspn(com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.Response4Code.APPR);
					} else {
						return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
								RejectReason1Code.PARS, dccResponse.getErrorMessage(),acceptorCurrConAdvReqXmlRecr);
					}
				}
			} else {
				cardPaymentTxn.setRspn(com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.Response4Code.APPR);
			}
		}
		return Constants.SUCCESS_CODE;
	}

	private static PlainCardData15 getPlainCardData(CardPaymentEnvironment69 requestEnv) {
		PlainCardData15 plainCardData15 = null;
		if (ObjectUtils.isEmpty(requestEnv) && ObjectUtils.isEmpty(requestEnv.getCard())
				&& ObjectUtils.isEmpty(requestEnv.getCard().getPlainCardData())) {
			plainCardData15 = requestEnv.getCard().getPlainCardData();
		}
		return plainCardData15;
	}

	private static void setPoi(GenericIdentification32 requestPoi,
			com.girmiti.nexo.acceptorcurrencyconversionadviceresponse.GenericIdentification32 responsePOIId) {
		if (!ObjectUtils.isEmpty(requestPoi)) {
			responsePOIId.setId(requestPoi.getId());
		}
	}
}

