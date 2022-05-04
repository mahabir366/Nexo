package com.girmiti.nexo.processor.impl;

import java.math.BigDecimal;
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

import com.girmiti.nexo.acceptorcurrencyconversionrequest.AcceptorCurrencyConversionRequest6;
import com.girmiti.nexo.acceptorcurrencyconversionrequest.Header35;
import com.girmiti.nexo.acceptorcurrencyconversionrequest.MessageFunction14Code;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.AcceptorCurrencyConversionResponse6;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.AcceptorCurrencyConversionResponseV06;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.CardPaymentTransaction88;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.CardPaymentTransactionDetails47;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.CurrencyConversion16;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.CurrencyConversionResponse3Code;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.PaymentCard28;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.PlainCardData15;
import com.girmiti.nexo.acceptorcurrencyconversionresponse.TransactionIdentifier1;
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

public class AccptrCurrencyConversionProcessor implements ITransactionProcessor{

	private static final Logger LOGGER = LoggerFactory.getLogger(AccptrCurrencyConversionProcessor.class);
	
	public String process(String reqXML) throws Exception {
		com.girmiti.nexo.acceptorcurrencyconversionrequest.Document reqDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			reqDocument = (com.girmiti.nexo.acceptorcurrencyconversionrequest.Document) jaxbHelper.unmarshall(
					com.girmiti.nexo.acceptorcurrencyconversionrequest.Document.class, reqXML, Constants.CAAA_016_001_06);

		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),reqXML);
		}
		return processCurrencyConversion(reqDocument, reqXML);

	}

	private String processCurrencyConversion(com.girmiti.nexo.acceptorcurrencyconversionrequest.Document reqDocument, String reqXML)
			throws JAXBException, XMLStreamException, FactoryConfigurationError,
			javax.xml.parsers.FactoryConfigurationError {
		AcceptorCurrencyConversionRequest6 acceptorCurrencyConversionRequest6 = reqDocument.getAccptrCcyConvsReq().getCcyConvsReq(); 
		Header35 header = reqDocument.getAccptrCcyConvsReq().getHdr();
		MessageFunction14Code messageFunction = header.getMsgFctn();
		String initiatingParty = header.getInitgPty().getId();
		RejectionMessageRequest rejectionMessageRequest=new RejectionMessageRequest();
		String poiID = getPoiId(acceptorCurrencyConversionRequest6);
		String pan = acceptorCurrencyConversionRequest6.getEnvt().getCard().getPlainCardData().getPAN();
		String expiryDate = acceptorCurrencyConversionRequest6.getEnvt().getCard().getPlainCardData().getXpryDt();
				
        XMLGregorianCalendar txtDate = acceptorCurrencyConversionRequest6.getTx().getTxId().getTxDtTm();
        String txtRefId = acceptorCurrencyConversionRequest6.getTx().getTxId().getTxRef();
        
        String currency = acceptorCurrencyConversionRequest6.getTx().getTxDtls().getCcy();
        BigDecimal totalAmont = acceptorCurrencyConversionRequest6.getTx().getTxDtls().getTtlAmt();
        
		com.girmiti.nexo.acceptorcurrencyconversionresponse.Document responseDoc = new com.girmiti.nexo.acceptorcurrencyconversionresponse.Document();
		AcceptorCurrencyConversionResponseV06 acceptorCurrencyConversionResponseV06 = new AcceptorCurrencyConversionResponseV06();
		
		com.girmiti.nexo.acceptorcurrencyconversionresponse.Header35 resHeader = new com.girmiti.nexo.acceptorcurrencyconversionresponse.Header35();
		resHeader.setMsgFctn(com.girmiti.nexo.acceptorcurrencyconversionresponse.MessageFunction14Code.DCCP);		
		resHeader.setPrtcolVrsn(header.getPrtcolVrsn());
		resHeader.setXchgId(header.getXchgId());
		resHeader.setCreDtTm(header.getCreDtTm());
		
		int milliSeconds = header.getCreDtTm().getMillisecond();
		if (milliSeconds < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,reqXML);
		}
		
		com.girmiti.nexo.acceptorcurrencyconversionresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorcurrencyconversionresponse.GenericIdentification53();
		initigParty.setId(initiatingParty);
		resHeader.setInitgPty(initigParty);
		acceptorCurrencyConversionResponseV06.setHdr(resHeader);
		
		AcceptorCurrencyConversionResponse6 acceptorCurrencyConversionResponse6 = new AcceptorCurrencyConversionResponse6();
		com.girmiti.nexo.acceptorcurrencyconversionresponse.CardPaymentEnvironment69 cardPaymentEnvironment69 = new com.girmiti.nexo.acceptorcurrencyconversionresponse.CardPaymentEnvironment69();
		com.girmiti.nexo.acceptorcurrencyconversionresponse.GenericIdentification32 genericIdentification32 = new com.girmiti.nexo.acceptorcurrencyconversionresponse.GenericIdentification32();
		genericIdentification32.setId(poiID);
		
		cardPaymentEnvironment69.setPOIId(genericIdentification32);
		
		PaymentCard28 paymentCard = new PaymentCard28();
		PlainCardData15 plainCardData = new PlainCardData15();
		plainCardData.setPAN(pan);
		plainCardData.setXpryDt(expiryDate);
		paymentCard.setPlainCardData(plainCardData);
		cardPaymentEnvironment69.setCard(paymentCard);
		acceptorCurrencyConversionResponse6.setEnvt(cardPaymentEnvironment69);
		
		int milliSecond = txtDate.getMillisecond();
		if (milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,reqXML);
		}
		
		if (CountryValidationUtil.isCountryNotNull(acceptorCurrencyConversionRequest6.getEnvt().getCard().getCardCtryCd()) && 
				CountryValidationUtil
				.validateCountry(acceptorCurrencyConversionRequest6.getEnvt().getCard().getCardCtryCd())) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.COUNTRY_ERROR,reqXML);
		}
		
		if (CurrencyValidationUtil.isCurrencyNotNull(currency) && CurrencyValidationUtil.validateCurrency(currency)) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CURRENCY_ERROR,reqXML);
		}
		
		CardPaymentTransaction88 cardPaymentTransaction88 = new CardPaymentTransaction88(); 
		TransactionIdentifier1 transactionIdentifier= new TransactionIdentifier1();
		transactionIdentifier.setTxDtTm(txtDate);
		transactionIdentifier.setTxRef(txtRefId);
		cardPaymentTransaction88.setTxId(transactionIdentifier);
		CardPaymentTransactionDetails47 cardPaymentTransactionDetails47 = new CardPaymentTransactionDetails47();
		cardPaymentTransactionDetails47.setCcy(currency);
		CurrencyConversion16 currencyConversion16 = new CurrencyConversion16();	
		DccRequest dccRequest = new DccRequest();
		dccRequest.setTxnRefNumber(txtRefId);
		dccRequest.setTargetCurrency(currency);
		dccRequest.setMerchantCode(poiID);
		dccRequest.setTxnAmount(totalAmont.toString());
		if (expiryDate!=null && !expiryDate.matches(Constants.EXPIRY_DATE_REGEX)) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.EXPIRY_DATE_ERROR,reqXML);
		}
		
		String response = checkCurrencyConversionTxn(rejectionMessageRequest, cardPaymentTransactionDetails47,
				currencyConversion16, dccRequest,reqXML);
		if (response != Constants.SUCCESS_CODE) {
			return response;
		}
		cardPaymentTransaction88.setTxDtls(cardPaymentTransactionDetails47);
		acceptorCurrencyConversionResponse6.setTx(cardPaymentTransaction88);
		acceptorCurrencyConversionResponse6.setCcyConvsRslt(currencyConversion16);
		
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorCurrencyConversion");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setTxTxRef(txtRefId);
			nexoTxn.setMsgFctn(String.valueOf(
					com.girmiti.nexo.acceptorcurrencyconversionresponse.MessageFunction14Code.valueOf(messageFunction.value())));
			nexoTxn.setRequestData(reqXML);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorCurrencyConversion Requset", e);
		}
		
		acceptorCurrencyConversionResponseV06.setCcyConvsRspn(acceptorCurrencyConversionResponse6);
		acceptorCurrencyConversionResponseV06.setHdr(resHeader);
			
		responseDoc.setAccptrCcyConvsRspn(acceptorCurrencyConversionResponseV06);
		String responseXml = JaxbHelper.marshall(responseDoc,responseDoc);
		updateNexoTxn(nexoTxn, responseXml);
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;	
		
	}

	private void updateNexoTxn(NexoTxn nexoTxn, String responseXml) {
		if(nexoTxn != null) {
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
	}

	private String checkCurrencyConversionTxn(RejectionMessageRequest rejectionMessageRequest,
			CardPaymentTransactionDetails47 cardPaymentTransactionDetails47, CurrencyConversion16 currencyConversion16,
			DccRequest dccRequest,String xml) throws javax.xml.parsers.FactoryConfigurationError, JAXBException,
			XMLStreamException, FactoryConfigurationError {
		if (Constants.PG_MOCK != null) {
			if (Constants.PG_MOCK.equals("false")) {
				DccResponse dccResponse = JsonUtil.postServiceRequest(dccRequest, DccResponse.class,
						Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
						"getCurrencyConversionRate");
				if (!ObjectUtils.isEmpty(dccResponse) && !ObjectUtils.isEmpty(dccResponse.getErrorCode())
						&& dccResponse.getErrorCode().equals("00")) {
					currencyConversion16.setRslt(CurrencyConversionResponse3Code.CATG);
					cardPaymentTransactionDetails47.setTtlAmt(new BigDecimal(dccResponse.getConversionAmount()));
				} else {
					return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
							RejectReason1Code.PARS,
							(!ObjectUtils.isEmpty(dccResponse) && !ObjectUtils.isEmpty(dccResponse.getErrorMessage()))
									? dccResponse.getErrorMessage()
									: null,xml);
				}
			}
		} else {
			currencyConversion16.setRslt(CurrencyConversionResponse3Code.CATG);
		}
		return Constants.SUCCESS_CODE;
	}

	private String getPoiId(AcceptorCurrencyConversionRequest6 acceptorCurrencyConversionRequest6) {
		if (!ObjectUtils.isEmpty(acceptorCurrencyConversionRequest6.getEnvt().getPOI())) {
			return acceptorCurrencyConversionRequest6.getEnvt().getPOI().getId().getId();
		} else {
			return null;
		}
	}

}