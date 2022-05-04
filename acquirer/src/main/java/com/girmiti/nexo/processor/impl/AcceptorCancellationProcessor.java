package com.girmiti.nexo.processor.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorcancellationreqest.AcceptorCancellationRequest8;
import com.girmiti.nexo.acceptorcancellationreqest.CardPaymentEnvironment68;
import com.girmiti.nexo.acceptorcancellationreqest.CardPaymentTransaction82;
import com.girmiti.nexo.acceptorcancellationreqest.GenericIdentification32;
import com.girmiti.nexo.acceptorcancellationreqest.GenericIdentification53;
import com.girmiti.nexo.acceptorcancellationreqest.Organisation32;
import com.girmiti.nexo.acceptorcancellationreqest.PaymentContext26;
import com.girmiti.nexo.acceptorcancellationreqest.PointOfInteraction8;
import com.girmiti.nexo.acceptorcancellationreqest.PointOfInteractionCapabilities6;
import com.girmiti.nexo.acceptorcancellationreqest.SaleContext3;
import com.girmiti.nexo.acceptorcancellationresponse.AcceptorCancellationResponse7;
import com.girmiti.nexo.acceptorcancellationresponse.AcceptorCancellationResponseV07;
import com.girmiti.nexo.acceptorcancellationresponse.AuthorisationResult12;
import com.girmiti.nexo.acceptorcancellationresponse.CardPaymentEnvironment69;
import com.girmiti.nexo.acceptorcancellationresponse.CardPaymentTransaction57;
import com.girmiti.nexo.acceptorcancellationresponse.CardPaymentTransaction68;
import com.girmiti.nexo.acceptorcancellationresponse.CardPaymentTransactionDetails35;
import com.girmiti.nexo.acceptorcancellationresponse.PaymentCard28;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.enums.TransactionType;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionResponse;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.CountryValidationUtil;
import com.girmiti.nexo.util.CurrencyValidationUtil;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;

public class AcceptorCancellationProcessor implements ITransactionProcessor {

	static Logger logger = Logger.getLogger(AcceptorCancellationProcessor.class);
	
	public String process(String acceptorCancellationReqxml) throws Exception {
		com.girmiti.nexo.acceptorcancellationreqest.Document acceptorcancelationDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			acceptorcancelationDocument = (com.girmiti.nexo.acceptorcancellationreqest.Document) jaxbHelper.unmarshall(
					com.girmiti.nexo.acceptorcancellationreqest.Document.class, acceptorCancellationReqxml,
					Constants.CAAA_005_001_08);

		} catch (Exception e) {
			logger.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),acceptorCancellationReqxml);
		}
		return processAuth(acceptorcancelationDocument, acceptorCancellationReqxml);
	}
	
	private String processAuth(com.girmiti.nexo.acceptorcancellationreqest.Document acceptorcancelationDocument,
			String acceptorCancellationReqxml) throws JAXBException, XMLStreamException, FactoryConfigurationError,
			javax.xml.parsers.FactoryConfigurationError {
		AcceptorCancellationRequest8 request = acceptorcancelationDocument.getAccptrCxlReq().getCxlReq();    
		com.girmiti.nexo.acceptorcancellationreqest.CardPaymentEnvironment68 requestEnv = request.getEnvt();

		// -------------- PARSING REQUEST ---------------//
		

		// POI data
		com.girmiti.nexo.acceptorcancellationreqest.PointOfInteraction8 requestPoi = requestEnv.getPOI();
		String pan =null;
		String expiryDate = null;
		if (!ObjectUtils.isEmpty(requestEnv.getCard())
				&& !ObjectUtils.isEmpty(requestEnv.getCard().getPlainCardData())) {
			com.girmiti.nexo.acceptorcancellationreqest.PlainCardData15 plainCardData15 = requestEnv.getCard()
					.getPlainCardData();
			pan = plainCardData15.getPAN();
			expiryDate = plainCardData15.getXpryDt();
		}
		
		// Card payment related info
		CardPaymentTransaction82 reqTransaction = request.getTx();
		XMLGregorianCalendar reqTxDtTm = reqTransaction.getTxId().getTxDtTm();
		String reqTxRef = reqTransaction.getTxId().getTxRef();
		String reqCurrency = reqTransaction.getTxDtls().getCcy();
		BigDecimal reqTotalAmt = reqTransaction.getTxDtls().getTtlAmt();

		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		rejectionMessageRequest.setTxnDate(reqTxDtTm);
		int milliSecond = reqTxDtTm.getMillisecond();
		if(milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,acceptorCancellationReqxml);
			
		}
		
		// ------------ FORMATTING RESPONSE ------------//
		com.girmiti.nexo.acceptorcancellationresponse.Document respDocument = new com.girmiti.nexo.acceptorcancellationresponse.Document();
		AcceptorCancellationResponseV07 response = new AcceptorCancellationResponseV07();
		AcceptorCancellationResponse7 acceptorAuthorisationResponse8 = new AcceptorCancellationResponse7();
		// Set header
		
		// Set AuthorizationResponse - Envt
		com.girmiti.nexo.acceptorcancellationresponse.CardPaymentEnvironment69 responseEnv = new com.girmiti.nexo.acceptorcancellationresponse.CardPaymentEnvironment69();
		com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification32 responsePOIId = new com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification32();
		GenericIdentification53 genericIdentification53 = new GenericIdentification53();
		GenericIdentification32 genericIdentification32 = new GenericIdentification32();
		genericIdentification53 = validateAcquirerid(requestEnv, genericIdentification53);
		genericIdentification32 = validateMerchantId(requestEnv, genericIdentification32);
		PaymentCard28 cardData =new PaymentCard28();
		com.girmiti.nexo.acceptorcancellationresponse.PlainCardData15 plainCardData = new com.girmiti.nexo.acceptorcancellationresponse.PlainCardData15();
		plainCardData.setPAN(pan);
		plainCardData.setXpryDt(expiryDate);
		cardData.setPlainCardData(plainCardData);
		setPioData(requestPoi, responseEnv, responsePOIId);
		setmerchantId(responseEnv, genericIdentification32);
		setAcquirerId(responseEnv, genericIdentification53);
		responseEnv.setCard(cardData);
		
		// Set AuthorizationResponse - Set Tx
		CardPaymentTransaction57 cardPaymentTxn = new CardPaymentTransaction57();
		
		com.girmiti.nexo.acceptorcancellationresponse.TransactionIdentifier1 transactionIdentifier = new com.girmiti.nexo.acceptorcancellationresponse.TransactionIdentifier1();
		transactionIdentifier.setTxDtTm(reqTxDtTm);
		transactionIdentifier.setTxRef(reqTxRef);
		cardPaymentTxn.setTxId(transactionIdentifier);
		cardPaymentTxn.setRcncltnId(reqTransaction.getRcncltnId());
		CardPaymentTransactionDetails35 txnDtls = new CardPaymentTransactionDetails35();
		txnDtls.setCcy(reqCurrency);
		txnDtls.setTtlAmt(reqTotalAmt);
		cardPaymentTxn.setTxDtls(txnDtls);
		
		acceptorAuthorisationResponse8.setTx(cardPaymentTxn);

		// Set AuthorizationResponse - Set TxRspn
		CardPaymentTransaction68 cardPaymentTxnRspn = new CardPaymentTransaction68();
		AuthorisationResult12 authorisationResult = new AuthorisationResult12();
		com.girmiti.nexo.acceptorcancellationresponse.ResponseType5 responseToAuthorization = new com.girmiti.nexo.acceptorcancellationresponse.ResponseType5();
		com.girmiti.nexo.acceptorcancellationreqest.Header35 reqHeader = acceptorcancelationDocument.getAccptrCxlReq().getHdr();
		com.girmiti.nexo.acceptorcancellationresponse.Header35 resHeader = new com.girmiti.nexo.acceptorcancellationresponse.Header35();
		resHeader.setMsgFctn(com.girmiti.nexo.acceptorcancellationresponse.MessageFunction14Code.CCAP);
		resHeader.setPrtcolVrsn(reqHeader.getPrtcolVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		
		int milliSeconds = reqHeader.getCreDtTm().getMillisecond();
		if (milliSeconds < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorCancellationReqxml);
		}
		
		com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		resHeader.setInitgPty(initigParty);
		
		if (CurrencyValidationUtil.isCurrencyNotNull(reqCurrency) && CurrencyValidationUtil.validateCurrency(reqCurrency)) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CURRENCY_ERROR,acceptorCancellationReqxml);			
		}
		
		// validating Message Function
		if (!acceptorcancelationDocument.getAccptrCxlReq().getHdr().getMsgFctn()
				.equals(com.girmiti.nexo.acceptorcancellationreqest.MessageFunction14Code.CCAQ)) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,acceptorCancellationReqxml);
		}

		if (!ObjectUtils.isEmpty(requestPoi) && requestPoi.getCpblties() != null && checkForCapabilities(
				acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getEnvt().getPOI().getCpblties())) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.POI_CAPABILITIES_ERROR,acceptorCancellationReqxml);
		}

		if (acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getEnvt().getMrchnt() != null
				&& checkForMerchant(acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getEnvt().getMrchnt())) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.MERCHANTDATA_ERROR,acceptorCancellationReqxml);
		}
		
		if (acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getCntxt().getSaleCntxt() != null
				&& checkForSalesContext(
						acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getCntxt().getSaleCntxt())) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.SALE_CONTEXT_ERROR,acceptorCancellationReqxml);
		}
		
		//validating AttendantLanguage
		if(acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getCntxt().getPmtCntxt().isAttndntMsgCpbl() && 
				checkAttendantLanguage(
						acceptorcancelationDocument.getAccptrCxlReq().getCxlReq().getCntxt().getPmtCntxt())){
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CONTEXT_ERROR,acceptorCancellationReqxml);
			
		}
		
		if (!ObjectUtils.isEmpty(requestEnv.getCard())
				&& CountryValidationUtil.isCountryNotNull(requestEnv.getCard().getCardCtryCd())
				&& CountryValidationUtil.validateCountry(requestEnv.getCard().getCardCtryCd())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.COUNTRY_ERROR,acceptorCancellationReqxml);

		}
		NexoTxn nexoTxn = setTransactionRequestData(acceptorcancelationDocument, reqTransaction,
        acceptorCancellationReqxml, reqTxRef, responseToAuthorization, resHeader, requestPoi, requestEnv, responseEnv);
		authorisationResult.setRspnToAuthstn(responseToAuthorization);
		cardPaymentTxnRspn.setAuthstnRslt(authorisationResult);
		acceptorAuthorisationResponse8.setTxRspn(cardPaymentTxnRspn);
		acceptorAuthorisationResponse8.setEnvt(responseEnv);
		response.setCxlRspn(acceptorAuthorisationResponse8);
		
		response.setHdr(resHeader);
		respDocument.setAccptrCxlRspn(response);
		String responseXml = JaxbHelper.marshall(respDocument,response);
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

	private GenericIdentification32 validateMerchantId(
			com.girmiti.nexo.acceptorcancellationreqest.CardPaymentEnvironment68 requestEnv,
			GenericIdentification32 genericIdentification32) {
		if (!ObjectUtils.isEmpty(requestEnv.getMrchnt()) && !ObjectUtils.isEmpty(requestEnv.getMrchnt().getId())) {
			genericIdentification32 = requestEnv.getMrchnt().getId();
		}
		return genericIdentification32;
	}

	private GenericIdentification53 validateAcquirerid(
			com.girmiti.nexo.acceptorcancellationreqest.CardPaymentEnvironment68 requestEnv,
			GenericIdentification53 genericIdentification53) {
		if (!ObjectUtils.isEmpty(requestEnv.getAcqrr()) && !ObjectUtils.isEmpty(requestEnv.getAcqrr().getId())) {
			genericIdentification53 = requestEnv.getAcqrr().getId();
		}
		return genericIdentification53;
	}

	private void setAcquirerId(com.girmiti.nexo.acceptorcancellationresponse.CardPaymentEnvironment69 responseEnv,
			GenericIdentification53 genericIdentification53) {
		com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification53 identification53 = new com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification53();
		if (!ObjectUtils.isEmpty(genericIdentification53)) {
			identification53.setId(genericIdentification53.getId());
			responseEnv.setAcqrrId(identification53);
		}
	}

	private void setmerchantId(com.girmiti.nexo.acceptorcancellationresponse.CardPaymentEnvironment69 responseEnv,
			GenericIdentification32 genericIdentification32) {
		if (!ObjectUtils.isEmpty(genericIdentification32)) {
			com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification32 responseMerId = new com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification32();
			responseMerId.setId(genericIdentification32.getId());
			responseEnv.setMrchntId(responseMerId);
		}
	}

	private void setPioData(com.girmiti.nexo.acceptorcancellationreqest.PointOfInteraction8 requestPoi,
			com.girmiti.nexo.acceptorcancellationresponse.CardPaymentEnvironment69 responseEnv,
			com.girmiti.nexo.acceptorcancellationresponse.GenericIdentification32 responsePOIId) {
		if(!ObjectUtils.isEmpty(requestPoi)) {
			responsePOIId.setId(requestPoi.getId().getId());
			responseEnv.setPOIId(responsePOIId);
		}
	}

  private NexoTxn setTransactionRequestData(
      com.girmiti.nexo.acceptorcancellationreqest.Document acceptorcancelationDocument,
      CardPaymentTransaction82 reqTransaction, String acceptorCancellationReqxml, String reqTxRef,
      com.girmiti.nexo.acceptorcancellationresponse.ResponseType5 responseToAuthorization,
      com.girmiti.nexo.acceptorcancellationresponse.Header35 resHeader, PointOfInteraction8 requestPoi, CardPaymentEnvironment68 requestEnv,
      CardPaymentEnvironment69 responseEnv) {
    if(Constants.PG_MOCK != null) {
    	if (Constants.PG_MOCK.equals("false")) {
    	      NexoTxn txn = new NexoTxn();
    	      txn.setPgTxnRef(reqTxRef);
    	      txn = JsonUtil.postServiceRequest(txn, NexoTxn.class,
    	          Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
    	          "findByPgTxRef");
    	      TransactionRequest transactionRequest = new TransactionRequest();
    	      validateCaptureStatus(reqTransaction, reqTxRef, responseToAuthorization, txn, transactionRequest, requestPoi, requestEnv, responseEnv);

    	    } else {
    	      responseToAuthorization.setRspn(com.girmiti.nexo.acceptorcancellationresponse.Response4Code.APPR);
    	    }
    }
    NexoTxn nexoTxn = new NexoTxn();
    try {
      nexoTxn.setRequestType("AcceptorCancellation");
      nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
      nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
      nexoTxn.setTxTxRef(reqTxRef);
      nexoTxn.setMsgFctn(
          String.valueOf(acceptorcancelationDocument.getAccptrCxlReq().getHdr().getMsgFctn()));
      nexoTxn.setRequestData(acceptorCancellationReqxml);
      nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
          Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
          "saveTransactionRequest");
    } catch (Exception e) {
      logger.error("Exception Occured while saving AcceptorCancellation Requset", e);
    }
    return nexoTxn;
  }

	private void validateCaptureStatus(CardPaymentTransaction82 reqTransaction, String reqTxRef,
			com.girmiti.nexo.acceptorcancellationresponse.ResponseType5 responseToAuthorization, NexoTxn txn,
			TransactionRequest transactionRequest, PointOfInteraction8 requestPoi, CardPaymentEnvironment68 requestEnv,
			CardPaymentEnvironment69 responseEnv) {
		TransactionResponse transactionResponse;
		if (!ObjectUtils.isEmpty(txn) && !(txn.getCaptureStatus().equalsIgnoreCase("voided")
				|| txn.getCaptureStatus().equalsIgnoreCase("refunded"))) {
			if (txn.getCaptureStatus().equalsIgnoreCase("false")) {
				transactionRequest.setTransactionType(TransactionType.VOID);
				txn.setCaptureStatus(String.valueOf("voided"));
			} else if (txn.getCaptureStatus().equalsIgnoreCase("true")) {
				transactionRequest.setTransactionType(TransactionType.REFUND);
				transactionRequest.setReconciliationId(reqTransaction.getRcncltnId());
				txn.setCaptureStatus(String.valueOf("refunded"));
			}
			transactionRequest.setTotalTxnAmount(reqTransaction.getTxDtls().getTtlAmt().longValue());
			transactionRequest.setCgRefNumber(txn.getCgTxnRef());
			transactionRequest.setTxnRefNumber(reqTxRef);
			transactionRequest.setMerchantCode(requestEnv.getMrchnt().getSchmeData());
			transactionRequest.setCurrencyCode(reqTransaction.getTxDtls().getCcy());
			transactionRequest.setTerminalId(requestPoi.getSysNm());
			transactionRequest.setTimeZoneOffset("GMT+0530");
			transactionRequest.setTimeZoneRegion("Asia/Kolkata");
			transactionResponse = JsonUtil.postServiceRequest(transactionRequest, TransactionResponse.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"processTransactionBasedOnStatus");
			if (transactionResponse != null && transactionResponse.getErrorCode().equals("00")) {
				responseToAuthorization.setRspn(com.girmiti.nexo.acceptorcancellationresponse.Response4Code.APPR);
				JsonUtil.postServiceRequest(txn, NexoTxn.class,
						Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
						"updateTransactionResponse");
				if (transactionResponse.getApprovalStatus() != null
						&& transactionResponse.getApprovalStatus().equals("APPR")) {
					responseEnv.setCard(null);
				}
			} else {
				responseToAuthorization.setRspn(com.girmiti.nexo.acceptorcancellationresponse.Response4Code.DECL);
			}
		} else {
			responseToAuthorization.setRspn(com.girmiti.nexo.acceptorcancellationresponse.Response4Code.DECL);
		}
	}

	private Boolean checkForCapabilities(PointOfInteractionCapabilities6 pointOfInteractionCapabilities6) {
		Boolean flag = true;
		if (pointOfInteractionCapabilities6.getApprvlCdLngth() != null || pointOfInteractionCapabilities6.getCardRdngCpblties() != null || pointOfInteractionCapabilities6.getCrdhldrVrfctnCpblties() != null
				|| pointOfInteractionCapabilities6.getMsgCpblties() != null || pointOfInteractionCapabilities6.getMxScrptLngth() != null || 
				pointOfInteractionCapabilities6.getOnLineCpblties() != null || pointOfInteractionCapabilities6.getPINLngthCpblties() != null) {
			flag =  false;
		} 
		return flag;
	}

	private Boolean checkForMerchant(Organisation32 mrchnt) {
		Boolean flag = true;
		if (mrchnt.getId() != null || mrchnt.getCmonNm() != null || mrchnt.getLctnCtgy() != null || mrchnt.getLctnAndCtct() != null ||
				mrchnt.getSchmeData() != null)  {
			flag = false;
		} return flag;

	}
	
	private Boolean checkForSalesContext(SaleContext3 saleContext3) {
		Boolean flag = true;
		if (saleContext3.getSaleId() != null || saleContext3.getSaleRefNb() != null || saleContext3.getSaleRcncltnId() != null || saleContext3.getCshrId() != null
				|| saleContext3.getShftNb() != null || saleContext3.getPurchsOrdrNb() != null || saleContext3.getInvcNb() != null || 
				saleContext3.getDlvryNoteNb() != null || saleContext3.getSpnsrdMrchnt() != null || saleContext3.isSpltPmt() != null || 
				saleContext3.getRmngAmt() != null || saleContext3.getAddtlSaleData() != null) {
			flag =  false;
		} 
		return flag;
	}
	
	private Boolean checkAttendantLanguage(PaymentContext26 paymentContext26){
		Boolean flag = true;
		if(paymentContext26.getAttndntLang() != null){
			flag = false;
		}
		return flag;
	}
}