package com.girmiti.nexo.processor.impl;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorauthorizationrequest.AcceptorAuthorisationRequest8;
import com.girmiti.nexo.acceptorauthorizationrequest.AddressVerification1;
import com.girmiti.nexo.acceptorauthorizationrequest.AttendanceContext1Code;
import com.girmiti.nexo.acceptorauthorizationrequest.AuthenticationMethod5Code;
import com.girmiti.nexo.acceptorauthorizationrequest.CardDataReading6Code;
import com.girmiti.nexo.acceptorauthorizationrequest.CardPaymentEnvironment68;
import com.girmiti.nexo.acceptorauthorizationrequest.CardPaymentServiceType12Code;
import com.girmiti.nexo.acceptorauthorizationrequest.CardPaymentTransaction87;
import com.girmiti.nexo.acceptorauthorizationrequest.DisplayCapabilities4;
import com.girmiti.nexo.acceptorauthorizationrequest.Document;
import com.girmiti.nexo.acceptorauthorizationrequest.MessageFunction14Code;
import com.girmiti.nexo.acceptorauthorizationrequest.OnLineCapability1Code;
import com.girmiti.nexo.acceptorauthorizationrequest.Organisation32;
import com.girmiti.nexo.acceptorauthorizationrequest.PlainCardData15;
import com.girmiti.nexo.acceptorauthorizationrequest.PointOfInteraction8;
import com.girmiti.nexo.acceptorauthorizationrequest.PointOfInteractionCapabilities6;
import com.girmiti.nexo.acceptorauthorizationrequest.SaleContext3;
import com.girmiti.nexo.acceptorauthorizationrequest.TypeOfAmount8Code;
import com.girmiti.nexo.acceptorauthorizationresponse.AcceptorAuthorisationResponse8;
import com.girmiti.nexo.acceptorauthorizationresponse.AcceptorAuthorisationResponseV08;
import com.girmiti.nexo.acceptorauthorizationresponse.Action8;
import com.girmiti.nexo.acceptorauthorizationresponse.ActionMessage2;
import com.girmiti.nexo.acceptorauthorizationresponse.ActionType7Code;
import com.girmiti.nexo.acceptorauthorizationresponse.Algorithm18Code;
import com.girmiti.nexo.acceptorauthorizationresponse.AlgorithmIdentification23;
import com.girmiti.nexo.acceptorauthorizationresponse.AuthorisationResult10;
import com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentEnvironment69;
import com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransaction84;
import com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransaction88;
import com.girmiti.nexo.acceptorauthorizationresponse.ContentInformationType17;
import com.girmiti.nexo.acceptorauthorizationresponse.ContentType2Code;
import com.girmiti.nexo.acceptorauthorizationresponse.EnvelopedData5;
import com.girmiti.nexo.acceptorauthorizationresponse.KEK5;
import com.girmiti.nexo.acceptorauthorizationresponse.KEKIdentifier2;
import com.girmiti.nexo.acceptorauthorizationresponse.OutputFormat1Code;
import com.girmiti.nexo.acceptorauthorizationresponse.Recipient6Choice;
import com.girmiti.nexo.acceptorauthorizationresponse.Response4Code;
import com.girmiti.nexo.acceptorauthorizationresponse.ResponseType5;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.config.BaseUrlConfig;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.enums.EntryModeEnum;
import com.girmiti.nexo.acquirer.enums.MethodOfPaymentTypeEnum;
import com.girmiti.nexo.acquirer.enums.TransactionType;
import com.girmiti.nexo.acquirer.exception.RejectionException;
import com.girmiti.nexo.acquirer.pojo.AuthTransactionProcessorRequest;
import com.girmiti.nexo.acquirer.pojo.CardData;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionResponse;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.CountryValidationUtil;
import com.girmiti.nexo.util.CurrencyValidationUtil;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;
import com.girmiti.nexo.util.Security;

public class AuthTransactionProcessor implements ITransactionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTransactionProcessor.class);

	static NexoTxn nexoTxn1 = new NexoTxn();

	BaseUrlConfig baseUrlConfig = new BaseUrlConfig();
	
	private Boolean IS_ON_LINE = true;

	public String process(String acceptorAuthReqXmlRecr) throws Exception {
		com.girmiti.nexo.acceptorauthorizationrequest.Document authReqDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			if (!acceptorAuthReqXmlRecr.contains("xsi:type") || !acceptorAuthReqXmlRecr.contains("xmlns")
					|| !acceptorAuthReqXmlRecr.contains(" xmlns:xsi")) {
				acceptorAuthReqXmlRecr = acceptorAuthReqXmlRecr.replace("<Document>",
						"<Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			}
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			authReqDocument = (Document) jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class,
					acceptorAuthReqXmlRecr, Constants.CAAA_001_001_08);
		} catch (RejectionException re) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", re);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					re.getRejectionReason(), re.getRejectionCause(), acceptorAuthReqXmlRecr);
		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),acceptorAuthReqXmlRecr);
		}
		return processAuth(authReqDocument, acceptorAuthReqXmlRecr);
	}
	
	private String processAuth(com.girmiti.nexo.acceptorauthorizationrequest.Document authReqDocument, String acceptorAuthReqXmlRecr)
			throws JAXBException, XMLStreamException, FactoryConfigurationError {
		AcceptorAuthorisationRequest8 request = authReqDocument.getAccptrAuthstnReq().getAuthstnReq();
		CardPaymentEnvironment68 requestEnv = request.getEnvt();
		NexoTxn nexoTxn = new NexoTxn();
		// Card payment related info
		CardPaymentTransaction87 reqTransaction = request.getTx();
		XMLGregorianCalendar reqTxDtTm = reqTransaction.getTxId().getTxDtTm();
		String reqTxRef = reqTransaction.getTxId().getTxRef();
		String reqCurrency = reqTransaction.getTxDtls().getCcy();
		BigDecimal reqTotalAmt = reqTransaction.getTxDtls().getTtlAmt();
		byte[] reqICCRltdData=reqTransaction.getTxDtls().getICCRltdData();
		String reconciliationId = reqTransaction.getRcncltnId();
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		rejectionMessageRequest.setTxnDate(reqTxDtTm);
		rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
		rejectionMessageRequest.setProtocolVersion(authReqDocument.getAccptrAuthstnReq().getHdr().getPrtcolVrsn());

		byte[] acquirerMAC = Security.getMACByteArray(acceptorAuthReqXmlRecr,"<AuthstnReq>","</AuthstnReq>",13);
		byte[] acceptorMAC = authReqDocument.getAccptrAuthstnReq().getSctyTrlr() != null
				&& authReqDocument.getAccptrAuthstnReq().getSctyTrlr().getAuthntcdData() != null
						? authReqDocument.getAccptrAuthstnReq().getSctyTrlr().getAuthntcdData().getMAC()
						: null;
						
		if(acceptorMAC != null && !Arrays.equals(acquirerMAC, acceptorMAC)) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion("001");
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.SECU, Constants.MAC_ERROR,acceptorAuthReqXmlRecr);
		}
		
		int milliSecond = reqTxDtTm.getMillisecond();
		if (milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorAuthReqXmlRecr);
		}
		
		if (CurrencyValidationUtil.isCurrencyNotNull(reqCurrency)
				&& CurrencyValidationUtil.validateCurrency(reqCurrency)) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.CURRENCY_ERROR,acceptorAuthReqXmlRecr);
		}
		validateAuthRequestData(authReqDocument, rejectionMessageRequest,acceptorAuthReqXmlRecr);
		// Merchant data
		checkForMerchant(requestEnv, rejectionMessageRequest,acceptorAuthReqXmlRecr);

		validateMerchant(requestEnv, rejectionMessageRequest,acceptorAuthReqXmlRecr);

		// POI data
		PointOfInteraction8 requestPoi = requestEnv.getPOI();
		PlainCardData15 plainCardData15 = getValidPlainCardData(requestEnv);
		if (!ObjectUtils.isEmpty(requestEnv.getCard()) && CountryValidationUtil.isCountryNotNull(requestEnv.getCard().getCardCtryCd())
				&& CountryValidationUtil.validateCountry(requestEnv.getCard().getCardCtryCd())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.COUNTRY_ERROR,acceptorAuthReqXmlRecr);
		}

		String pan = null;
		String cvv = null;
		String expiryDate = null;
		String newExpirydate = null;
		if (plainCardData15 != null && plainCardData15.getPAN() != null && plainCardData15.getXpryDt() != null) {
			pan = plainCardData15.getPAN();
			cvv = plainCardData15.getSvcCd();
			expiryDate = plainCardData15.getXpryDt();
			if(expiryDate.contains("-")) {
			newExpirydate = expiryDate.replace("-", "");
			newExpirydate = newExpirydate.substring(2, 6);
			} else {
				newExpirydate = expiryDate;
			}
		} 

		validatePioData(requestEnv, rejectionMessageRequest,acceptorAuthReqXmlRecr);

		if (requestEnv.getPOI() != null && requestEnv.getPOI().getCpblties() != null
				&& requestEnv.getPOI().getCpblties().getOnLineCpblties() != null
				&& (requestEnv.getPOI().getCpblties().getOnLineCpblties().equals(OnLineCapability1Code.OFLN)
						|| requestEnv.getPOI().getCpblties().getOnLineCpblties().equals(OnLineCapability1Code.SMON))
				&& reqTransaction.isTxCaptr()) {
			IS_ON_LINE = false;
			nexoTxn.setCaptureStatus(OnLineCapability1Code.OFLN.toString());
		} else if (requestEnv.getPOI() != null && requestEnv.getPOI().getCpblties() != null
				&& requestEnv.getPOI().getCpblties().getOnLineCpblties() != null
				&& (requestEnv.getPOI().getCpblties().getOnLineCpblties().equals(OnLineCapability1Code.OFLN)
						|| requestEnv.getPOI().getCpblties().getOnLineCpblties().equals(OnLineCapability1Code.SMON))
				&& !reqTransaction.isTxCaptr()){
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.OFFLINE_TRANSACTION_ERROR, acceptorAuthReqXmlRecr);
		} else {
			nexoTxn.setCaptureStatus(OnLineCapability1Code.ONLN.toString());
		}
		
		validateCardHolder(requestEnv, rejectionMessageRequest,acceptorAuthReqXmlRecr);
		// ------------ FORMATTING RESPONSE ------------//
		com.girmiti.nexo.acceptorauthorizationresponse.Document respDocument = new com.girmiti.nexo.acceptorauthorizationresponse.Document();
		AcceptorAuthorisationResponseV08 response = new AcceptorAuthorisationResponseV08();
		AcceptorAuthorisationResponse8 acceptorAuthorisationResponse8 = new AcceptorAuthorisationResponse8();
		// Set header

		CardPaymentEnvironment69 responseEnv = new CardPaymentEnvironment69();
		// Set AuthorizationResponse - Envt
		checkForPoiData(requestPoi, responseEnv);
		com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification53 acquirerId = new com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification53();
	    setAcquirerId(requestEnv, responseEnv, acquirerId);

		// Set AuthorizationResponse - Set Tx
		CardPaymentTransaction88 cardPaymentTxn = new CardPaymentTransaction88();
		com.girmiti.nexo.acceptorauthorizationresponse.TransactionIdentifier1 transactionIdentifier = new com.girmiti.nexo.acceptorauthorizationresponse.TransactionIdentifier1();
		transactionIdentifier.setTxDtTm(reqTxDtTm);
		cardPaymentTxn.setRcncltnId(reconciliationId);
		com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransactionDetails47 txnDtls = new com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransactionDetails47();
		txnDtls.setCcy(reqCurrency);
		
		if (reqTransaction.getTxDtls().getAmtQlfr() != null
				&& reqTransaction.getTxDtls().getAmtQlfr().equals(TypeOfAmount8Code.ESTM)) {
			txnDtls.setAmtQlfr(com.girmiti.nexo.acceptorauthorizationresponse.TypeOfAmount8Code.ESTM);
		} else if (reqTransaction.getTxDtls().getAmtQlfr() != null
				&& reqTransaction.getTxDtls().getAmtQlfr().equals(TypeOfAmount8Code.INCR)) {
			txnDtls.setAmtQlfr(com.girmiti.nexo.acceptorauthorizationresponse.TypeOfAmount8Code.INCR);
		} else {
			txnDtls.setAmtQlfr(com.girmiti.nexo.acceptorauthorizationresponse.TypeOfAmount8Code.DECR);
		}
		txnDtls.setTtlAmt(reqTotalAmt);
		txnDtls.setVldtyDt(reqTransaction.getTxDtls() != null && reqTransaction.getTxDtls().getVldtyDt() != null
				? reqTransaction.getTxDtls().getVldtyDt()
				: reqTxDtTm);
		if(request.getCntxt().getPmtCntxt().getFllbckInd() != null) {
			txnDtls.setICCRltdData(null);
		} else {
			txnDtls.setICCRltdData(reqICCRltdData);
		}
		
		getDetaildAmounts(reqTransaction, txnDtls);
		getValidityDate(reqTransaction, txnDtls);
		setPlainCardData(requestEnv, responseEnv);
		
		// Set AuthorizationResponse - Set TxRspn
		CardPaymentTransaction84 cardPaymentTxnRspn = new CardPaymentTransaction84();
		AuthorisationResult10 authorisationResult = new AuthorisationResult10();
		ResponseType5 responseToAuthorization = new ResponseType5();
		validateContextData(request, rejectionMessageRequest,acceptorAuthReqXmlRecr);
        checkForPmCntxt(request, rejectionMessageRequest,acceptorAuthReqXmlRecr);
		com.girmiti.nexo.acceptorauthorizationrequest.Header35 reqHeader = authReqDocument.getAccptrAuthstnReq().getHdr();
		com.girmiti.nexo.acceptorauthorizationresponse.Header35 resHeader = new com.girmiti.nexo.acceptorauthorizationresponse.Header35();
		if (authReqDocument.getAccptrAuthstnReq().getHdr().getMsgFctn().toString().equals("FAUQ")) {
			resHeader.setMsgFctn(com.girmiti.nexo.acceptorauthorizationresponse.MessageFunction14Code.FAUP);
		} else if (authReqDocument.getAccptrAuthstnReq().getHdr().getMsgFctn().toString().equals("AUTQ")){
			resHeader.setMsgFctn(com.girmiti.nexo.acceptorauthorizationresponse.MessageFunction14Code.AUTP);
		} else {
			resHeader.setMsgFctn(com.girmiti.nexo.acceptorauthorizationresponse.MessageFunction14Code.RVRA);
		}
		resHeader.setPrtcolVrsn(reqHeader.getPrtcolVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		resHeader.setInitgPty(initigParty);

		int milliSeconds = reqHeader.getCreDtTm().getMillisecond();
		if (milliSeconds < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorAuthReqXmlRecr);
		}
		
		if (request.getTx() != null) {
			if (!(reqHeader.getMsgFctn().equals(MessageFunction14Code.FAUQ))
					&& !(reqHeader.getMsgFctn().equals(MessageFunction14Code.AUTQ))
					&& !(reqHeader.getMsgFctn().equals(MessageFunction14Code.RVRA))) {
				
				if(reqHeader.getMsgFctn().equals(MessageFunction14Code.CCAQ)){
					return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
							RejectReason1Code.MSGT, Constants.MESSAGE_FUNCTION_ERROR,acceptorAuthReqXmlRecr);
				}
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,acceptorAuthReqXmlRecr);
			} 
			CardPaymentServiceType12Code cardPaymentServiceType12Code = request.getTx().getTxTp();
			TypeOfAmount8Code amount8Code = request.getTx().getTxDtls().getAmtQlfr();
			validateTransactionRelatedData(cardPaymentServiceType12Code, amount8Code, rejectionMessageRequest,acceptorAuthReqXmlRecr);
		}
		
		try {
			nexoTxn.setRequestType("AcceptorAuthTransaction");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setAcqrrId(requestEnv.getAcqrr() != null && requestEnv.getAcqrr().getId() != null ? requestEnv.getAcqrr().getId().getId() :null);
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setMrchntId(requestEnv.getMrchnt() != null && requestEnv.getMrchnt().getId() != null ? requestEnv.getMrchnt().getId().getId() :null);
			nexoTxn.setMsgFctn(String.valueOf(
					com.girmiti.nexo.acceptorauthorizationresponse.MessageFunction14Code.valueOf(resHeader.getMsgFctn().toString())));
			nexoTxn.setRequestData(acceptorAuthReqXmlRecr);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorAuthRequset", e);
		}
		
		AuthTransactionProcessorRequest authTransactionProcessorRequest = new AuthTransactionProcessorRequest();
		authTransactionProcessorRequest.setPan(pan);
		authTransactionProcessorRequest.setCvv(cvv);
		authTransactionProcessorRequest.setReqTxRef(reqTxRef);
		authTransactionProcessorRequest.setExpiryDate(newExpirydate);
		authTransactionProcessorRequest.setReqTotalAmt(reqTotalAmt);
		authTransactionProcessorRequest.setMsgFunction(authReqDocument.getAccptrAuthstnReq().getHdr().getMsgFctn().toString());
		authTransactionProcessorRequest.setInitgPty(authReqDocument.getAccptrAuthstnReq().getHdr().getInitgPty().getId().toString());
		authTransactionProcessorRequest.setPrtcolVrsn(authReqDocument.getAccptrAuthstnReq().getHdr().getPrtcolVrsn().toString());
		String errorCode = "";
		try {
			errorCode = setCardAndTransactionData(reqTransaction , authTransactionProcessorRequest,
					acceptorAuthorisationResponse8, cardPaymentTxn, transactionIdentifier, txnDtls,
					authorisationResult, responseToAuthorization, requestEnv,nexoTxn, responseEnv);
		} catch (RejectionException re) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					re.getRejectionReason(), re.getRejectionCause(), acceptorAuthReqXmlRecr);
		}
		
		 if(!errorCode.equals(Constants.SUCCESS_CODE)) {
			 return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, errorCode,acceptorAuthReqXmlRecr);
		 }

		if (reqTotalAmt.compareTo(new BigDecimal(Constants.TTLAMT_2)) != 0) {
			checkForCapabilities(requestPoi, cardPaymentTxnRspn);
		} 
		cardPaymentTxnRspn.setAuthstnRslt(authorisationResult);
		acceptorAuthorisationResponse8.setTxRspn(cardPaymentTxnRspn);
		response.setAuthstnRspn(acceptorAuthorisationResponse8);
		response.setHdr(resHeader);
		respDocument.setAccptrAuthstnRspn(response);
		String responseXml = JaxbHelper.marshall(respDocument, respDocument);
		responseXml = responseXml.replaceAll("ns2:", "");
		updateNexoTxn(nexoTxn, responseXml);
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

	private void checkForCapabilities(PointOfInteraction8 requestPoi, CardPaymentTransaction84 cardPaymentTxnRspn) {
		if (requestPoi != null && requestPoi.getCpblties() != null) {
			List<DisplayCapabilities4> reqMsgCpbltiesList = requestPoi.getCpblties().getMsgCpblties();

			if (reqMsgCpbltiesList != null) {
				Action8 action1;
				com.girmiti.nexo.acceptorauthorizationresponse.ActionMessage2 actionMessage2;
				for (DisplayCapabilities4 dispCapb : reqMsgCpbltiesList) {
					action1 = new Action8();
					actionMessage2 = new com.girmiti.nexo.acceptorauthorizationresponse.ActionMessage2();
					action1.setActnTp(ActionType7Code.DISP);
					setMsgDstn(actionMessage2, dispCapb);
					actionMessage2.setFrmt(OutputFormat1Code.MREF);
					actionMessage2.setMsgCntt("call merchant service");
					actionMessage2.setMsgCnttSgntr(actionMessage2.getMsgCntt().getBytes());
					action1.setMsgToPres(actionMessage2);
					cardPaymentTxnRspn.getActn().add(action1);
				}
			}
		}
	}

	private void checkForPmCntxt(AcceptorAuthorisationRequest8 request,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		if(request.getCntxt().getPmtCntxt() != null) {
        	Boolean bool = request.getCntxt().getPmtCntxt().isAttndntMsgCpbl();
    		validateAttndntMsgCpblAndContextData(request, rejectionMessageRequest, bool,xml);
        }
	}

	private void getValidityDate(CardPaymentTransaction87 reqTransaction,
			com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransactionDetails47 txnDtls) {
		if (reqTransaction.getTxDtls().getVldtyDt() != null) {
			txnDtls.setVldtyDt(reqTransaction.getTxDtls().getVldtyDt());
		}
	}

	private void getDetaildAmounts(CardPaymentTransaction87 reqTransaction,
			com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransactionDetails47 txnDtls) {
		if (reqTransaction.getTxDtls().getDtldAmt() != null) {
			com.girmiti.nexo.acceptorauthorizationresponse.DetailedAmount15 detailedAmount = new com.girmiti.nexo.acceptorauthorizationresponse.DetailedAmount15();
			detailedAmount.setAmtGoodsAndSvcs((reqTransaction.getTxDtls().getDtldAmt().getAmtGoodsAndSvcs() != null ? reqTransaction.getTxDtls().getDtldAmt().getAmtGoodsAndSvcs() : null));
			detailedAmount.setCshBck(reqTransaction.getTxDtls().getDtldAmt().getCshBck() != null ? reqTransaction.getTxDtls().getDtldAmt().getCshBck() : null);
			detailedAmount.setGrtty(reqTransaction.getTxDtls().getDtldAmt().getGrtty() != null ? reqTransaction.getTxDtls().getDtldAmt().getGrtty() : null);
			txnDtls.setDtldAmt(detailedAmount);
		}
	}

	private void checkForPoiData(PointOfInteraction8 requestPoi, CardPaymentEnvironment69 responseEnv) {
		if(requestPoi != null) {
			com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification32 responsePOIId = new com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification32();
			responsePOIId.setId(requestPoi.getId().getId());
			responseEnv.setPOIId(responsePOIId);
		}
	}

	private void checkForMerchant(CardPaymentEnvironment68 requestEnv,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		if (requestEnv.getMrchnt() != null) {
			validateMerchantData(requestEnv, rejectionMessageRequest,xml);
		}
	}

	private void setMsgDstn(ActionMessage2 actionMessage2, DisplayCapabilities4 dispCapb) {
		String reqDstn = dispCapb.getDstn().toString();

		if ("[CDSP]".equals(reqDstn)) {
			actionMessage2.setMsgDstn(com.girmiti.nexo.acceptorauthorizationresponse.UserInterface4Code.CDSP);
		} else if ("[MDSP]".equals(reqDstn)) {
			actionMessage2.setMsgDstn(com.girmiti.nexo.acceptorauthorizationresponse.UserInterface4Code.MDSP);
		} else if ("[CRCP]".equals(reqDstn)) {
			actionMessage2.setMsgDstn(com.girmiti.nexo.acceptorauthorizationresponse.UserInterface4Code.CRCP);
		} else if ("[CRDO]".equals(reqDstn)) {
			actionMessage2.setMsgDstn(com.girmiti.nexo.acceptorauthorizationresponse.UserInterface4Code.CRDO);
		} else if ("[MRCP]".equals(reqDstn)) {
			actionMessage2.setMsgDstn(com.girmiti.nexo.acceptorauthorizationresponse.UserInterface4Code.MRCP);
		}

	}

	private PlainCardData15 getValidPlainCardData(CardPaymentEnvironment68 requestEnv) {
		if(!ObjectUtils.isEmpty(requestEnv.getCard()) && !ObjectUtils.isEmpty(requestEnv.getCard().getPlainCardData())) {
			return requestEnv.getCard().getPlainCardData();
		}
		return null;
	}

	private void setPlainCardData(CardPaymentEnvironment68 requestEnv, CardPaymentEnvironment69 responseEnv) {
		com.girmiti.nexo.acceptorauthorizationresponse.PaymentCard28 card = new com.girmiti.nexo.acceptorauthorizationresponse.PaymentCard28();
		com.girmiti.nexo.acceptorauthorizationresponse.PlainCardData15 plainCardData = new com.girmiti.nexo.acceptorauthorizationresponse.PlainCardData15();
		ContentInformationType17 prtctdCardData = new ContentInformationType17();
		if (requestEnv.getCard() != null) {
		  if(requestEnv.getCard().getPrtctdCardData() != null) {
			if (requestEnv.getCard().getPrtctdCardData().getCnttTp() != null) {
				if (requestEnv.getCard().getPrtctdCardData().getCnttTp().name().equals(ContentType2Code.AUTH.value())) {
					prtctdCardData.setCnttTp(ContentType2Code.AUTH);
				} else if (requestEnv.getCard().getPrtctdCardData().getCnttTp().name()
						.equals(ContentType2Code.DATA.value())) {
					prtctdCardData.setCnttTp(ContentType2Code.DATA);
				} else if (requestEnv.getCard().getPrtctdCardData().getCnttTp().name()
						.equals(ContentType2Code.DGST.value())) {
					prtctdCardData.setCnttTp(ContentType2Code.DGST);
				} else if (requestEnv.getCard().getPrtctdCardData().getCnttTp().name()
						.equals(ContentType2Code.EVLP.value())) {
					prtctdCardData.setCnttTp(ContentType2Code.EVLP);
				} else if (requestEnv.getCard().getPrtctdCardData().getCnttTp().name()
						.equals(ContentType2Code.SIGN.value())) {
					prtctdCardData.setCnttTp(ContentType2Code.SIGN);
				}
			}
			EnvelopedData5 envlpdData = new EnvelopedData5();
			List<Recipient6Choice> recipient6ChoiceList = new ArrayList<>();
			Recipient6Choice recipient6Choice = new Recipient6Choice();
			KEK5 kek = new KEK5();
			KEKIdentifier2 kekId = new KEKIdentifier2();
			if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt() != null) {
				kekId.setKeyId(requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK().getKEKId()
						.getKeyId() != null
								? requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
										.getKEKId().getKeyId()
								: null);
				kekId.setKeyVrsn(requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
						.getKEKId().getKeyVrsn() != null
								? requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
										.getKEKId().getKeyVrsn()
								: null);
				kekId.setDerivtnId(requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
						.getKEKId().getDerivtnId() != null
								? requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
										.getKEKId().getDerivtnId()
								: null);
				kek.setKEKId(kekId);
				AlgorithmIdentification23 keyNcrptnAlgo = new AlgorithmIdentification23();
				if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK().getKeyNcrptnAlgo()
						.getAlgo() != null) {
					if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DKP_9.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.DKP_9);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DA_12.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.DA_12);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DA_19.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.DA_19);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DA_25.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.DA_25);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_36_C.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.E_36_C);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_36_R.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.E_36_R);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_3_DC.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.E_3_DC);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_3_DR.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.E_3_DR);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_2_C.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.EA_2_C);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_2_R.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.EA_2_R);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_5_C.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.EA_5_C);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_5_R.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.EA_5_R);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_9_C.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.EA_9_C);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_9_R.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.EA_9_R);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.N_108.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.N_108);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.SD_5_C.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.SD_5_C);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.UKA_1.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.UKA_1);
					} else if (requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
							.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.UKPT.name())) {
						keyNcrptnAlgo.setAlgo(Algorithm18Code.UKPT);
					}
				}
				kek.setKeyNcrptnAlgo(keyNcrptnAlgo);
				kek.setNcrptdKey(requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
						.getNcrptdKey() != null
								? requestEnv.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
										.getNcrptdKey()
								: null);
				recipient6Choice.setKEK(kek);
				recipient6ChoiceList.add(recipient6Choice);
				envlpdData.getRcpt().add(recipient6Choice);
				prtctdCardData.setEnvlpdData(envlpdData);
			}
			card.setPrtctdCardData(prtctdCardData);
		}
		if (!ObjectUtils.isEmpty(requestEnv) && !ObjectUtils.isEmpty(requestEnv.getCard())
				&& !ObjectUtils.isEmpty(requestEnv.getCard().getPlainCardData())) {
			plainCardData.setPAN(requestEnv.getCard().getPlainCardData().getPAN());
			plainCardData.setSvcCd(requestEnv.getCard().getPlainCardData().getSvcCd());
			plainCardData.setXpryDt(requestEnv.getCard().getPlainCardData().getXpryDt());
			card.setPlainCardData(plainCardData);
		}
		responseEnv.setCard(card);
	  }
	}

	private void setAcquirerId(CardPaymentEnvironment68 requestEnv, CardPaymentEnvironment69 responseEnv,
			com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification53 acquirerId) {
		if (!ObjectUtils.isEmpty(requestEnv.getAcqrr()) && !ObjectUtils.isEmpty(requestEnv.getAcqrr().getId())
				&& !ObjectUtils.isEmpty(requestEnv.getAcqrr().getId().getId())) {
			acquirerId.setId(requestEnv.getAcqrr().getId().getId());
			responseEnv.setAcqrrId(acquirerId);
		}
	}

	private String setCardAndTransactionData(CardPaymentTransaction87 reqTransaction,
      AuthTransactionProcessorRequest authTransactionProcessorRequest, AcceptorAuthorisationResponse8 acceptorAuthorisationResponse8,
      CardPaymentTransaction88 cardPaymentTxn,
      com.girmiti.nexo.acceptorauthorizationresponse.TransactionIdentifier1 transactionIdentifier,
      com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransactionDetails47 txnDtls,
      AuthorisationResult10 authorisationResult, ResponseType5 responseToAuthorization,
      CardPaymentEnvironment68 requestEnv,NexoTxn nexoTxn, CardPaymentEnvironment69 responseEnv ) throws RejectionException {
	  TransactionResponse authTransactionResponse = null;
	  PointOfInteraction8 requestPoi = requestEnv.getPOI();
	  if (IS_ON_LINE && Constants.PG_MOCK.equals("false")) {
    		String terminalId = null; 
			CardData cardData = new CardData();
			cardData.setCardNumber(authTransactionProcessorRequest.getPan() != null ? authTransactionProcessorRequest.getPan() :null);
			cardData.setExpDate(authTransactionProcessorRequest.getExpiryDate() != null ? authTransactionProcessorRequest.getExpiryDate() :null);
			cardData.setCvv(authTransactionProcessorRequest.getCvv() != null ? authTransactionProcessorRequest.getCvv() :null);
			cardData.setCardType(MethodOfPaymentTypeEnum.IP);
			TransactionRequest transactionRequest = new TransactionRequest();
			transactionRequest.setTxnRefNumber(reqTransaction.getTxId().getTxRef());
			transactionRequest.setMerchantCode(requestEnv.getMrchnt() != null ? requestEnv.getMrchnt().getId().getId() :null);
			transactionRequest.setTotalTxnAmount((long)(authTransactionProcessorRequest.getReqTotalAmt().doubleValue()*100));
			transactionRequest.setCardData(cardData);
			transactionRequest.setMerchantAmount(1000l);
			terminalId = getTermialId(requestEnv);
			transactionRequest.setTerminalId(terminalId);
			transactionRequest.setTimeZoneOffset("GMT+0530");
			transactionRequest.setTimeZoneRegion("Asia/Kolkata");
			transactionRequest.setUserName("acqadminuser");
			transactionRequest.setOriginChannel("ADMIN_WEB");
			transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
			transactionRequest.setRegisterNumber("66631671");
			transactionRequest.setCurrencyCode(reqTransaction != null && reqTransaction.getTxDtls() != null ? reqTransaction.getTxDtls().getCcy() : null);
			transactionRequest.setInvoiceNumber(String.valueOf(ThreadLocalRandom.current().nextInt()).replace("-", ""));
			transactionRequest.setOrderId(String.valueOf(ThreadLocalRandom.current().nextInt()).replace("-", ""));
			transactionRequest.setMsgFunction(authTransactionProcessorRequest.getMsgFunction());
			transactionRequest.setInitgPty(authTransactionProcessorRequest.getInitgPty());
			transactionRequest.setPrtcolVrsn(authTransactionProcessorRequest.getPrtcolVrsn());
			transactionRequest.setReqMsgCpbltiesList(
					requestPoi.getCpblties() != null && requestPoi.getCpblties().getMsgCpblties() != null
							? requestPoi.getCpblties().getMsgCpblties()
							: null);
			authTransactionResponse = doAuthTransaction(authorisationResult, reqTransaction, transactionRequest);
			
			if (authTransactionResponse!=null && authTransactionResponse.getRejectionCode() != null) {
				throw new RejectionException(authTransactionResponse.getRejectionCode(),
						authTransactionResponse.getRejectionReason()); 
			}
			
			if(authTransactionResponse != null && authTransactionResponse.getApprovalStatus() != null) {  // for simulator
				getAuthTxnResponse(authorisationResult, responseToAuthorization, authTransactionResponse); 
			} else {	// for PG 
				if (authTransactionResponse != null && authTransactionResponse.getErrorCode().equals("00")) {
					responseToAuthorization.setRspn(Response4Code.APPR);
					responseToAuthorization.setAddtlRspnInf(authTransactionResponse.getAuthId());
					nexoTxn.setCaptureStatus(String.valueOf(reqTransaction.isTxCaptr()));
					nexoTxn.setPgTxnRef(authTransactionResponse.getTxnRefNumber());
					nexoTxn.setCgTxnRef(authTransactionResponse.getCgRefNumber());
					nexoTxn.setPgRrn(authTransactionResponse.getRrn());
					cardPaymentTxn.setSaleRefId(authTransactionResponse.getRrn());
					authorisationResult.setAuthstnCd(authTransactionResponse.getErrorCode());
				} else if(authTransactionResponse != null && authTransactionResponse.getErrorCode().equals("TXN_0030")){
					return authTransactionResponse.getErrorMessage();
				}else {
					responseToAuthorization.setRspn(Response4Code.DECL);
				}
			}
			authorisationResult.setRspnToAuthstn(responseToAuthorization);
				checkForResponse(acceptorAuthorisationResponse8, cardPaymentTxn, transactionIdentifier, txnDtls,
						authTransactionResponse, transactionRequest);
		} else {
			responseToAuthorization.setRspn(Response4Code.APPR);
			cardPaymentTxn.setTxDtls(txnDtls);
			acceptorAuthorisationResponse8.setTx(cardPaymentTxn);
			transactionIdentifier.setTxRef(authTransactionProcessorRequest.getReqTxRef());
			cardPaymentTxn.setTxId(transactionIdentifier);
			authorisationResult.setRspnToAuthstn(responseToAuthorization);
		}
    
    com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification32 merchantId = new com.girmiti.nexo.acceptorauthorizationresponse.GenericIdentification32();
    if(authTransactionResponse != null) {
    	merchantId.setId(authTransactionResponse.getMerchantCode() != null ? authTransactionResponse.getMerchantCode() : null);
        responseEnv.setMrchntId(merchantId);
        acceptorAuthorisationResponse8.setEnvt(responseEnv);
    }
	return Constants.SUCCESS_CODE;
  }

	private void checkForResponse(AcceptorAuthorisationResponse8 acceptorAuthorisationResponse8,
			CardPaymentTransaction88 cardPaymentTxn,
			com.girmiti.nexo.acceptorauthorizationresponse.TransactionIdentifier1 transactionIdentifier,
			com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransactionDetails47 txnDtls,
			TransactionResponse authTransactionResponse, TransactionRequest transactionRequest) {
		if (authTransactionResponse != null && authTransactionResponse.getErrorCode()!= null) {
		cardPaymentTxn.setTxDtls(txnDtls);
		cardPaymentTxn.setInitrTxId(transactionRequest.getInvoiceNumber() != null ? transactionRequest.getInvoiceNumber() : null);
		cardPaymentTxn.setRcptTxId(transactionRequest.getTransactionType().toString());
		acceptorAuthorisationResponse8.setTx(cardPaymentTxn);
		transactionIdentifier.setTxRef(authTransactionResponse.getTxnRefNumber());
		cardPaymentTxn.setTxId(transactionIdentifier);
}
	}

	private void getAuthTxnResponse(AuthorisationResult10 authorisationResult, ResponseType5 responseToAuthorization,
			TransactionResponse authTransactionResponse) {
		if(authTransactionResponse.getApprovalStatus().equalsIgnoreCase("APPROVED")) {
			if (authTransactionResponse.isCmpltnReqrd()) {
				authorisationResult.setCmpltnReqrd(authTransactionResponse.isCmpltnReqrd());
			}
			responseToAuthorization.setRspn(Response4Code.APPR);
			authorisationResult.setAuthstnCd(authTransactionResponse.getAuthstnCd());
		}else if(authTransactionResponse.getApprovalStatus().equalsIgnoreCase("DECLINED")) {
			responseToAuthorization.setRspn(Response4Code.DECL);
			responseToAuthorization.setRspnRsn(authTransactionResponse.getResponseReason());
		}else if (authTransactionResponse.getApprovalStatus().equalsIgnoreCase("PARTIAL")) {
			responseToAuthorization.setRspn(Response4Code.PART);
			authorisationResult.setAuthstnCd(authTransactionResponse.getAuthstnCd());
		}
	}

	private TransactionResponse doAuthTransaction(AuthorisationResult10 authorisationResult, CardPaymentTransaction87 reqTransaction,
			TransactionRequest transactionRequest) {
		TransactionResponse authTransactionResponse;
		if (reqTransaction.isTxCaptr()) {
			authorisationResult.setCmpltnReqrd(false);
			transactionRequest.setReconciliationId(reqTransaction.getRcncltnId());
			transactionRequest.setTransactionType(TransactionType.SALE);
			authTransactionResponse = JsonUtil.postServiceRequest(transactionRequest, TransactionResponse.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"processSaleTransaction");
		} else {
			authorisationResult.setCmpltnReqrd(true);
			transactionRequest.setTransactionType(TransactionType.AUTH);
			authTransactionResponse = JsonUtil.postServiceRequest(transactionRequest, TransactionResponse.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"processAuthTransaction");
		}
		return authTransactionResponse;
	}

	private String getTermialId(CardPaymentEnvironment68 requestEnv) {
		String terminalId;
		if (!ObjectUtils.isEmpty(requestEnv.getPOI()) && !ObjectUtils.isEmpty(requestEnv.getPOI().getCmpnt())) {
			terminalId = requestEnv.getPOI().getCmpnt().get(0).getId().getSrlNb();
		} else {
			if (!ObjectUtils.isEmpty(requestEnv.getMrchnt()) && !ObjectUtils.isEmpty(requestEnv.getMrchnt().getId())
					&& !ObjectUtils.isEmpty(requestEnv.getMrchnt().getId().getId()) && requestEnv.getMrchnt().getId().getId().length() == 15) {
				terminalId = requestEnv.getMrchnt().getId().getId()
						.substring(requestEnv.getMrchnt().getId().getId().length() - 8);
			} else {
				return !ObjectUtils.isEmpty(requestEnv.getMrchnt()) && !ObjectUtils.isEmpty(requestEnv.getMrchnt().getId())
						&& !ObjectUtils.isEmpty(requestEnv.getMrchnt().getId().getId()) ? requestEnv.getMrchnt().getId().getId() : null;
			}
		}
		return terminalId;
	}

  private void validateCardHolder(CardPaymentEnvironment68 requestEnv,
      RejectionMessageRequest rejectionMessageRequest,String xml) {
    if (requestEnv.getCrdhldr() != null) {
      validateCardHolderData(requestEnv, rejectionMessageRequest,xml);
    }
  }

  private void validateMerchant(CardPaymentEnvironment68 requestEnv,
      RejectionMessageRequest rejectionMessageRequest,String xml) {
   

    checkForMerchant(requestEnv, rejectionMessageRequest,xml);
  }


  private void validateAttndntMsgCpblAndContextData(AcceptorAuthorisationRequest8 request,
      RejectionMessageRequest rejectionMessageRequest, Boolean bool,String xml) {
    if (bool != null) {
      validateAttndntMsgCpblData(bool, request, rejectionMessageRequest,xml);
    }

    if (request.getCntxt() != null) {
      valdateSaleContextData(request, rejectionMessageRequest,xml);
    }
  }

	private static String validateAttndntMsgCpblData(Boolean bool, AcceptorAuthorisationRequest8 request,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (bool && (request.getCntxt().getPmtCntxt().getAttndntLang() == null)) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CONTEXT_DATA_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating PIO Data : ", e);
		}

		return null;
	}

	private static String validatePioData(CardPaymentEnvironment68 requestEnv,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (requestEnv.getPOI() != null && requestEnv.getPOI().getCpblties() != null
					&& (validatePOI(requestEnv.getPOI().getCpblties()))) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.POI_CAPABILITIES_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating PIO Data : ", e);
		}
		return null;
	}

	private static String valdateSaleContextData(AcceptorAuthorisationRequest8 request,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (request.getCntxt().getSaleCntxt() != null && (validateSaleContext(request.getCntxt().getSaleCntxt()))) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.SALE_CONTEXT_ERROR,xml);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating SaleContext Data : ", e);
		}
		return null;
	}

	private static String validateMerchantData(CardPaymentEnvironment68 requestEnv,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (validateMerchant(requestEnv.getMrchnt())) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.MERCHANTDATA_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating Merchant Data : ", e);
		}
		return null;

	}

	private static String validateAuthRequestData(Document authReqDocument,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (!authReqDocument.getAccptrAuthstnReq().getHdr().getMsgFctn()
					.equals(com.girmiti.nexo.acceptorauthorizationrequest.MessageFunction14Code.AUTQ)
					&& !authReqDocument.getAccptrAuthstnReq().getHdr().getMsgFctn()
							.equals(com.girmiti.nexo.acceptorauthorizationrequest.MessageFunction14Code.FAUQ)) {

				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating AuthRequest Data : ", e);
		}
		return null;

	}

	private static String validateContextData(AcceptorAuthorisationRequest8 request,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (request.getCntxt() != null && request.getCntxt().getPmtCntxt() != null
					&& request.getCntxt().getPmtCntxt().getCardDataNtryMd() != null
					&& request.getCntxt().getPmtCntxt().getAttndncCntxt() != null) {
				CardDataReading6Code cardDataReading6Code = request.getCntxt().getPmtCntxt().getCardDataNtryMd();
				AttendanceContext1Code attendanceContext1Code = request.getCntxt().getPmtCntxt().getAttndncCntxt();
				 Boolean  boo = request.getCntxt().getPmtCntxt().isCardPres();
				if (boo != null && (boo && ((!cardDataReading6Code.equals(CardDataReading6Code.PHYS)
						|| !cardDataReading6Code.equals(CardDataReading6Code.TAGC))
						|| (!(attendanceContext1Code.equals(AttendanceContext1Code.ATTD)
								|| attendanceContext1Code.equals(AttendanceContext1Code.SATT)
								|| attendanceContext1Code.equals(AttendanceContext1Code.UATT)))))
						|| (boo && !cardDataReading6Code.equals(CardDataReading6Code.TAGC))) {
						return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
								RejectReason1Code.PARS, Constants.CONTEXT_DATA_ERROR,xml);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating Context Data : ", e);
		}
		return null;
	}

	private static String validateTransactionRelatedData(CardPaymentServiceType12Code cardPaymentServiceType12Code,
			 TypeOfAmount8Code amount8Code, RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (cardPaymentServiceType12Code != null && ((!ObjectUtils.isEmpty(amount8Code) && (cardPaymentServiceType12Code.equals(CardPaymentServiceType12Code.CRDP)
					|| cardPaymentServiceType12Code.equals(CardPaymentServiceType12Code.RFND))
					&& (!amount8Code.equals(TypeOfAmount8Code.ACTL)))
			|| (cardPaymentServiceType12Code.equals(CardPaymentServiceType12Code.DEFR)
					&& (!amount8Code.equals(TypeOfAmount8Code.ESTM)
							|| !amount8Code.equals(TypeOfAmount8Code.MAXI)))
			|| (cardPaymentServiceType12Code.equals(CardPaymentServiceType12Code.RESA)
					&& (!amount8Code.equals(TypeOfAmount8Code.ESTM)
							|| !amount8Code.equals(TypeOfAmount8Code.INCR)
							|| !amount8Code.equals(TypeOfAmount8Code.DECR))))) {
					return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
							RejectReason1Code.PARS, Constants.CARD_DATA_ERROR,xml);
				
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating Transaction Data : ", e);
		}
		return null;
	}

	private static String validateCardHolderData(CardPaymentEnvironment68 requestEnv,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {

			if (validateCardHolder(requestEnv.getMrchnt())) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.MERCHANT_DATA_ERROR,xml);
			}

			if (requestEnv.getCrdhldr().getAuthntcn() != null) {
				AuthenticationMethod5Code authenticationMethod5Code = requestEnv.getCrdhldr().getAuthntcn().get(0)
						.getAuthntcnMtd();

				if (!authenticationMethod5Code.equals(AuthenticationMethod5Code.SCRT)
						|| !authenticationMethod5Code.equals(AuthenticationMethod5Code.SNCT)
						|| (authenticationMethod5Code.equals(AuthenticationMethod5Code.NPIN)
								&& (requestEnv.getCrdhldr().getAuthntcn().get(0).getCrdhldrOnLinePIN() != null))
						|| (requestEnv.getCrdhldr().getAuthntcn().get(0).getAdrVrfctn() != null
								&& (validateAddressVerification(
										requestEnv.getCrdhldr().getAuthntcn().get(0).getAdrVrfctn())))) {
					return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
							RejectReason1Code.PARS, Constants.CARD_DATA_ERROR,xml);
				}

			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating CardHolder Data : ", e);
		}
		return null;
	}

	private static boolean validateSaleContext(SaleContext3 saleCntxt) {
		return !(saleCntxt.getAddtlSaleData() != null || saleCntxt.getAllwdNtryMd() != null
				|| saleCntxt.getCshrId() != null || saleCntxt.getCshrLang() != null
				|| saleCntxt.getDlvryNoteNb() != null || saleCntxt.getInvcNb() != null
				|| saleCntxt.getPurchsOrdrNb() != null || saleCntxt.getRmngAmt() != null
				|| saleCntxt.getSaleId() != null || saleCntxt.getSaleRcncltnId() != null
				|| saleCntxt.getSaleRefNb() != null || saleCntxt.getSaleTknScp() != null
				|| saleCntxt.getShftNb() != null || saleCntxt.getSpnsrdMrchnt() != null);
	}

	private static boolean validateAddressVerification(AddressVerification1 adrVrfctn) {
		return !(adrVrfctn.getAdrDgts() != null || adrVrfctn.getPstlCdDgts() != null);
	}

	private static boolean validateCardHolder(Organisation32 mrchnt) {
		return !(mrchnt.getCmonNm() != null || mrchnt.getId() != null || mrchnt.getLctnAndCtct() != null
				|| mrchnt.getLctnCtgy() != null || mrchnt.getSchmeData() != null);
	}

	private static boolean validatePOI(PointOfInteractionCapabilities6 pointOfInteractionCapabilities6) {
		return !(pointOfInteractionCapabilities6.getApprvlCdLngth() != null
				|| pointOfInteractionCapabilities6.getCardRdngCpblties() != null
				|| pointOfInteractionCapabilities6.getCrdhldrVrfctnCpblties() != null
				|| pointOfInteractionCapabilities6.getMsgCpblties() != null
				|| pointOfInteractionCapabilities6.getMxScrptLngth() != null
				|| pointOfInteractionCapabilities6.getOnLineCpblties() != null
				|| pointOfInteractionCapabilities6.getPINLngthCpblties() != null);

	}

	private static boolean validateMerchant(Organisation32 mrchnt) {
		return !(mrchnt.getId() != null || mrchnt.getCmonNm() != null || mrchnt.getLctnAndCtct() != null
				|| mrchnt.getLctnCtgy() != null || mrchnt.getSchmeData() != null);
	}

}
