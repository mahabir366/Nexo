package com.girmiti.nexo.processor.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorcompletionadvice.AcceptorCompletionAdvice8;
import com.girmiti.nexo.acceptorcompletionadvice.Acquirer4;
import com.girmiti.nexo.acceptorcompletionadvice.CardPaymentEnvironment68;
import com.girmiti.nexo.acceptorcompletionadvice.CardPaymentTransaction89;
import com.girmiti.nexo.acceptorcompletionadvice.Cardholder13;
import com.girmiti.nexo.acceptorcompletionadvice.Document;
import com.girmiti.nexo.acceptorcompletionadvice.OnLineCapability1Code;
import com.girmiti.nexo.acceptorcompletionadvice.Organisation32;
import com.girmiti.nexo.acceptorcompletionadvice.PaymentCard28;
import com.girmiti.nexo.acceptorcompletionadvice.PointOfInteraction8;
import com.girmiti.nexo.acceptorcompletionadviceresponse.AcceptorCompletionAdviceResponse7;
import com.girmiti.nexo.acceptorcompletionadviceresponse.AcceptorCompletionAdviceResponseV07;
import com.girmiti.nexo.acceptorcompletionadviceresponse.Algorithm18Code;
import com.girmiti.nexo.acceptorcompletionadviceresponse.AlgorithmIdentification23;
import com.girmiti.nexo.acceptorcompletionadviceresponse.CardPaymentEnvironment69;
import com.girmiti.nexo.acceptorcompletionadviceresponse.CardPaymentTransactionAdviceResponse6;
import com.girmiti.nexo.acceptorcompletionadviceresponse.ContentInformationType17;
import com.girmiti.nexo.acceptorcompletionadviceresponse.ContentType2Code;
import com.girmiti.nexo.acceptorcompletionadviceresponse.EnvelopedData5;
import com.girmiti.nexo.acceptorcompletionadviceresponse.KEK5;
import com.girmiti.nexo.acceptorcompletionadviceresponse.KEKIdentifier2;
import com.girmiti.nexo.acceptorcompletionadviceresponse.MessageFunction14Code;
import com.girmiti.nexo.acceptorcompletionadviceresponse.PartyType3Code;
import com.girmiti.nexo.acceptorcompletionadviceresponse.Recipient6Choice;
import com.girmiti.nexo.acceptorcompletionadviceresponse.Response4Code;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.enums.TransactionType;
import com.girmiti.nexo.acquirer.exception.RejectionException;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataRequest;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataResponse;
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

public class CompletionTransactionProcessor implements ITransactionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompletionTransactionProcessor.class);
	
	public String process(String accptrCmpltnAdvcReq) throws Exception {
		com.girmiti.nexo.acceptorcompletionadvice.Document completionReqDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			if (!accptrCmpltnAdvcReq.contains("xsi:type") || !accptrCmpltnAdvcReq.contains("xmlns")
					|| !accptrCmpltnAdvcReq.contains(" xmlns:xsi")) {
				accptrCmpltnAdvcReq = accptrCmpltnAdvcReq.replace("<Document>",
						"<Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			}
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			completionReqDocument = (com.girmiti.nexo.acceptorcompletionadvice.Document) jaxbHelper.unmarshall(
					com.girmiti.nexo.acceptorcompletionadvice.Document.class, accptrCmpltnAdvcReq, Constants.CAAA_003_001_08);
		} catch (RejectionException re) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", re);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					re.getRejectionReason(), re.getRejectionCause(), accptrCmpltnAdvcReq);
		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),accptrCmpltnAdvcReq);
		}
		return processCompletionTransaction(completionReqDocument, accptrCmpltnAdvcReq);
	}

	private static String processCompletionTransaction(com.girmiti.nexo.acceptorcompletionadvice.Document completionReqDocument,
			String accptrCmpltnAdvcReq)
			throws FactoryConfigurationError, Exception {
		
		//for offline tansactions
		NexoAcquirerDataRequest acquirerDataRequest = new NexoAcquirerDataRequest();
		acquirerDataRequest.setCaptureStatus(OnLineCapability1Code.OFLN.toString());
		NexoAcquirerDataResponse acquirerDataResponse = JsonUtil.postServiceRequest(acquirerDataRequest,
				NexoAcquirerDataResponse.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
				"findByCaptureStatus");
		if (acquirerDataResponse.getNexoTxns() != null) {
			for (NexoTxn nexoTxn : acquirerDataResponse.getNexoTxns()) {
				if (nexoTxn.getRequestData() != null) {
					nexoTxn.setRequestData(
							nexoTxn.getRequestData().replace("<OnLineCpblties>OFLN</OnLineCpblties>", "<OnLineCpblties>ONLN</OnLineCpblties>"));
					AuthTransactionProcessor authTransactionProcessor = new AuthTransactionProcessor();
					byte[] acquirerMAC = Security.getMACByteArray(nexoTxn.getRequestData(),"<AuthstnReq>","</AuthstnReq>",13);
					String mac = Base64.getEncoder().encodeToString(acquirerMAC);
					changeMac(nexoTxn, mac);
					String response = authTransactionProcessor.process(nexoTxn.getRequestData());
					nexoTxn.setCaptureStatus(OnLineCapability1Code.ONLN.toString());
					updateNexoTxn(nexoTxn, response);
				}
			}
		}
		
		AcceptorCompletionAdvice8 request = completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc();
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		CardPaymentTransaction89 reqTransaction1 = request.getTx();
		XMLGregorianCalendar reqTxDtTm1 = reqTransaction1.getTxId().getTxDtTm();
		rejectionMessageRequest.setTxnDate(reqTxDtTm1);
		rejectionMessageRequest.setProtocolVersion(completionReqDocument.getAccptrCmpltnAdvc().getHdr().getPrtcolVrsn());
		int milliSecond = reqTxDtTm1.getMillisecond();
		if (milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,accptrCmpltnAdvcReq);
		}
		
		CardPaymentEnvironment68 requestEnv = request.getEnvt();
		if(!(completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.CCAQ.name()) ||
				completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.RCLQ.name())||
				completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.FCMV.name()) ||
				completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.FRVA.name()) ||
				completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.RVRA.name()) ||
				completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.CMPV.name()) ||
				completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.FRVR.name()))){
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,accptrCmpltnAdvcReq);
		}
		
		// validating Cardholder and Sale Context
		if(completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getEnvt().getCrdhldr() != null && 
				checkForCardHolder(completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getEnvt().getCrdhldr())){
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CARD_DATA_ERROR,accptrCmpltnAdvcReq);
		}
		
      if(completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getCntxt() != null){
    	  String errorCode = validateContextData(completionReqDocument,rejectionMessageRequest,accptrCmpltnAdvcReq);
    	  if (errorCode != null) {
    		  return errorCode;
    	  }
      }
      
		// TransactionCapture
		if ((completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().value().equals(MessageFunction14Code.FCMV.value())
				|| completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().value().equals(MessageFunction14Code.FRVR.value())) &&
				(completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().isTxCaptr() != null
				&& !completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().isTxCaptr())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.CAPTURE_ERROR,accptrCmpltnAdvcReq);
		} 
		
		// check Transaction Success is false Then Failure Reason Must
		if(!completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().isTxSucss() && 
				completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().getFailrRsn()==null){
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.FAIL_REASON_ERROR,accptrCmpltnAdvcReq);
		}
		
		if (CurrencyValidationUtil.isCurrencyNotNull(
				completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().getTxDtls().getCcy()) && 
				CurrencyValidationUtil.validateCurrency(
						completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().getTxDtls().getCcy())) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CURRENCY_ERROR,accptrCmpltnAdvcReq);
		}
		
		// ------------ FORMATTING RESPONSE ------------//
		com.girmiti.nexo.acceptorcompletionadviceresponse.Document respDocument = new com.girmiti.nexo.acceptorcompletionadviceresponse.Document();

		com.girmiti.nexo.acceptorcompletionadvice.Header36 reqHeader = completionReqDocument.getAccptrCmpltnAdvc().getHdr();
		com.girmiti.nexo.acceptorcompletionadviceresponse.Header36 resHeader = new com.girmiti.nexo.acceptorcompletionadviceresponse.Header36();
		resHeader.setMsgFctn(completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.CMPV.name()) ? MessageFunction14Code.CMPK :
			                 messageFunctionFRVA(completionReqDocument) );
			
		resHeader.setPrtcolVrsn(reqHeader.getPrtcolVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		
		getHederData(reqHeader, resHeader);
		
		int milliSeconds = reqHeader.getCreDtTm().getMillisecond();
		if (milliSeconds < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,accptrCmpltnAdvcReq);
		}
		
		com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		initigParty.setTp(PartyType3Code.ACCP);
		resHeader.setInitgPty(initigParty);

		// POI data
		PointOfInteraction8 requestPoi = requestEnv.getPOI();
		Organisation32 merchant = requestEnv.getMrchnt();
		Acquirer4 acqirer = requestEnv.getAcqrr();
		PaymentCard28 cardData = requestEnv.getCard();

		AcceptorCompletionAdviceResponseV07 completionResponse = new AcceptorCompletionAdviceResponseV07();
		AcceptorCompletionAdviceResponse7 acceptorCompletionResponse8 = new AcceptorCompletionAdviceResponse7();

		// Set AuthorizationResponse - Envt
		CardPaymentEnvironment69 responseEnv = new CardPaymentEnvironment69();
		com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification32 responsePOIId = new com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification32();
		setPoiAndMerchantAndAcquirerData(requestPoi, merchant, acqirer, responseEnv, responsePOIId);
		com.girmiti.nexo.acceptorcompletionadviceresponse.PlainCardData15 plnData = new com.girmiti.nexo.acceptorcompletionadviceresponse.PlainCardData15();
		com.girmiti.nexo.acceptorcompletionadviceresponse.PaymentCard28 card = new com.girmiti.nexo.acceptorcompletionadviceresponse.PaymentCard28();
		ContentInformationType17 prtctdCardData = new ContentInformationType17();
		getProtectedCardData(request, prtctdCardData);
		checkForEnvelopedData(request, prtctdCardData);
		card.setPrtctdCardData(prtctdCardData);

		if (cardData.getPlainCardData().getXpryDt() != null
				&& cardData.getPlainCardData().getXpryDt().matches(Constants.EXPIRY_DATE_REGEX)) {
			plnData.setXpryDt(cardData.getPlainCardData().getXpryDt());
		} else {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.EXPIRY_DATE_ERROR,accptrCmpltnAdvcReq);
		}
		String expiryDate = cardData.getPlainCardData().getXpryDt();
		expiryDate = expiryDate.replace("-", "");
		expiryDate = expiryDate.substring(2, 6);
		plnData.setPAN(cardData.getPlainCardData().getPAN());
		plnData.setXpryDt(expiryDate);
		card.setPlainCardData(plnData);
		responseEnv.setCard(card);
		acceptorCompletionResponse8.setEnvt(responseEnv);
		// Card payment related info
		CardPaymentTransaction89 reqTransaction = request.getTx();
		XMLGregorianCalendar reqTxDtTm = reqTransaction.getTxId().getTxDtTm();
		String reqTxRef = reqTransaction.getTxId().getTxRef();
		
		// Set AuthorizationResponse - Set Tx
		CardPaymentTransactionAdviceResponse6 cardPaymentTxn = new CardPaymentTransactionAdviceResponse6();
		
		getReciptTxId(request, cardPaymentTxn);
		
		if (requestEnv.getCard() != null && requestEnv.getCard().getCardCtryCd() != null
				&& CountryValidationUtil.isCountryNotNull(requestEnv.getCard().getCardCtryCd())
				&& CountryValidationUtil.validateCountry(requestEnv.getCard().getCardCtryCd())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.COUNTRY_ERROR,accptrCmpltnAdvcReq);
		}
		
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorCompletionTransaction");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setTxTxRef(reqTxRef);
			nexoTxn.setMsgFctn(String.valueOf(completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn()));
			nexoTxn.setRequestData(accptrCmpltnAdvcReq);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorCompletion Requset", e);
		}
		
		com.girmiti.nexo.acceptorcompletionadviceresponse.TransactionIdentifier1 transactionIdentifier = new com.girmiti.nexo.acceptorcompletionadviceresponse.TransactionIdentifier1();
		cardPaymentTxn.setRcncltnId(reqTransaction.getRcncltnId());
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			transactionResponse = setTransactionData(reqTxRef, cardPaymentTxn, requestPoi, reqTransaction1,
					completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name(),
					completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getTx().isTxCaptr());
		} catch (RejectionException re) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					re.getRejectionReason(), re.getRejectionCause(), accptrCmpltnAdvcReq);
		}
		if(transactionResponse != null && transactionResponse.getErrorCode().equals("TXN_0030")) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, transactionResponse.getErrorMessage(),accptrCmpltnAdvcReq);
		}
		transactionIdentifier.setTxDtTm(reqTxDtTm);
		transactionIdentifier.setTxRef(transactionResponse != null && transactionResponse.getTxnRefNumber() != null
				? transactionResponse.getTxnRefNumber()
				: null);
		cardPaymentTxn.setTxId(transactionIdentifier);
		acceptorCompletionResponse8.setTx(cardPaymentTxn);
		completionResponse.setCmpltnAdvcRspn(acceptorCompletionResponse8);
		completionResponse.setHdr(resHeader);
		respDocument.setAccptrCmpltnAdvcRspn(completionResponse);
		String responseXml = JaxbHelper.marshall(respDocument,completionResponse);
		updateNexoTxn(nexoTxn, responseXml);
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;
	}

	private static void changeMac(NexoTxn nexoTxn, String mac) {
		nexoTxn.setRequestData(nexoTxn.getRequestData()
				.replace(nexoTxn.getRequestData().substring(nexoTxn.getRequestData().indexOf("<MAC>"),
						nexoTxn.getRequestData().indexOf("</MAC>") + 6), "<MAC>" + mac + "</MAC>"));
	}

	private static void updateNexoTxn(NexoTxn nexoTxn, String responseXml) {
		if(nexoTxn != null) {
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
	}

	private static void getReciptTxId(AcceptorCompletionAdvice8 request,
			CardPaymentTransactionAdviceResponse6 cardPaymentTxn) {
		if (request.getTx().getRcptTxId() != null) {
			cardPaymentTxn.setRcptTxId(request.getTx().getRcptTxId());
		}
	}

	private static void checkForEnvelopedData(AcceptorCompletionAdvice8 request,
			ContentInformationType17 prtctdCardData) {
		if (request.getEnvt().getCard() != null && request.getEnvt().getCard().getPrtctdCardData() != null
				&& request.getEnvt().getCard().getPrtctdCardData().getCnttTp() != null) {
			EnvelopedData5 envelopedData5 = new EnvelopedData5();
			List<Recipient6Choice> rcpt = new ArrayList<>();
			Recipient6Choice recipient6Choice = new Recipient6Choice();
			KEK5 kek = new KEK5();
			KEKIdentifier2 kekIdentifier2 = new KEKIdentifier2();
			getEnvData(request, kekIdentifier2);
			kek.setKEKId(kekIdentifier2);
			AlgorithmIdentification23 algorithmIdentification23 = new AlgorithmIdentification23();
			getAlgorithamId(request, algorithmIdentification23);
			kek.setKeyNcrptnAlgo(algorithmIdentification23);
			kek.setNcrptdKey(request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getNcrptdKey() != null
							? request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
									.getNcrptdKey()
							: null);
			recipient6Choice.setKEK(kek);
			rcpt.add(recipient6Choice);
			envelopedData5.getRcpt().add(recipient6Choice);
			prtctdCardData.setEnvlpdData(envelopedData5);
		}
	}

	private static void getHederData(com.girmiti.nexo.acceptorcompletionadvice.Header36 reqHeader,
			com.girmiti.nexo.acceptorcompletionadviceresponse.Header36 resHeader) {
		if(reqHeader.getReTrnsmssnCntr()!=null) {
			resHeader.setReTrnsmssnCntr(reqHeader.getReTrnsmssnCntr());
		}
	}

	private static void setPoiAndMerchantAndAcquirerData(PointOfInteraction8 requestPoi, Organisation32 merchant,
			Acquirer4 acqirer, CardPaymentEnvironment69 responseEnv,
			com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification32 responsePOIId) {
		if (!ObjectUtils.isEmpty(requestPoi)) {
			responsePOIId.setId(requestPoi.getId().getId());
			responseEnv.setPOIId(responsePOIId);
		}
		com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification53 acQ = new com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification53();
		if (!ObjectUtils.isEmpty(acqirer)) {
			acQ.setId(acqirer.getId().getId());
			responseEnv.setAcqrrId(acQ);
		}
		com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification32 merch = new com.girmiti.nexo.acceptorcompletionadviceresponse.GenericIdentification32();
		if (!ObjectUtils.isEmpty(merchant)) {
			merch.setId(merchant.getId().getId());
			responseEnv.setMrchntId(merch);
		}
	}

	private static void getProtectedCardData(AcceptorCompletionAdvice8 request,
			ContentInformationType17 prtctdCardData) {
		if (request.getEnvt().getCard() != null && request.getEnvt().getCard().getPrtctdCardData() != null
				&& request.getEnvt().getCard().getPrtctdCardData().getCnttTp() != null) {
			if ((request.getEnvt().getCard().getPrtctdCardData().getCnttTp().name())
					.equals(ContentType2Code.DATA.value())) {
				prtctdCardData.setCnttTp(ContentType2Code.DATA);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.DGST.value())) {
				prtctdCardData.setCnttTp(ContentType2Code.DGST);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.EVLP.name())) {
				prtctdCardData.setCnttTp(ContentType2Code.EVLP);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.SIGN.value())) {
				prtctdCardData.setCnttTp(ContentType2Code.SIGN);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.AUTH.value())) {
				prtctdCardData.setCnttTp(ContentType2Code.AUTH);
			}
		}
	}

	private static void getEnvData(AcceptorCompletionAdvice8 request, KEKIdentifier2 kekIdentifier2) {
		if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
				.getKEKId() != null) {
			kekIdentifier2.setKeyId(request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKEK().getKEKId().getKeyId() != null ? request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
							.getKEK().getKEKId().getKeyId() : null);
			kekIdentifier2.setDerivtnId(request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt()
					.get(0).getKEK().getKEKId().getDerivtnId() != null ? request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt()
							.get(0).getKEK().getKEKId().getDerivtnId() : null);
			kekIdentifier2.setKeyVrsn(request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt()
					.get(0).getKEK().getKEKId().getKeyVrsn() != null ? request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt()
							.get(0).getKEK().getKEKId().getKeyVrsn() : null);
		}
	}

	private static void getAlgorithamId(AcceptorCompletionAdvice8 request,
			AlgorithmIdentification23 algorithmIdentification23) {
		if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
				.getKeyNcrptnAlgo().getAlgo() != null) {
			if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DKP_9.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.DKP_9);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DA_12.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.DA_12);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DA_19.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.DA_19);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.DA_25.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.DA_25);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_36_C.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.E_36_C);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_36_R.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.E_36_R);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_3_DC.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.E_3_DC);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.E_3_DR.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.E_3_DR);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_2_C.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.EA_2_C);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_2_R.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.EA_2_R);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_5_C.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.EA_5_C);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_5_R.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.EA_5_R);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_9_C.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.EA_9_C);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.EA_9_R.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.EA_9_R);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.N_108.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.N_108);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.SD_5_C.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.SD_5_C);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.UKA_1.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.UKA_1);
			} else if (request.getEnvt().getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0).getKEK()
					.getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm18Code.UKPT.name())) {
				algorithmIdentification23.setAlgo(Algorithm18Code.UKPT);
			}
		}
	}

	private static MessageFunction14Code messageFunctionFRVA(
			com.girmiti.nexo.acceptorcompletionadvice.Document completionReqDocument) {
		return completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.FRVA.name()) ? MessageFunction14Code.FRVR :
		 messageFunctionFCMV(completionReqDocument);
	}

	private static MessageFunction14Code messageFunctionFCMV(
			com.girmiti.nexo.acceptorcompletionadvice.Document completionReqDocument) {
		return completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.FCMV.name()) ? MessageFunction14Code.FCMK :
		 messageFunctionRVRR(completionReqDocument);
	}

	private static MessageFunction14Code messageFunctionRVRR(
			com.girmiti.nexo.acceptorcompletionadvice.Document completionReqDocument) {
		return completionReqDocument.getAccptrCmpltnAdvc().getHdr().getMsgFctn().name().equals(MessageFunction14Code.RVRA.name()) ? MessageFunction14Code.RVRR :	 
			 MessageFunction14Code.CMPK;
	}
	
  private static TransactionResponse setTransactionData(String reqTxRef,
			CardPaymentTransactionAdviceResponse6 cardPaymentTxn, PointOfInteraction8 requestPoi,
			CardPaymentTransaction89 reqTransaction1, String messageFunction, Boolean capture) throws RejectionException {
		TransactionResponse captureTransactionResponse = null;
		NexoTxn txn = new NexoTxn();
		txn.setPgTxnRef(reqTxRef);
		txn = JsonUtil.postServiceRequest(txn, NexoTxn.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION, "findByPgTxRef");
		if (!ObjectUtils.isEmpty(Constants.PG_MOCK) && Constants.PG_MOCK.equals("false")) {
			if (!MessageFunction14Code.CMPV.toString().equals(messageFunction) && capture) {
				if (txn != null) {
					TransactionRequest transactionRequest = new TransactionRequest();
					transactionRequest.setTxnRefNumber(reqTxRef);
					transactionRequest.setTransactionType(TransactionType.CAPTURE);
					transactionRequest.setCgRefNumber(txn.getCgTxnRef());
					transactionRequest.setMerchantCode(reqTransaction1.getMrchntRefData());
					transactionRequest.setTerminalId(requestPoi != null ? requestPoi.getSysNm() : null);
					transactionRequest.setTimeZoneOffset("GMT+0530");
					transactionRequest.setTimeZoneRegion("Asia/Kolkata");
					transactionRequest.setCurrencyCode(reqTransaction1.getTxDtls().getCcy());
					transactionRequest.setReconciliationId(cardPaymentTxn.getRcncltnId());
					transactionRequest.setAuthId(cardPaymentTxn.getRcptTxId());
					transactionRequest.setTxnAmount((Long) reqTransaction1.getTxDtls().getTtlAmt().longValue());
					captureTransactionResponse = JsonUtil.postServiceRequest(transactionRequest,
							TransactionResponse.class,
							Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
							"processCaptureTransaction");
					if (captureTransactionResponse.getRejectionCode() != null) {
						throw new RejectionException(captureTransactionResponse.getRejectionCode(),
								captureTransactionResponse.getRejectionReason());
					}
					return validatecaptureTransactionResponse(cardPaymentTxn, captureTransactionResponse, txn);
				} else {
					cardPaymentTxn.setRspn(Response4Code.DECL);
				}
			} else {
				cardPaymentTxn.setRspn(Response4Code.APPR);
			}
		}else {
			cardPaymentTxn.setRspn(Response4Code.APPR);
		}
		return captureTransactionResponse;
	}

private static TransactionResponse validatecaptureTransactionResponse(CardPaymentTransactionAdviceResponse6 cardPaymentTxn,
		TransactionResponse captureTransactionResponse, NexoTxn txn) {
	if (captureTransactionResponse != null && captureTransactionResponse.getErrorCode().equals("00")) {
		cardPaymentTxn.setRspn(Response4Code.APPR);
		cardPaymentTxn.setSaleRefId(captureTransactionResponse.getRrn());
		cardPaymentTxn.setRcptTxId(captureTransactionResponse.getAuthId());
		cardPaymentTxn.setInitrTxId(captureTransactionResponse.getInvoiceNumber());
		txn.setCaptureStatus(String.valueOf("true"));
		txn.setCgTxnRef(captureTransactionResponse.getCgRefNumber());
		txn.setPgTxnRef(captureTransactionResponse.getTxnRefNumber());
		JsonUtil.postServiceRequest(txn, NexoTxn.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
				"updateTransactionResponse");
		return captureTransactionResponse;
	} else if (captureTransactionResponse != null
			&& captureTransactionResponse.getErrorCode().equals("TXN_0030")) {
		return captureTransactionResponse;
	} else {
		cardPaymentTxn.setRspn(Response4Code.DECL);
	}
	return captureTransactionResponse;
}

	private static String validateContextData(Document completionReqDocument,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		if (completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getCntxt().getSaleCntxt() != null && 
				checkForSaleContext(
						completionReqDocument.getAccptrCmpltnAdvc().getCmpltnAdvc().getCntxt().getSaleCntxt())) {
				try {
					return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
							RejectReason1Code.PARS, Constants.SALE_CONTEXT_ERROR,xml);
				} catch (Exception e) {
					LOGGER.error("Exception Occured While Validating Context Data : ", e);
				}
			
		}
		return null;
	}

	private static  boolean checkForSaleContext(com.girmiti.nexo.acceptorcompletionadvice.SaleContext3 saleCntxt) {
		Boolean flag = true;
		if (saleCntxt.getAddtlSaleData() != null) {
			flag =  false;
		} else if (saleCntxt.getAllwdNtryMd() != null) {
			flag =  false;
		} else if (saleCntxt.getCshrId() != null) {
			flag =  false;
		} else if (saleCntxt.getCshrLang() != null) {
			flag =  false;
		} else if (saleCntxt.getDlvryNoteNb() != null) {
			flag =  false;
		} else if (saleCntxt.getInvcNb() != null) {
			flag =  false;
		} else if (saleCntxt.getPurchsOrdrNb() != null) {
			flag =  false;
		} else if (saleCntxt.getRmngAmt() != null) {
			flag =  false;
		}  else if (saleCntxt.getSaleId() != null) {
			flag =  false;
		} else if (saleCntxt.getSaleRcncltnId() != null) {
			flag =  false;
		} else if (saleCntxt.getSaleTknScp() != null) {
			flag =  false;
		} else if (saleCntxt.getSaleRefNb() != null) {
			flag =  false;
		} else if (saleCntxt.getShftNb() != null) {
			flag =  false;
		} else if (saleCntxt.getSpnsrdMrchnt() != null) {
			return false;
		}
		return flag;
	}
	
	private static  boolean checkForCardHolder(Cardholder13 paymentCard28) {
		Boolean flag = true;
		if (paymentCard28.getId() != null || paymentCard28.getNm() != null || paymentCard28.getLang() != null || paymentCard28.getBllgAdr() != null || 
				paymentCard28.getShppgAdr() != null || paymentCard28.getTripNb() != null || paymentCard28.getVhcl() != null || 
				paymentCard28.getAuthntcn() != null || paymentCard28.getTxVrfctnRslt() != null) {
			flag =  false;
		} 
		return flag;
	}
}
