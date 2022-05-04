package com.girmiti.nexo.processor.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorbatchtransfer.CardPaymentBatchTransfer7;
import com.girmiti.nexo.acceptorbatchtransfer.CardPaymentDataSet22;
import com.girmiti.nexo.acceptorbatchtransfer.CardPaymentDataSetTransaction7Choice;
import com.girmiti.nexo.acceptorbatchtransfer.CardPaymentServiceType12Code;
import com.girmiti.nexo.acceptorbatchtransferresponse.AcceptorBatchTransferResponseV07;
import com.girmiti.nexo.acceptorbatchtransferresponse.CardPaymentBatchTransferResponse6;
import com.girmiti.nexo.acceptorbatchtransferresponse.CardPaymentDataSet21;
import com.girmiti.nexo.acceptorbatchtransferresponse.DataSetIdentification5;
import com.girmiti.nexo.acceptorbatchtransferresponse.Response4Code;
import com.girmiti.nexo.acceptorbatchtransferresponse.ResponseType5;
import com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7;
import com.girmiti.nexo.acceptorbatchtransferresponse.TypeTransactionTotals2Code;
import com.girmiti.nexo.acceptorcompletionadvice.OnLineCapability1Code;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.enums.EntryModeEnum;
import com.girmiti.nexo.acquirer.enums.MethodOfPaymentTypeEnum;
import com.girmiti.nexo.acquirer.enums.TransactionStatus;
import com.girmiti.nexo.acquirer.enums.TransactionType;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.CardData;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataRequest;
import com.girmiti.nexo.acquirer.pojo.NexoAcquirerDataResponse;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.processor.ITransactionProcessor;
import com.girmiti.nexo.util.AcceptorRejectionMessageUtil;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.CountryValidationUtil;
import com.girmiti.nexo.util.CurrencyValidationUtil;
import com.girmiti.nexo.util.JaxbHelper;
import com.girmiti.nexo.util.JsonUtil;

public class AcceptorBatchTransferProcessor implements ITransactionProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptorBatchTransferProcessor.class);
	
	public String process(String acceptorBatchTransferxml) throws Exception {
		com.girmiti.nexo.acceptorbatchtransfer.Document acceptorBatchTransDocument = null;
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			rejectionMessageRequest.setTxnDate(now);
			String xmlRequest = acceptorBatchTransferxml = acceptorBatchTransferxml.replaceAll("True", "true");
			xmlRequest = xmlRequest.replaceAll("False", "false");
			JaxbHelper jaxbHelper = new JaxbHelper();
			acceptorBatchTransDocument = (com.girmiti.nexo.acceptorbatchtransfer.Document) jaxbHelper.unmarshall(
					com.girmiti.nexo.acceptorbatchtransfer.Document.class, xmlRequest,
					Constants.CAAA_011_001_08);
		} catch (Exception e) {
			LOGGER.error("Exception Occured while Unmarshalling Requset", e);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, e.getCause().toString(),acceptorBatchTransferxml);
		}
		return processAuth(acceptorBatchTransDocument, acceptorBatchTransferxml);
	}
	
	private String processAuth(com.girmiti.nexo.acceptorbatchtransfer.Document acceptorBatchTransferDocument,
			String acceptorBatchTransferxml)
			throws FactoryConfigurationError, javax.xml.parsers.FactoryConfigurationError, Exception {
		AcceptorBatchTransferResponseV07 response = new AcceptorBatchTransferResponseV07();
		CardPaymentBatchTransferResponse6 cardPaymentBatchTransferResponse6 = new CardPaymentBatchTransferResponse6();
		List<com.girmiti.nexo.acceptorbatchtransfer.TransactionTotals7> transactionTotals = new ArrayList<>();
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		
		CardPaymentBatchTransfer7 cardPaymentBatchTransfer7 = acceptorBatchTransferDocument.getAccptrBtchTrf()
				.getBtchTrf();
		transactionTotals=cardPaymentBatchTransfer7.getTxTtls();
		com.girmiti.nexo.acceptorbatchtransfer.Header25 reqHeader = acceptorBatchTransferDocument.getAccptrBtchTrf()
				.getHdr();
		com.girmiti.nexo.acceptorbatchtransferresponse.Header25 resHeader = new com.girmiti.nexo.acceptorbatchtransferresponse.Header25();
		resHeader.setDwnldTrf(reqHeader.isDwnldTrf());
		resHeader.setFrmtVrsn(reqHeader.getFrmtVrsn());
		resHeader.setXchgId(reqHeader.getXchgId());
		resHeader.setCreDtTm(reqHeader.getCreDtTm());
		rejectionMessageRequest.setTxnDate(reqHeader.getCreDtTm());

		int milliSecond = reqHeader.getCreDtTm().getMillisecond();
		if (milliSecond < 0) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATE_TIME_FORMAT_ERROR,acceptorBatchTransferxml);
		}
		
		com.girmiti.nexo.acceptorbatchtransferresponse.GenericIdentification53 initigParty = new com.girmiti.nexo.acceptorbatchtransferresponse.GenericIdentification53();
		initigParty.setId(reqHeader.getInitgPty().getId());
		resHeader.setInitgPty(initigParty);

		if (!acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getTxTtls().isEmpty() && CurrencyValidationUtil.isCurrencyNotNull(
				acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getTxTtls().get(0).getCcy()) && CurrencyValidationUtil.validateCurrency(
						acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getTxTtls().get(0).getCcy())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.CURRENCY_ERROR,acceptorBatchTransferxml);

		}
		List<CardPaymentDataSet22> cardPaymentDataSet22s = cardPaymentBatchTransfer7.getDataSet();
		
		if (!cardPaymentDataSet22s.isEmpty() && !cardPaymentDataSet22s.get(0).getTx().isEmpty()
				&& !ObjectUtils.isEmpty(cardPaymentBatchTransfer7.getDataSet().get(0).getTx().get(0).getCmpltn())
				&& CountryValidationUtil.isCountryNotNull(cardPaymentBatchTransfer7.getDataSet().get(0).getTx().get(0)
						.getCmpltn().getEnvt().getCard().getCardCtryCd())
				&& CountryValidationUtil.validateCountry(cardPaymentBatchTransfer7.getDataSet().get(0).getTx().get(0)
						.getCmpltn().getEnvt().getCard().getCardCtryCd())) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.COUNTRY_ERROR,acceptorBatchTransferxml);

		}
		
		////POI must either be present in CommonData or in Transaction
		if (!acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getDataSet().isEmpty()
				&& !ObjectUtils.isEmpty(cardPaymentBatchTransfer7.getDataSet().get(0).getTx().get(0).getCmpltn())
				&& CountryValidationUtil.isCountryNotNull(cardPaymentBatchTransfer7.getDataSet().get(0).getTx().get(0)
						.getCmpltn().getEnvt().getCard().getCardCtryCd())
				&& CountryValidationUtil.validateCountry(cardPaymentBatchTransfer7.getDataSet().get(0).getTx().get(0)
						.getCmpltn().getEnvt().getCard().getCardCtryCd())) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.COUNTRY_ERROR,acceptorBatchTransferxml);
		}
		
		if (!acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getDataSet().isEmpty() && checkForContext(
				acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getDataSet().get(0).getTx().get(0))) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, Constants.DATASET_ERROR,acceptorBatchTransferxml);
		}
		//MerchantCategoryCode must be present either in CommonData or in Transaction 
		checkforDateSet(acceptorBatchTransferDocument, rejectionMessageRequest);
		
		CardPaymentDataSet21 cardPaymentDataSet21 = new CardPaymentDataSet21();
		DataSetIdentification5 dataSetIdentification5 = new DataSetIdentification5();
		dataSetIdentification5.setCreDtTm(reqHeader.getCreDtTm());
		dataSetIdentification5.setNm("malisa");
		String dateSetIdTp=cardPaymentBatchTransfer7.getDataSet().get(0).getDataSetId().getTp().toString();
		chaekForDataSetTp(dataSetIdentification5, dateSetIdTp);
		ResponseType5 responseType5 = new ResponseType5();
		responseType5.setAddtlRspnInf("Batch Transfer");
		responseType5.setRspn(Response4Code.APPR);
		responseType5.setRspnRsn("BATCH");
		cardPaymentDataSet21.setDataSetId(dataSetIdentification5);
		cardPaymentDataSet21.setDataSetRslt(responseType5);
		cardPaymentDataSet21.setDataSetInitr(initigParty);
		cardPaymentBatchTransferResponse6.getDataSet().add(cardPaymentDataSet21);
		NexoTxn nexoTxn = new NexoTxn();
		BulkTransactionRequest bulkTransactionRequest = new BulkTransactionRequest();
		List<TransactionRequest> transactionRequestsList = new ArrayList<>();
		doBatchTxn(cardPaymentBatchTransfer7, transactionRequestsList, cardPaymentBatchTransferResponse6);
		
		bulkTransactionRequest.setTransactionRequest(transactionRequestsList);
		bulkTransactionRequest.setTransactionTotals(transactionTotals);
		
		// for offline tansactions
		NexoAcquirerDataRequest acquirerDataRequest = new NexoAcquirerDataRequest();
		acquirerDataRequest.setCaptureStatus(OnLineCapability1Code.OFLN.toString());
		NexoAcquirerDataResponse acquirerDataResponse = JsonUtil.postServiceRequest(acquirerDataRequest,
				NexoAcquirerDataResponse.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
				"findByCaptureStatus");
		if (acquirerDataResponse.getNexoTxns() != null) {
			for (NexoTxn nexoTxns : acquirerDataResponse.getNexoTxns()) {
				if (nexoTxns.getRequestData() != null) {
					nexoTxns.setRequestData(
							nexoTxns.getRequestData().replace("<OnLineCpblties>OFLN</OnLineCpblties>", ""));
					AuthTransactionProcessor authTransactionProcessor = new AuthTransactionProcessor();
					String responses = authTransactionProcessor.process(nexoTxns.getRequestData());
					nexoTxns.setCaptureStatus(OnLineCapability1Code.ONLN.toString());
					updateNexoTxn(nexoTxns, responses);
				}
			}
		}
			
		BulkTransactionResponse bulkTransactionResponse = JsonUtil.postServiceRequest(bulkTransactionRequest, BulkTransactionResponse.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
				"batchProcessTransaction");
		int transLen = (bulkTransactionResponse != null && bulkTransactionResponse.getTransactionTotals() != null
				? bulkTransactionResponse.getTransactionTotals().size()
				: 0);
		if (bulkTransactionResponse != null) {
			getTransactionTtls(bulkTransactionResponse.getTransactionTotals(), cardPaymentBatchTransferResponse6,
					cardPaymentDataSet21, transLen);
		}
		response.setBtchTrfRspn(cardPaymentBatchTransferResponse6);
		String responseXml = "ns2:";
		if (bulkTransactionResponse != null && !ObjectUtils.isEmpty(bulkTransactionResponse.getTransactionResponse())) {
			responseXml = getResponseXml(acceptorBatchTransferxml, response, resHeader, bulkTransactionResponse,
					responseXml);
		} else if (bulkTransactionResponse != null && !ObjectUtils.isEmpty(bulkTransactionResponse)
				&& !ObjectUtils.isEmpty(bulkTransactionResponse.getErrorCode())
				&& bulkTransactionResponse.getErrorCode().equals("TXN_0030")) {
			return AcceptorRejectionMessageUtil.getClientAcceptorRejectionMessage(rejectionMessageRequest,
					RejectReason1Code.PARS, bulkTransactionResponse.getErrorMessage(),acceptorBatchTransferxml);
		} else {
			response.setHdr(resHeader);
			com.girmiti.nexo.acceptorbatchtransferresponse.Document respDocument = new com.girmiti.nexo.acceptorbatchtransferresponse.Document();
			respDocument.setAccptrBtchTrfRspn(response);
			responseXml = JaxbHelper.marshall(respDocument, response);
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
		responseXml = responseXml.replaceAll("ns2:", "");
		return responseXml;
	}

	private String getResponseXml(String acceptorBatchTransferxml, AcceptorBatchTransferResponseV07 response,
			com.girmiti.nexo.acceptorbatchtransferresponse.Header25 resHeader,
			BulkTransactionResponse bulkTransactionResponse, String responseXml)
			throws JAXBException, XMLStreamException, FactoryConfigurationError {
		for (int i = 0; i < bulkTransactionResponse.getTransactionResponse().size(); i++) {
			responseXml = checkForBatchResponse(acceptorBatchTransferxml, response, resHeader,
					bulkTransactionResponse, i);
		}
		return responseXml;
	}

	private void getTransactionTtls(List<TransactionTotals7> translist,CardPaymentBatchTransferResponse6 cardPaymentBatchTransferResponse6, CardPaymentDataSet21 cardPaymentDataSet21,
			int transLen) {
		for (int i = 0;i<transLen; i++) {
			cardPaymentDataSet21.getTxTtls().add(translist.get(i));
			cardPaymentBatchTransferResponse6.getTxTtls().add(translist.get(i));
		}
	}

	private void chaekForDataSetTp(DataSetIdentification5 dataSetIdentification5, String dateSetIdTp) {
		if (dateSetIdTp.equals("TXCP")) {
			dataSetIdentification5.setTp(com.girmiti.nexo.acceptorbatchtransferresponse.DataSetCategory8Code.AKCP);
		} else {
			dataSetIdentification5.setTp(com.girmiti.nexo.acceptorbatchtransferresponse.DataSetCategory8Code.DLGT);
			dataSetIdentification5.setVrsn("v0043");
		}
	}

	private void checkforDateSet(com.girmiti.nexo.acceptorbatchtransfer.Document acceptorBatchTransferDocument,
			RejectionMessageRequest rejectionMessageRequest) {
		if (!acceptorBatchTransferDocument.getAccptrBtchTrf().getBtchTrf().getDataSet().isEmpty()) {
			rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCQ);
			rejectionMessageRequest.setProtocolVersion(null);

		}
	}

	private String checkForBatchResponse(String acceptorBatchTransferxml, AcceptorBatchTransferResponseV07 response,
			com.girmiti.nexo.acceptorbatchtransferresponse.Header25 resHeader,
			BulkTransactionResponse bulkTransactionResponse, int i)
			throws JAXBException, XMLStreamException, FactoryConfigurationError {
		NexoTxn nexoTxn;
		String responseXml;
	     ResponseType5 responseType5 = new ResponseType5();
		nexoTxn = saveTransactionRequest(acceptorBatchTransferxml, resHeader);
		if(bulkTransactionResponse != null && bulkTransactionResponse.getTransactionResponse().get(i)
				.getErrorCode() != null) {
			if (bulkTransactionResponse.getTransactionResponse().get(i)
					.getErrorCode().equalsIgnoreCase("00")) {
				nexoTxn.setCgTxnRef(bulkTransactionResponse.getTransactionResponse().get(i).getCgRefNumber());
				nexoTxn.setPgTxnRef(bulkTransactionResponse.getTransactionResponse().get(i).getTxnRefNumber());
				if (bulkTransactionResponse.getTransactionResponse().get(i).getTransactionType()
						.equalsIgnoreCase("auth")) {
					nexoTxn.setCaptureStatus("false");
				} else if (bulkTransactionResponse.getTransactionResponse().get(i).getTransactionType()
						.equalsIgnoreCase("sale")) {
					nexoTxn.setCaptureStatus("true");
				} else if (bulkTransactionResponse.getTransactionResponse().get(i).getTransactionType()
						.equalsIgnoreCase("capture")) {
					nexoTxn.setCaptureStatus("true");
				} else if (bulkTransactionResponse.getTransactionResponse().get(i).getTransactionType()
						.equalsIgnoreCase("refund")) {
					nexoTxn.setCaptureStatus("refunded");
				} else if (bulkTransactionResponse.getTransactionResponse().get(i).getTransactionType()
						.equalsIgnoreCase("void")) {
					nexoTxn.setCaptureStatus("voided");
				}
			}
		} else {

				if (bulkTransactionResponse.getTransactionResponse().get(i).getApprovalStatus()
						.equalsIgnoreCase(TransactionStatus.PARTIAL.toString())) {
					responseType5.setRspn(Response4Code.PART);
				} else if (bulkTransactionResponse.getTransactionResponse().get(i).getApprovalStatus()
						.equalsIgnoreCase(TransactionStatus.DECLINED.toString())) {
					responseType5.setRspn(Response4Code.DECL);
				}else if(bulkTransactionResponse.getTransactionResponse().get(i).getApprovalStatus()
						.equalsIgnoreCase(TransactionStatus.APPROVED.toString())) {
					responseType5.setRspn(Response4Code.APPR);
				}
			}
		response.setHdr(resHeader);
		response.getBtchTrfRspn().getDataSet().get(0).setDataSetRslt(responseType5);
		com.girmiti.nexo.acceptorbatchtransferresponse.Document respDocument = new com.girmiti.nexo.acceptorbatchtransferresponse.Document();
		respDocument.setAccptrBtchTrfRspn(response);
		responseXml = JaxbHelper.marshall(respDocument, response);
		nexoTxn.setResponseData(responseXml);
		JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
				Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
				"updateTransactionResponse");
		return responseXml;
	}

	private void doBatchTxn(CardPaymentBatchTransfer7 cardPaymentBatchTransfer7,
			List<TransactionRequest> transactionRequestsList, CardPaymentBatchTransferResponse6 cardPaymentBatchTransferResponse6) {
		if(!cardPaymentBatchTransfer7.getDataSet().isEmpty()) {
				for(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22 : cardPaymentBatchTransfer7.getDataSet().get(0).getTx()) {
					TransactionTotals7 transactionTotals7 = new TransactionTotals7();
					BigDecimal reqTotalAmt = null;
					TransactionRequest transactionRequest = new TransactionRequest();
					if(cardPaymentDataSet22.getCmpltn() != null) {
						reqTotalAmt = cardPaymentDataSet22.getCmpltn().getTx().getTxDtls().getTtlAmt();
						transactionRequest.setCurrencyCode(cardPaymentDataSet22.getCmpltn().getTx().getTxDtls().getCcy());
						transactionRequest.setTxnRefNumber(cardPaymentDataSet22.getCmpltn().getTx().getTxId() != null
								? cardPaymentDataSet22.getCmpltn().getTx().getTxId().getTxRef()
								: null);
					} else if (cardPaymentDataSet22.getCxl() != null) {
						reqTotalAmt = cardPaymentDataSet22.getCxl().getTx().getTxDtls().getTtlAmt();
							transactionRequest.setCurrencyCode(
									cardPaymentDataSet22.getCxl() != null && cardPaymentDataSet22.getCxl().getTx() != null
											&& cardPaymentDataSet22.getCxl().getTx().getTxDtls() != null
													? cardPaymentDataSet22.getCxl().getTx().getTxDtls().getCcy()
													: null);
							transactionRequest.setTxnRefNumber(
									cardPaymentDataSet22.getCxl() != null && cardPaymentDataSet22.getCxl().getTx() != null
											&& cardPaymentDataSet22.getCxl().getTx().getTxId() != null
													? cardPaymentDataSet22.getCxl().getTx().getTxId().getTxRef()
													: null);
					}
					NexoTxn txn = new NexoTxn();
					txn.setPgTxnRef(transactionRequest.getTxnRefNumber());
					txn = JsonUtil.postServiceRequest(txn, NexoTxn.class,
							Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION, "findByPgTxRef");
					transactionRequest.setCgRefNumber(txn != null ? txn.getCgTxnRef() : null);
					CardData cardData = new CardData();
					transactionRequest.setMerchantAmount(1000l);
					checkForTxDetails(cardPaymentDataSet22, transactionRequest, cardData);
					cardData.setCardType(MethodOfPaymentTypeEnum.IP);
					transactionRequest.setTotalTxnAmount(reqTotalAmt!= null ? Long.valueOf(reqTotalAmt.toString()) : null);
					transactionRequest.setTxnAmount(reqTotalAmt!= null ? Long.valueOf(reqTotalAmt.toString()) : null);
					transactionRequest.setCardData(cardData);
					transactionRequest.setTimeZoneOffset("GMT+0530");
					transactionRequest.setTimeZoneRegion("Asia/Kolkata");
					transactionRequest.setUserName("acqadminuser");
					transactionRequest.setOriginChannel("ADMIN_WEB");
					transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
					transactionRequest.setInvoiceNumber(String.valueOf(ThreadLocalRandom.current().nextInt()).replace("-", ""));
					transactionRequest.setOrderId(String.valueOf(ThreadLocalRandom.current().nextInt()).replace("-", ""));
					transactionTotals7.setCcy(transactionRequest.getCurrencyCode());
					transactionTotals7.setCmltvAmt(BigDecimal.valueOf(reqTotalAmt!= null ? Long.valueOf(reqTotalAmt.toString()) : null));
					transactionTotals7.setTp(TypeTransactionTotals2Code.CRDR);
					transactionTotals7.setTtlNb(BigDecimal.ONE);
					cardPaymentBatchTransferResponse6.getTxTtls().add(transactionTotals7);
					transactionRequestsList.add(transactionRequest);
				}
			}
	}

	private void checkForTxDetails(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22,
			TransactionRequest transactionRequest, CardData cardData) {
		if(cardPaymentDataSet22.getCmpltn() != null) {
			checkForCmpl(cardPaymentDataSet22, transactionRequest, cardData);
		} else {
			checkForCxl(cardPaymentDataSet22, transactionRequest, cardData);
		}
	}

	private void checkForCxl(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22,
			TransactionRequest transactionRequest, CardData cardData) {
		String expiryDate = cardPaymentDataSet22.getCxl().getEnvt().getCard().getPlainCardData().getXpryDt();
		expiryDate = expiryDate.replace("-", "");
		expiryDate = expiryDate.substring(2, 6);
		cardData.setCardNumber(cardPaymentDataSet22.getCxl().getEnvt().getCard().getPlainCardData().getPAN());
		cardData.setExpDate(expiryDate);
		cardData.setCvv(cardPaymentDataSet22.getCxl().getEnvt().getCard().getPlainCardData().getSvcCd());
		
		if((cardPaymentDataSet22.getCxl().getTx().getOrgnlTx().getTxTp()).equals(CardPaymentServiceType12Code.CRDP)) {
			transactionRequest.setTransactionType(TransactionType.CAPTURE);
		}else if((cardPaymentDataSet22.getCxl().getTx().getOrgnlTx().getTxTp()).equals(CardPaymentServiceType12Code.RFND)) {
			transactionRequest.setTransactionType(TransactionType.REFUND);
		} else {
			transactionRequest.setTransactionType(TransactionType.CAPTURE);
		}
		transactionRequest.setMerchantCode(cardPaymentDataSet22.getCxl().getEnvt().getMrchnt().getId().getId());
		transactionRequest.setTerminalId(cardPaymentDataSet22.getCxl().getEnvt().getPOI().getId().getId());
		transactionRequest.setRegisterNumber(cardPaymentDataSet22.getCxl().getEnvt().getPOI().getId().getId());
	}

	private void checkForCmpl(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22,
			TransactionRequest transactionRequest, CardData cardData) {
		String expiryDate = getExpiryDate(cardPaymentDataSet22);
		String newExpirydate = null;
		if(expiryDate != null && expiryDate.contains("-")) {
			newExpirydate  = expiryDate.replace("-", "");
			newExpirydate = newExpirydate.substring(2, 6);
			} else {
				newExpirydate = expiryDate;
			}
		cardData.setCardNumber(getPan(cardPaymentDataSet22));
		cardData.setExpDate(newExpirydate);
		cardData.setCvv(getCvv(cardPaymentDataSet22));
		
		if (!ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getTx())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getTx().getTxTp())
				&& (cardPaymentDataSet22.getCmpltn().getTx().getTxTp())
						.equals(CardPaymentServiceType12Code.CRDP)) {
			transactionRequest.setTransactionType(TransactionType.CAPTURE);
		} else if (!ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getTx())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getTx().getTxTp())
				&& (cardPaymentDataSet22.getCmpltn().getTx().getTxTp())
						.equals(CardPaymentServiceType12Code.RFND)) {
			transactionRequest.setTransactionType(TransactionType.REFUND);
		} else {
			transactionRequest.setTransactionType(TransactionType.CAPTURE);
		}
		if (!ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getMrchnt())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getPOI())) {
			transactionRequest
					.setMerchantCode(cardPaymentDataSet22.getCmpltn().getEnvt().getMrchnt().getId().getId());
			transactionRequest
					.setTerminalId(cardPaymentDataSet22.getCmpltn().getEnvt().getPOI().getId().getId());
			transactionRequest
					.setRegisterNumber(cardPaymentDataSet22.getCmpltn().getEnvt().getPOI().getId().getId());
		}
	}

	private String getCvv(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22) {
		if (!ObjectUtils.isEmpty(cardPaymentDataSet22) && !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getCard())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getCard().getPlainCardData())) {
			return cardPaymentDataSet22.getCmpltn().getEnvt().getCard().getPlainCardData().getSvcCd();
		}
		return null;
	}

	private String getPan(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22) {
		if (!ObjectUtils.isEmpty(cardPaymentDataSet22) && !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getCard())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getCard().getPlainCardData())) {
			return cardPaymentDataSet22.getCmpltn().getEnvt().getCard().getPlainCardData().getPAN();
		}
		return null;

	}

	private String getExpiryDate(CardPaymentDataSetTransaction7Choice cardPaymentDataSet22) {
		if (!ObjectUtils.isEmpty(cardPaymentDataSet22) && !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getCard())
				&& !ObjectUtils.isEmpty(cardPaymentDataSet22.getCmpltn().getEnvt().getCard().getPlainCardData())) {
			return cardPaymentDataSet22.getCmpltn().getEnvt().getCard().getPlainCardData().getXpryDt();
		}
		return null;
	}

	private NexoTxn saveTransactionRequest(String acceptorBatchTransferxml,
			com.girmiti.nexo.acceptorbatchtransferresponse.Header25 resHeader) {
		NexoTxn nexoTxn = new NexoTxn();
		try {
			nexoTxn.setRequestType("AcceptorBatchTransfer");
			nexoTxn.setCreDtTm(new Timestamp(System.currentTimeMillis()));
			nexoTxn.setInitgPtyId(resHeader.getInitgPty().getId());
			nexoTxn.setRequestData(acceptorBatchTransferxml);
			nexoTxn = JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"saveTransactionRequest");
		} catch (Exception e) {
			LOGGER.error("Exception Occured while saving AcceptorBatch Requset", e);
		}
		return nexoTxn;
	}

	private boolean checkForContext(CardPaymentDataSetTransaction7Choice cardPaymentDataSetTransaction7Choice) {
		return !(cardPaymentDataSetTransaction7Choice.getCmpltn() != null && cardPaymentDataSetTransaction7Choice.getCmpltn().getCntxt() != null
				|| cardPaymentDataSetTransaction7Choice.getCxl() != null && cardPaymentDataSetTransaction7Choice.getCxl().getCntxt() != null
				|| cardPaymentDataSetTransaction7Choice.getAuthstnReq() != null && cardPaymentDataSetTransaction7Choice.getAuthstnReq().getCntxt() != null);
	}
	
	private static void updateNexoTxn(NexoTxn nexoTxn, String responseXml) {
		if (nexoTxn != null) {
			nexoTxn.setResponseData(responseXml);
			JsonUtil.postServiceRequest(nexoTxn, NexoTxn.class,
					Constants.LOCALHOST + Constants.PORT + Constants.TRANSACTION + Constants.VERSION,
					"updateTransactionResponse");
		}
	}
	
}