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
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorcancellationadvice.AcceptorCancellationAdvice8;
import com.girmiti.nexo.acceptorcancellationadvice.Acquirer4;
import com.girmiti.nexo.acceptorcancellationadvice.CardPaymentContext27;
import com.girmiti.nexo.acceptorcancellationadvice.CardPaymentEnvironment68;
import com.girmiti.nexo.acceptorcancellationadvice.CardPaymentTransaction83;
import com.girmiti.nexo.acceptorcancellationadvice.CardPaymentTransactionDetails34;
import com.girmiti.nexo.acceptorcancellationadvice.Document;
import com.girmiti.nexo.acceptorcancellationadvice.Header36;
import com.girmiti.nexo.acceptorcancellationadvice.Organisation32;
import com.girmiti.nexo.acceptorcancellationadvice.PointOfInteractionCapabilities6;
import com.girmiti.nexo.acceptorcancellationadvice.SaleContext3;
import com.girmiti.nexo.acceptorcancellationadviceresponse.AcceptorCancellationAdviceResponse7;
import com.girmiti.nexo.acceptorcancellationadviceresponse.AcceptorCancellationAdviceResponseV07;
import com.girmiti.nexo.acceptorcancellationadviceresponse.Algorithm7Code;
import com.girmiti.nexo.acceptorcancellationadviceresponse.AlgorithmIdentification19;
import com.girmiti.nexo.acceptorcancellationadviceresponse.AttributeType1Code;
import com.girmiti.nexo.acceptorcancellationadviceresponse.CardPaymentEnvironment69;
import com.girmiti.nexo.acceptorcancellationadviceresponse.CardPaymentTransactionAdviceResponse6;
import com.girmiti.nexo.acceptorcancellationadviceresponse.CertificateIssuer1;
import com.girmiti.nexo.acceptorcancellationadviceresponse.ContentInformationType17;
import com.girmiti.nexo.acceptorcancellationadviceresponse.ContentType2Code;
import com.girmiti.nexo.acceptorcancellationadviceresponse.EnvelopedData5;
import com.girmiti.nexo.acceptorcancellationadviceresponse.GenericIdentification32;
import com.girmiti.nexo.acceptorcancellationadviceresponse.GenericIdentification53;
import com.girmiti.nexo.acceptorcancellationadviceresponse.IssuerAndSerialNumber1;
import com.girmiti.nexo.acceptorcancellationadviceresponse.KeyTransport5;
import com.girmiti.nexo.acceptorcancellationadviceresponse.OriginatorInformation1;
import com.girmiti.nexo.acceptorcancellationadviceresponse.PaymentCard28;
import com.girmiti.nexo.acceptorcancellationadviceresponse.PlainCardData15;
import com.girmiti.nexo.acceptorcancellationadviceresponse.Recipient5Choice;
import com.girmiti.nexo.acceptorcancellationadviceresponse.Recipient6Choice;
import com.girmiti.nexo.acceptorcancellationadviceresponse.RelativeDistinguishedName1;
import com.girmiti.nexo.acceptorcancellationadviceresponse.Response4Code;
import com.girmiti.nexo.acceptorrejection.Header26;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.exception.RejectionException;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceRequest;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceResponse;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.CountryValidationUtil;
import com.girmiti.nexo.util.CurrencyValidationUtil;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;

public class AcceptorCancellationAdviceProcessor implements ITransactionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptorCancellationAdviceProcessor.class);
	
	public String process(String xml) throws Exception {
		com.girmiti.nexo.acceptorcancellationadvice.Document document = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			JaxbHelper jaxbHelper = new JaxbHelper();
			document = (com.girmiti.nexo.acceptorcancellationadvice.Document) jaxbHelper
					.unmarshall(com.girmiti.nexo.acceptorcancellationadvice.Document.class, xml, Constants.CAAA_007_001_08);
		} catch (RejectionException re) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", re);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					re.getRejectionReason(), re.getRejectionCause(), xml);
		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),xml);
		}

		return processCancellationAdvice(document,xml);
	}

	private String processCancellationAdvice(Document authReqDocument, String xml) throws JAXBException, XMLStreamException,
			FactoryConfigurationError, javax.xml.parsers.FactoryConfigurationError, RejectionException {
		AcceptorCancellationAdvice8 acceptorCancellationAdvice8 = authReqDocument.getAccptrCxlAdvc().getCxlAdvc();
		CardPaymentEnvironment68 cardPaymentEnvironment68 = acceptorCancellationAdvice8.getEnvt();
		CardPaymentTransaction83 cardPaymentTransaction83 = acceptorCancellationAdvice8.getTx();
		XMLGregorianCalendar reqTxDtTm = cardPaymentTransaction83.getTxId().getTxDtTm();
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		rejectionMessageRequest.setTxnDate(reqTxDtTm);
		rejectionMessageRequest.setProtocolVersion(authReqDocument.getAccptrCxlAdvc().getHdr().getPrtcolVrsn());
		
		int milliSecond = reqTxDtTm.getMillisecond();
		if(milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,xml);
		}
		
		// validating Message Function
		validateMessageFuctionData(authReqDocument,rejectionMessageRequest,xml);

		// validating Capabilities
		validateCapabilities(authReqDocument, rejectionMessageRequest,xml);

		// validating Context and Sale Context
		validateContextAndSaleContext(authReqDocument, rejectionMessageRequest,xml);

		// Acquirer data
		String acqrrId;
		com.girmiti.nexo.acceptorcancellationadvice.Acquirer4 acqrr = cardPaymentEnvironment68.getAcqrr();
		String msg = validateAcquirerData(cardPaymentEnvironment68.getAcqrr(), 
				rejectionMessageRequest,xml);
		if (msg == null) {
			acqrrId = getAcquirerId(acqrr);
		} else {
			return msg;
		}
		
		// Merchant data
		com.girmiti.nexo.acceptorcancellationadvice.Organisation32 mer = cardPaymentEnvironment68.getMrchnt();
		String merchId = mer.getId().getId();
		  mer.getCmonNm();

		// POI data
		com.girmiti.nexo.acceptorcancellationadvice.PointOfInteraction8 requestPoi = cardPaymentEnvironment68.getPOI();
		com.girmiti.nexo.acceptorcancellationadvice.PlainCardData15 plainCardData15 = getPlainCard(cardPaymentEnvironment68);
		String reqTxRef = cardPaymentTransaction83.getTxId().getTxRef();
		String reqCurrency = cardPaymentTransaction83.getTxDtls().getCcy();
		BigDecimal reqTotalAmt = cardPaymentTransaction83.getTxDtls().getTtlAmt();
		if (!ObjectUtils.isEmpty(cardPaymentEnvironment68) && !ObjectUtils.isEmpty(cardPaymentEnvironment68.getCard())
				&& CountryValidationUtil.isCountryNotNull(cardPaymentEnvironment68.getCard().getCardCtryCd())
				&& CountryValidationUtil.validateCountry(cardPaymentEnvironment68.getCard().getCardCtryCd())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.COUNTRY_ERROR,xml);

		}
		
		if (CurrencyValidationUtil.isCurrencyNotNull(reqCurrency) && CurrencyValidationUtil.validateCurrency(reqCurrency)) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CURRENCY_ERROR,xml);
			
		}

		com.girmiti.nexo.acceptorcancellationadviceresponse.Document document = new com.girmiti.nexo.acceptorcancellationadviceresponse.Document();
		AcceptorCancellationAdviceResponse7 acceptorCancellationAdviceResponse7 = new AcceptorCancellationAdviceResponse7();
		AcceptorCancellationAdviceResponseV07 acceptorCancellationAdviceResponseV07 = new AcceptorCancellationAdviceResponseV07();

		com.girmiti.nexo.acceptorcancellationadviceresponse.CardPaymentEnvironment69 responseEnv = new com.girmiti.nexo.acceptorcancellationadviceresponse.CardPaymentEnvironment69();
		com.girmiti.nexo.acceptorcancellationadviceresponse.GenericIdentification32 responsePOIId = new com.girmiti.nexo.acceptorcancellationadviceresponse.GenericIdentification32();
		GenericIdentification53 genericIdentification53 = new GenericIdentification53();
		setPoiId(requestPoi, responseEnv, responsePOIId);
		setAcquirerId(cardPaymentEnvironment68, responseEnv, genericIdentification53);
		setMerchantId(mer,responseEnv);
		PaymentCard28 paymentCard28 = new PaymentCard28(); 
		PlainCardData15 cardData15 = new PlainCardData15();
		ContentInformationType17 contentInformationType17 = new ContentInformationType17();
		if (cardPaymentEnvironment68.getCard()!=null && cardPaymentEnvironment68.getCard().getPrtctdCardData() != null) {
			getCardData(cardPaymentEnvironment68, paymentCard28, contentInformationType17);
		}
		setCardDetails(plainCardData15, paymentCard28, cardData15);
		responseEnv.setCard(paymentCard28);
		acceptorCancellationAdviceResponse7.setEnvt(responseEnv);

		CardPaymentTransactionAdviceResponse6 cardPaymentTxn = new CardPaymentTransactionAdviceResponse6();

		com.girmiti.nexo.acceptorcancellationadviceresponse.TransactionIdentifier1 transactionIdentifier = new com.girmiti.nexo.acceptorcancellationadviceresponse.TransactionIdentifier1();
		transactionIdentifier.setTxDtTm(reqTxDtTm);
		transactionIdentifier.setTxRef(reqTxRef);

		CardPaymentTransactionDetails34 txnDtls = new CardPaymentTransactionDetails34();
		txnDtls.setCcy(reqCurrency);
		txnDtls.setTtlAmt(reqTotalAmt);

		Header36 reqHeader = authReqDocument.getAccptrCxlAdvc().getHdr();
		CancellationAdviceResponse transactionResponse = new CancellationAdviceResponse();
		try {
			transactionResponse = getCancellationAdviceResponse(rejectionMessageRequest, merchId, reqTxRef, reqTotalAmt,
					cardPaymentTxn, reqHeader, transactionResponse);
		} catch (RejectionException re) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					re.getRejectionReason(), re.getRejectionCause(), xml);
		}
		
		if (!ObjectUtils.isEmpty(transactionResponse) && !ObjectUtils.isEmpty(transactionResponse.getErrorCode())
				&& transactionResponse.getErrorCode().equals("-99")) {
			rejectionMessageRequest.setProtocolVersion(reqHeader.getPrtcolVrsn());
			com.girmiti.nexo.acceptorrejection.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorrejection.GenericIdentification53();
			initigParty.setId(reqHeader.getInitgPty().getId());
			Header26 header26 = new Header26();
			header26.setInitgPty(initigParty);
			header26.setXchgId(reqHeader.getXchgId());
			rejectionMessageRequest.setHeader26(header26);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.DPMG, Constants.DUPLICATE_MESSAGE,xml);
		}
		setTxnRef(transactionIdentifier, transactionResponse);
		
		cardPaymentTxn.setTxId(transactionIdentifier);
		acceptorCancellationAdviceResponse7.setTx(cardPaymentTxn);
		acceptorCancellationAdviceResponseV07.setCxlAdvcRspn(acceptorCancellationAdviceResponse7);

		com.girmiti.nexo.acceptorcancellationadviceresponse.Header36 resHeader = new com.girmiti.nexo.acceptorcancellationadviceresponse.Header36();
		resHeader.setMsgFctn(com.girmiti.nexo.acceptorcancellationadviceresponse.MessageFunction14Code.CCAK);
		resHeader.setPrtcolVrsn(reqHeader.getPrtcolVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		GenericIdentification53 initigParty = new GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		resHeader.setInitgPty(initigParty);
		
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorCancellationAdvice");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setAcqrrId(acqrrId);
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setTxTxRef(reqTxRef);
			nexoTxn.setMrchntId(merchId);
			nexoTxn.setMsgFctn(String.valueOf(authReqDocument.getAccptrCxlAdvc().getHdr().getMsgFctn()));
			nexoTxn.setRequestData(xml);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorCancellationAdive Requset", e);
		}

		acceptorCancellationAdviceResponseV07.setHdr(resHeader);
		document.setAccptrCxlAdvcRspn(acceptorCancellationAdviceResponseV07);
		String responseXml = JaxbHelper.marshall(document, acceptorCancellationAdviceResponseV07);
		updateNexoTxn(nexoTxn, responseXml);
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;
	}

	private void getCardData(CardPaymentEnvironment68 cardPaymentEnvironment68, PaymentCard28 paymentCard28,
			ContentInformationType17 contentInformationType17) {
		if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp() != null) {
			if ((cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name())
					.equals(ContentType2Code.DATA.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.DATA);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.DGST.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.DGST);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.EVLP.name())) {
				contentInformationType17.setCnttTp(ContentType2Code.EVLP);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.SIGN.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.SIGN);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name()
					.equals(ContentType2Code.AUTH.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.AUTH);
			}
		}
		EnvelopedData5 envlpdData = new EnvelopedData5();
		envlpdData.setVrsn(cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getVrsn());
		OriginatorInformation1 orgtrInf = new OriginatorInformation1();
		if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getOrgtrInf() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getOrgtrInf()
						.getCert() != null) {
			orgtrInf.getCert().add(cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getOrgtrInf()
					.getCert().get(0));
			envlpdData.setOrgtrInf(orgtrInf);
		}
		List<Recipient6Choice> recipient6ChoiceList = new ArrayList<>();
		Recipient6Choice recipient6Choice = new Recipient6Choice();
		KeyTransport5 keyTrnsprt = new KeyTransport5();
		Recipient5Choice rcptId = new Recipient5Choice();
		IssuerAndSerialNumber1 issrAndSrlNb = new IssuerAndSerialNumber1();
		CertificateIssuer1 issr = new CertificateIssuer1();
		List<RelativeDistinguishedName1> rltvDstngshdNmList = new ArrayList<>();
		RelativeDistinguishedName1 relativeDistinguishedName1 = new RelativeDistinguishedName1();
		if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId().getIssrAndSrlNb() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm() != null) {
			if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm().get(0).getAttrTp()
					.name().equals(AttributeType1Code.CATT.value())) {
				relativeDistinguishedName1.setAttrTp(AttributeType1Code.CATT);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm().get(0).getAttrTp()
					.name().equals(AttributeType1Code.CNAT.value())) {
				relativeDistinguishedName1.setAttrTp(AttributeType1Code.CNAT);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm().get(0).getAttrTp()
					.name().equals(AttributeType1Code.LATT.value())) {
				relativeDistinguishedName1.setAttrTp(AttributeType1Code.LATT);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm().get(0).getAttrTp()
					.name().equals(AttributeType1Code.OATT.value())) {
				relativeDistinguishedName1.setAttrTp(AttributeType1Code.OATT);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm().get(0).getAttrTp()
					.name().equals(AttributeType1Code.OUAT.value())) {
				relativeDistinguishedName1.setAttrTp(AttributeType1Code.OUAT);
			}
		}
		relativeDistinguishedName1
				.setAttrVal(cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt() != null
						&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
								.getKeyTrnsprt() != null
						&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
								.getKeyTrnsprt().getRcptId() != null
						&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
								.getKeyTrnsprt().getRcptId().getIssrAndSrlNb() != null
						&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
								.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr() != null
						&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
								.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm() != null
						&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
								.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getIssr().getRltvDstngshdNm().get(0)
								.getAttrVal() != null
										? cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData()
												.getRcpt().get(0).getKeyTrnsprt().getRcptId().getIssrAndSrlNb()
												.getIssr().getRltvDstngshdNm().get(0).getAttrVal()
										: null);
		rltvDstngshdNmList.add(relativeDistinguishedName1);
		issr.getRltvDstngshdNm().add(relativeDistinguishedName1);
		issrAndSrlNb.setIssr(issr);
		issrAndSrlNb.setSrlNb(cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId().getIssrAndSrlNb() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getSrlNb() != null
								? cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt()
										.get(0).getKeyTrnsprt().getRcptId().getIssrAndSrlNb().getSrlNb()
								: null);
		rcptId.setIssrAndSrlNb(issrAndSrlNb);
		keyTrnsprt.setRcptId(rcptId);
		AlgorithmIdentification19 keyNcrptnAlgo = new AlgorithmIdentification19();
		if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getKeyNcrptnAlgo() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getKeyNcrptnAlgo().getAlgo() != null) {
			if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm7Code.ERSA.value())) {
				keyNcrptnAlgo.setAlgo(Algorithm7Code.ERSA);
			} else if (cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
					.getKeyTrnsprt().getKeyNcrptnAlgo().getAlgo().name().equals(Algorithm7Code.RSAO.value())) {
				keyNcrptnAlgo.setAlgo(Algorithm7Code.RSAO);
			}

		}
		keyTrnsprt.setKeyNcrptnAlgo(keyNcrptnAlgo);
		keyTrnsprt.setNcrptdKey(cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt().get(0)
						.getKeyTrnsprt().getNcrptdKey() != null
								? cardPaymentEnvironment68.getCard().getPrtctdCardData().getEnvlpdData().getRcpt()
										.get(0).getKeyTrnsprt().getNcrptdKey()
								: null);
		recipient6Choice.setKeyTrnsprt(keyTrnsprt);
		recipient6ChoiceList.add(recipient6Choice);
		envlpdData.getRcpt().add(recipient6Choice);
		contentInformationType17.setEnvlpdData(envlpdData);
		paymentCard28.setPrtctdCardData(contentInformationType17);
	}

	private CancellationAdviceResponse getCancellationAdviceResponse(RejectionMessageRequest rejectionMessageRequest,
			String merchId, String reqTxRef, BigDecimal reqTotalAmt,
			CardPaymentTransactionAdviceResponse6 cardPaymentTxn, Header36 reqHeader,
			CancellationAdviceResponse transactionResponse) throws javax.xml.parsers.FactoryConfigurationError,
			JAXBException, XMLStreamException, FactoryConfigurationError, RejectionException {
		if (Constants.PG_MOCK != null && Constants.PG_MOCK.equals("false")) {
			CancellationAdviceRequest request = new CancellationAdviceRequest();
			request.setMerchantCode(merchId);
			request.setTxnRefNumber(reqTxRef);
			request.setTxnAmount(reqTotalAmt.longValue());
			transactionResponse = JsonUtil.postServiceRequest(request, CancellationAdviceResponse.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"getCancellationAdviceStatus");
			if (transactionResponse.getRejectionCode() != null) {
				throw new RejectionException(transactionResponse.getRejectionCode(),
						transactionResponse.getRejectionReason()); 
			}
			if (!ObjectUtils.isEmpty(transactionResponse) && !ObjectUtils.isEmpty(transactionResponse.getErrorMessage())
					&& transactionResponse.getErrorMessage()
							.equals("Transaction is in Pending Status,You can do either capture or void")) {
				cardPaymentTxn.setRspn(Response4Code.APPR);
			} else if (!ObjectUtils.isEmpty(transactionResponse)
					&& !ObjectUtils.isEmpty(transactionResponse.getErrorMessage()) && transactionResponse
							.getErrorMessage().equals("Transaction is successfully excecuted, you can do refund")) {
				cardPaymentTxn.setRspn(Response4Code.APPR);
			} else if (!ObjectUtils.isEmpty(transactionResponse)
					&& !ObjectUtils.isEmpty(transactionResponse.getErrorCode())
					&& transactionResponse.getErrorCode().equals("-99")) {
				return transactionResponse;
			} else if (!ObjectUtils.isEmpty(transactionResponse)
					&& !ObjectUtils.isEmpty(transactionResponse.getErrorCode())
					&& transactionResponse.getErrorCode().equals("00")) {
				cardPaymentTxn.setRspn(Response4Code.APPR);
			} else {
				cardPaymentTxn.setRspn(Response4Code.DECL);
			}
		} else {
			cardPaymentTxn.setRspn(Response4Code.APPR);
		}
		return transactionResponse;
	}

	private void updateNexoTxn(NexoTxn nexoTxn, String responseXml) {
		if(nexoTxn != null) {
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
	}

	private void checkForContentType(CardPaymentEnvironment68 cardPaymentEnvironment68,
			ContentInformationType17 contentInformationType17) {
		if (cardPaymentEnvironment68.getCard() != null && cardPaymentEnvironment68.getCard().getPrtctdCardData() != null
				&& cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp() != null) {
			if((cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name()).equals(ContentType2Code.DATA.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.DATA);
			} else if(cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name().equals(ContentType2Code.DGST.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.DGST);
			} else if(cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name().equals(ContentType2Code.EVLP.name())) {
				contentInformationType17.setCnttTp(ContentType2Code.EVLP);
			} else if(cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name().equals(ContentType2Code.SIGN.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.SIGN);
			} else if(cardPaymentEnvironment68.getCard().getPrtctdCardData().getCnttTp().name().equals(ContentType2Code.AUTH.value())) {
				contentInformationType17.setCnttTp(ContentType2Code.AUTH);
			}
		}
	}

	private void validateContextAndSaleContext(Document authReqDocument,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		if (authReqDocument.getAccptrCxlAdvc().getCxlAdvc().getCntxt() != null) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			validateContext(authReqDocument,rejectionMessageRequest,xml);
		}
	}

	private void validateCapabilities(Document authReqDocument, RejectionMessageRequest rejectionMessageRequest,String xml) {
		if (authReqDocument.getAccptrCxlAdvc().getCxlAdvc().getEnvt().getPOI().getCpblties() != null) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			validateCapablties(authReqDocument,rejectionMessageRequest,xml);
		}
	}

	private com.girmiti.nexo.acceptorcancellationadvice.PlainCardData15 getPlainCard(
			CardPaymentEnvironment68 cardPaymentEnvironment68) {
		if (!ObjectUtils.isEmpty(cardPaymentEnvironment68)
				&& !ObjectUtils.isEmpty(cardPaymentEnvironment68.getCard())) {
			return cardPaymentEnvironment68.getCard().getPlainCardData();
		}
		return null;
	}

	private void setMerchantId(Organisation32 mer, CardPaymentEnvironment69 responseEnv) {
		com.girmiti.nexo.acceptorcancellationadviceresponse.GenericIdentification32 responseMerId;
		if (!ObjectUtils.isEmpty(mer) && !ObjectUtils.isEmpty(mer.getId())) {
			responseMerId = new GenericIdentification32();
			responseMerId.setId(mer.getId().getId());
			responseEnv.setMrchntId(responseMerId);
		}
	}

	private void setTxnRef(com.girmiti.nexo.acceptorcancellationadviceresponse.TransactionIdentifier1 transactionIdentifier,
			CancellationAdviceResponse transactionResponse) {
		if(!ObjectUtils.isEmpty(transactionResponse)) {
			transactionIdentifier.setTxRef(transactionResponse.getTxnRefNumber());
		}
	}

	private void setCardDetails(com.girmiti.nexo.acceptorcancellationadvice.PlainCardData15 plainCardData15,
			PaymentCard28 paymentCard28, PlainCardData15 cardData15) {
		if (!ObjectUtils.isEmpty(plainCardData15)) {
			cardData15.setPAN(plainCardData15.getPAN());
			cardData15.setXpryDt(plainCardData15.getXpryDt());
			if(plainCardData15.getTrck1() != null) {
			  cardData15.setTrck1(plainCardData15.getTrck1());
			}
			paymentCard28.setPlainCardData(cardData15);
		}
	}

	private void setPoiId(com.girmiti.nexo.acceptorcancellationadvice.PointOfInteraction8 requestPoi,
			com.girmiti.nexo.acceptorcancellationadviceresponse.CardPaymentEnvironment69 responseEnv,
			com.girmiti.nexo.acceptorcancellationadviceresponse.GenericIdentification32 responsePOIId) {
		if (!ObjectUtils.isEmpty(requestPoi) && !ObjectUtils.isEmpty(requestPoi.getId())) {
			responsePOIId.setId(requestPoi.getId().getId());
			responseEnv.setPOIId(responsePOIId);
		}
	}

	private void setAcquirerId(CardPaymentEnvironment68 cardPaymentEnvironment68,
			com.girmiti.nexo.acceptorcancellationadviceresponse.CardPaymentEnvironment69 responseEnv,
			GenericIdentification53 genericIdentification53) {
		if (!ObjectUtils.isEmpty(cardPaymentEnvironment68.getAcqrr())
				&& !ObjectUtils.isEmpty(cardPaymentEnvironment68.getAcqrr().getId())) {
			genericIdentification53.setId(cardPaymentEnvironment68.getAcqrr().getId().getId());
			responseEnv.setAcqrrId(genericIdentification53);
		}
	}

	private String getAcquirerId(com.girmiti.nexo.acceptorcancellationadvice.Acquirer4 acqrr) {
		if(!ObjectUtils.isEmpty(acqrr) && !ObjectUtils.isEmpty(acqrr.getId()) && !ObjectUtils.isEmpty(acqrr.getId().getId())) {
			return acqrr.getId().getId();
		}
		return null;
	}

	private String validateAcquirerData(Acquirer4 acqrr,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (!ObjectUtils.isEmpty(acqrr) && ObjectUtils.isEmpty(acqrr.getParamsVrsn())) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.ACQUIRER_DATA_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating Acquirer Data : ", e);
		}
		return null;
	}

	private String validateContext(Document authReqDocument, RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (checkForContext(authReqDocument.getAccptrCxlAdvc().getCxlAdvc().getCntxt()) || (checkForSaleContext(
					authReqDocument.getAccptrCxlAdvc().getCxlAdvc().getCntxt().getSaleCntxt()))) {
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.CONTEXT_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating Context Data : ", e);
		}
		return null;
	}

	private String validateCapablties(Document authReqDocument, RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
		if (checkForCapabilities(
				authReqDocument.getAccptrCxlAdvc().getCxlAdvc().getEnvt().getPOI().getCpblties())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.POI_CAPABILITIES_ERROR,xml);
		}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating Capablties Data : ", e);
		}
		
		return null;
	}

	private String validateMessageFuctionData(Document authReqDocument,
			RejectionMessageRequest rejectionMessageRequest,String xml) {
		try {
			if (!authReqDocument.getAccptrCxlAdvc().getHdr().getMsgFctn()
					.equals(com.girmiti.nexo.acceptorcancellationadvice.MessageFunction14Code.CCAV)) {
				rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
				rejectionMessageRequest.setProtocolVersion(null);
				return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
						RejectReason1Code.PARS, Constants.MESSAGE_FUNCTION_ERROR,xml);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured While Validating MessageFuction Data : ", e);
		}
		return null;
	}

	private boolean checkForSaleContext(SaleContext3 saleCntxt) {
		return !(saleCntxt.getAddtlSaleData() != null || saleCntxt.getAllwdNtryMd() != null || saleCntxt.getCshrId() != null
				|| saleCntxt.getCshrLang() != null || saleCntxt.getDlvryNoteNb() != null
				|| saleCntxt.getInvcNb() != null || saleCntxt.getPurchsOrdrNb() != null
				|| saleCntxt.getRmngAmt() != null || saleCntxt.getSaleId() != null
				|| saleCntxt.getSaleRcncltnId() != null || saleCntxt.getSaleTknScp() != null
				|| saleCntxt.getSaleRefNb() != null || saleCntxt.getShftNb() != null
				|| saleCntxt.getSpnsrdMrchnt() != null);
	}

	private boolean checkForContext(CardPaymentContext27 cardPaymentContext27) {
		return !(cardPaymentContext27.getDrctDbtCntxt() != null || cardPaymentContext27.getPmtCntxt() != null
				|| cardPaymentContext27.getSaleCntxt() != null);
	}

	private Boolean checkForCapabilities(PointOfInteractionCapabilities6 pointOfInteractionCapabilities6) {
		return !(pointOfInteractionCapabilities6.getApprvlCdLngth() != null
				|| pointOfInteractionCapabilities6.getCardRdngCpblties() != null
				|| pointOfInteractionCapabilities6.getCrdhldrVrfctnCpblties() != null
				|| pointOfInteractionCapabilities6.getMsgCpblties() != null
				|| pointOfInteractionCapabilities6.getMxScrptLngth() != null
				|| pointOfInteractionCapabilities6.getOnLineCpblties() != null
				|| pointOfInteractionCapabilities6.getPINLngthCpblties() != null);
	}
}
