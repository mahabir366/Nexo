package com.girmiti.nexo.acquirer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.girmiti.nexo.acceptorauthorizationrequest.DisplayCapabilities4;
import com.girmiti.nexo.acceptorauthorizationresponse.Action8;
import com.girmiti.nexo.acceptorauthorizationresponse.ActionType7Code;
import com.girmiti.nexo.acceptorauthorizationresponse.CardPaymentTransaction84;
import com.girmiti.nexo.acceptorauthorizationresponse.OutputFormat1Code;
import com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7;
import com.girmiti.nexo.acceptorbatchtransferresponse.TypeTransactionTotals2Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acquirer.dao.TransactionDao;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.dao.repository.TransactionRepository;
import com.girmiti.nexo.acquirer.enums.TransactionStatus;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.BulkTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceRequest;
import com.girmiti.nexo.acquirer.pojo.CancellationAdviceResponse;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionRequest;
import com.girmiti.nexo.acquirer.pojo.DaignosticTransactionResponse;
import com.girmiti.nexo.acquirer.pojo.DccRequest;
import com.girmiti.nexo.acquirer.pojo.DccResponse;
import com.girmiti.nexo.acquirer.pojo.ReconciliationRequest;
import com.girmiti.nexo.acquirer.pojo.ReconciliationResponse;
import com.girmiti.nexo.acquirer.pojo.TransactionRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionResponse;
import com.girmiti.nexo.acquirer.service.TransactionService;
import com.girmiti.nexo.util.Constants;

@Service("transactionSimulatorService")
@Qualifier("transactionSimulatorService")
public class TransactionSimulatorServiceImpl implements TransactionService {
	
	@Autowired(required = true)
	TransactionDao transactionDao;

	@Override
	public NexoTxn saveTransactionRequest(NexoTxn nexoTxn) {
		return transactionDao.saveTransactionRequest(nexoTxn);
	}

	@Override
	public NexoTxn updateTransactionResponse(NexoTxn nexoTxn) {
		return transactionDao.updateTransactionResponse(nexoTxn);
	}

	@Override
	public DaignosticTransactionResponse getDiagnosticStatus(DaignosticTransactionRequest transactionRequest) {
		DaignosticTransactionResponse daignosticTransactionResponse = new DaignosticTransactionResponse();
		daignosticTransactionResponse.setStatus(true);
		daignosticTransactionResponse.setTmsTrigger(Boolean.valueOf(Constants.TMSTRIGGER));
		if (transactionRequest.getMac() != null) {
			String decodedString = new String(transactionRequest.getMac());
			if (Constants.CORRUPTED.equalsIgnoreCase(decodedString)) {
				daignosticTransactionResponse.setRejectionCode(RejectReason1Code.SECU);
				daignosticTransactionResponse.setRejectionReason("MAC of the Security Trailer is corrupted");
				return daignosticTransactionResponse;
			}
		}
		return daignosticTransactionResponse;
	}

	@Override
	public CancellationAdviceResponse getCancellationAdviceStatus(CancellationAdviceRequest transactionRequest) {
		CancellationAdviceResponse cancellationAdviceResponse = new CancellationAdviceResponse();
		Long txnAmount = transactionRequest.getTxnAmount();
		if (Long.parseLong(Constants.REJECTION_REMAINDER) == (transactionRequest.getTxnAmount() % 10) 
				||Constants.REJECT_MESSAGE_FUNCTION .equalsIgnoreCase(transactionRequest.getMsgFunction()) ||Constants.REJECT_INITIATING_PARTY .equalsIgnoreCase(transactionRequest.getInitgPty())
				|| Constants.PROTOCOL_VERSION.equalsIgnoreCase(transactionRequest.getPrtcolVrsn())) {
			validateCancellationRejection(transactionRequest, cancellationAdviceResponse);
		}
		Long reminder = txnAmount % 10;
		if (reminder == 0 || reminder == 4) { 
			cancellationAdviceResponse.setErrorCode("00");
			cancellationAdviceResponse.setTxnRefNumber("ID2");
		} else if (reminder == 1) { 
			cancellationAdviceResponse.setErrorCode("00");
			cancellationAdviceResponse.setTxnRefNumber("ID4");
		} else if (reminder == 2 || reminder == 3) { 
			cancellationAdviceResponse.setErrorCode("00");
			cancellationAdviceResponse.setTxnRefNumber(transactionRequest.getTxnRefNumber());
		} else {
			cancellationAdviceResponse.setErrorCode("-99"); // Duplicate message
			cancellationAdviceResponse.setTxnRefNumber(transactionRequest.getTxnRefNumber());
		}
		return cancellationAdviceResponse;
	}

	@Override
	public TransactionResponse processTransactionBasedOnStatus(TransactionRequest transactionRequest) {
		TransactionResponse transactionResponse = new TransactionResponse();
		
		transactionResponse.setCgRefNumber(getSequenceValue());
		transactionResponse.setTxnRefNumber(getSequenceValue());
		transactionResponse.setDeviceLocalTxnTime(Long.toString(System.currentTimeMillis()));
		transactionResponse.setMerchantCode(transactionRequest.getMerchantCode());
		transactionResponse.setTransactionType(transactionRequest.getTransactionType().toString());
		transactionResponse.setTxnDateTime(System.currentTimeMillis());
		
		Long txnAmount = transactionRequest.getTotalTxnAmount();
		Long reminder = txnAmount % 10;
		
		if (reminder == 0) { 
			transactionResponse.setErrorCode("00");
			transactionResponse.setApprovalStatus("APPR");
			transactionResponse.setErrorMessage("Success");
		} else {
			transactionResponse.setErrorCode("-99");
			transactionResponse.setErrorMessage("Failure");
		}
		return transactionResponse;
	}

	@Override
	public NexoTxn findByPgTxRef(NexoTxn nexoTxn) {
		nexoTxn.setCgTxnRef(String.valueOf(ThreadLocalRandom.current().nextInt()).replace("-", ""));
		nexoTxn.setCaptureStatus("true");
		return nexoTxn;
	}
	
	@Autowired
	TransactionRepository transactionRepository;
	public String getSequenceValue() {
		return transactionRepository.getNextSeriesId();
	}

	@Override
	public TransactionResponse processSaleTransaction(TransactionRequest transactionRequest) {
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setAuthId(getSequenceValue());
		transactionResponse.setCgRefNumber(getSequenceValue());
		transactionResponse.setTxnRefNumber(getSequenceValue());
		transactionResponse.setDeviceLocalTxnTime(Long.toString(System.currentTimeMillis()));
		transactionResponse.setMerchantCode(transactionRequest.getMerchantCode());
		transactionResponse.setTransactionType(transactionRequest.getTransactionType().toString());
		transactionResponse.setTxnDateTime(System.currentTimeMillis());
		transactionResponse.setAuthstnCd("auth001");
		Long txnAmount = transactionRequest.getTotalTxnAmount();
		Long remainder = txnAmount % 10;
		if (remainder == 0) { // For APPROVED Scenario
			transactionResponse.setApprovalStatus("APPROVED");
			transactionResponse.setErrorCode("00");
			transactionResponse.setErrorMessage("Success");
		} else if (remainder == 1) {	// For PARTIAL APPROVED Scenario
			transactionResponse.setApprovalStatus("PARTIAL");
			transactionResponse.setErrorCode("111");
			transactionResponse.setErrorMessage("Failure");
		} else {	// For DECLINED Scenario
			transactionResponse.setApprovalStatus("DECLINED");
			transactionResponse.setErrorCode("222");
			transactionResponse.setErrorMessage("Failure");
		}
		return transactionResponse;
	}
	
	@Override
	public TransactionResponse processAuthTransaction(TransactionRequest transactionRequest) {
		TransactionResponse transactionResponse = new TransactionResponse();
		if (transactionRequest.getTotalTxnAmount() == Long.parseLong(Constants.TTLAMT_1)) {
			setTxnAmount(transactionRequest);
		}
		if (Long.parseLong(Constants.REJECTION_REMAINDER) == (transactionRequest.getTotalTxnAmount() % 10)
				|| Constants.REJECT_MESSAGE_FUNCTION.equalsIgnoreCase(transactionRequest.getMsgFunction())
				|| Constants.REJECT_INITIATING_PARTY.equalsIgnoreCase(transactionRequest.getInitgPty())
				|| Constants.PROTOCOL_VERSION.equalsIgnoreCase(transactionRequest.getPrtcolVrsn())) {
			validateReqForRejection(transactionRequest, transactionResponse);
		}
		transactionResponse.setAuthId(getSequenceValue());
		transactionResponse.setCgRefNumber(getSequenceValue());
		transactionResponse.setTxnRefNumber(transactionRequest.getTxnRefNumber());
		transactionResponse.setDeviceLocalTxnTime(Long.toString(System.currentTimeMillis()));
		transactionResponse.setMerchantCode(transactionRequest.getMerchantCode());
		transactionResponse.setTransactionType(transactionRequest.getTransactionType().toString());
		transactionResponse.setTxnDateTime(System.currentTimeMillis());
		transactionResponse.setAuthstnCd("auth001");
		Long txnAmount = transactionRequest.getTotalTxnAmount();
		Long remainder = txnAmount % 10;
		if (remainder == 0) { // For APPROVED Scenario
			transactionResponse.setApprovalStatus(TransactionStatus.APPROVED.toString());
			transactionResponse.setErrorCode(TransactionStatus.APPROVED.value());
			transactionResponse.setErrorMessage("Success");
		} else if (remainder == 1) { // For PARTIAL APPROVED Scenario
			transactionResponse.setApprovalStatus(TransactionStatus.PARTIAL.toString());
			transactionResponse.setErrorCode(TransactionStatus.PARTIAL.value());
			transactionResponse.setErrorMessage("Failure");
		} else if (remainder == 2) { // For Completion Required Scenario
			transactionResponse.setCmpltnReqrd(true);
			transactionResponse.setApprovalStatus(TransactionStatus.APPROVED.toString());
			transactionResponse.setErrorCode(TransactionStatus.APPROVED.value());
			transactionResponse.setErrorMessage("Success");
		} else { // For DECLINED Scenario
			transactionResponse.setApprovalStatus(TransactionStatus.DECLINED.toString());
			transactionResponse.setErrorCode(TransactionStatus.DECLINED.value());
			transactionResponse.setResponseReason("TECH");
			transactionResponse.setErrorMessage("Failure");
		}
		return transactionResponse;
	}

	
	private void setTxnAmount(TransactionRequest transactionRequest) {
		List<DisplayCapabilities4> reqMsgCpbltiesList = transactionRequest.getReqMsgCpbltiesList();
		CardPaymentTransaction84 cardPaymentTxnRspn = new CardPaymentTransaction84();
		if (reqMsgCpbltiesList != null) {
			Action8 action1;
			com.girmiti.nexo.acceptorauthorizationresponse.ActionMessage2 actionMessage2;
			int i = 0;
			for (DisplayCapabilities4 dispCapb : reqMsgCpbltiesList) {
				action1 = new Action8();
				actionMessage2 = new com.girmiti.nexo.acceptorauthorizationresponse.ActionMessage2();
				if (i == 2 || i == 3) {
					action1.setActnTp(ActionType7Code.DISP);
				} else {
					action1.setActnTp(ActionType7Code.PRNT);
				}
				actionMessage2.setFrmt(OutputFormat1Code.MREF);
				actionMessage2.setMsgDstn(com.girmiti.nexo.acceptorauthorizationresponse.UserInterface4Code.CDSP);
				actionMessage2.setMsgCntt("call merchant service");
				actionMessage2.setMsgCnttSgntr(null);
				action1.setMsgToPres(actionMessage2);
				cardPaymentTxnRspn.getActn().add(action1);
				i++;
			}
		}
	}
	
	
	@Override
	public TransactionResponse processCaptureTransaction(TransactionRequest transactionRequest) {
		TransactionResponse response = new TransactionResponse();
		Long txnAmount = transactionRequest.getTxnAmount(); 
		if (Long.parseLong(Constants.REJECTION_REMAINDER) == (transactionRequest.getTxnAmount() % 10) 
				||Constants.REJECT_MESSAGE_FUNCTION .equalsIgnoreCase(transactionRequest.getMsgFunction()) ||Constants.REJECT_INITIATING_PARTY .equalsIgnoreCase(transactionRequest.getInitgPty())
				|| Constants.PROTOCOL_VERSION.equalsIgnoreCase(transactionRequest.getPrtcolVrsn())) {
			validateRejection(transactionRequest, response);
		}
		if (txnAmount%2 == 0) {
			response.setAuthId(transactionRequest.getAuthId());
			response.setResponse("Approved");
			response.setCgRefNumber(getSequenceValue());
			response.setTxnRefNumber(transactionRequest.getTxnRefNumber());
			response.setErrorCode("00");
		}else {
			response.setResponse("Declined");
			response.setCgRefNumber(getSequenceValue());
			response.setTxnRefNumber(getSequenceValue());
			response.setErrorCode("01");
		}
		return response;
	}

	@Override
	public ReconciliationResponse processReconcialtionTransaction(ReconciliationRequest reconciliationRequest) {
		List<com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7> transactionTotals7list = getReconTotalTransactions(reconciliationRequest.getTransactionTotals7Req());
		ReconciliationResponse reconciliationResponse = new ReconciliationResponse();
		reconciliationResponse.setTransactionTotals7Res(transactionTotals7list);
		Long txnAmount = Long.valueOf(reconciliationRequest.getReconciliationId());
		Long reminder = txnAmount % 10;
		if (reminder == 0) { 
			reconciliationResponse.setErrorCode("00");
			reconciliationResponse.setTxnStatus(Constants.APPROVED);
		} else if (reminder == 1) { 
			reconciliationResponse.setErrorCode("01");
			reconciliationResponse.setErrorMessage("Totals Unavailable");
		} else if (reminder == 2) { 
			reconciliationResponse.setErrorCode("02");
			reconciliationResponse.setTxnStatus(Constants.APPROVED);
		} else { 
			reconciliationResponse.setErrorCode("03");
			reconciliationResponse.setTxnStatus(Constants.APPROVED);
		} 
		return reconciliationResponse;
	}

	@Override
	public BulkTransactionResponse batchProcessTransaction(BulkTransactionRequest bulkTransactionRequest) {
		List<com.girmiti.nexo.acceptorbatchtransfer.TransactionTotals7> transactionTotals7Req =bulkTransactionRequest.getTransactionTotals();
		List<com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7> transactionTotals7Res=setTransactionTotals(transactionTotals7Req);
			List<TransactionResponse> transactionResponseList = new ArrayList<>();
			TransactionResponse transactionResponse = new TransactionResponse();
			BulkTransactionResponse bulkTransactionResponse = new BulkTransactionResponse();
			Long txnAmount = bulkTransactionRequest.getTransactionRequest().get(0).getTotalTxnAmount();
			Long remainder = txnAmount % 10;
			if (remainder == 0) { // For APPROVED Scenario
				transactionResponse.setApprovalStatus(TransactionStatus.APPROVED.toString());
				transactionResponse.setErrorMessage("Success");
			} else if (remainder == 1) {	// For PARTIAL APPROVED Scenario
				transactionResponse.setApprovalStatus(TransactionStatus.PARTIAL.toString());
				transactionResponse.setErrorMessage("Failure");
			} else {	// For DECLINED Scenario
				transactionResponse.setApprovalStatus(TransactionStatus.DECLINED.toString());
				transactionResponse.setResponseReason("TECH");
				transactionResponse.setErrorMessage("Failure");
			}
			transactionResponseList.add(transactionResponse);
			bulkTransactionResponse.setTransactionResponse(transactionResponseList);
			bulkTransactionResponse.setTransactionTotals(transactionTotals7Res);
			return bulkTransactionResponse;

	
	}
	
	private List<com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7> setTransactionTotals(
			List<com.girmiti.nexo.acceptorbatchtransfer.TransactionTotals7> transactionTotals7) {
		List<com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7> transactionTotalsList = new ArrayList<>();
		if ( transactionTotals7!= null) {
			for (com.girmiti.nexo.acceptorbatchtransfer.TransactionTotals7 transtotal:transactionTotals7) {
				com.girmiti.nexo.acceptorbatchtransferresponse.TransactionTotals7 transactionTotals7Res= new TransactionTotals7();
				transactionTotals7Res.setPOIGrpId(transtotal.getPOIGrpId());
				transactionTotals7Res.setCardPdctPrfl(transtotal.getCardPdctPrfl());
				transactionTotals7Res.setCcy(transtotal.getCcy());
				if (transtotal != null) {
					if (transtotal.getTp().name().equals(TypeTransactionTotals2Code.FAIL.value())) {
						transactionTotals7Res.setTp(TypeTransactionTotals2Code.FAIL);
					}
					if (transtotal.getTp().name().equals(TypeTransactionTotals2Code.DEBT.value())) {
						transactionTotals7Res.setTp(TypeTransactionTotals2Code.DEBT);
					}
					if (transtotal.getTp().name().equals(TypeTransactionTotals2Code.DECL.value())) {
						transactionTotals7Res.setTp(TypeTransactionTotals2Code.DECL);
					}
					if (transtotal.getTp().name().equals(TypeTransactionTotals2Code.CRDT.value())) {
						transactionTotals7Res.setTp(TypeTransactionTotals2Code.CRDT);
					}
					if (transtotal.getTp().name().equals(TypeTransactionTotals2Code.DBTR.value())) {
						transactionTotals7Res.setTp(TypeTransactionTotals2Code.DBTR);
					}
					if (transtotal.getTp().name().equals(TypeTransactionTotals2Code.CRDR.value())) {
						transactionTotals7Res.setTp(TypeTransactionTotals2Code.CRDR);
					}
				}
				
				if (transtotal.getTtlNb() != null) {
					if (transtotal.getTtlNb().toString().equals("3")
							&& transtotal.getTp().name().equals(TypeTransactionTotals2Code.DEBT.value())
							&& transtotal.getCcy().equals("USD")&& transtotal.getCmltvAmt().longValue()>100) {
						BigDecimal txValue = new BigDecimal(0);
						transactionTotals7Res.setTtlNb(txValue);
						transactionTotals7Res.setCmltvAmt(txValue);
					} else {
						transactionTotals7Res.setTtlNb(transtotal.getTtlNb());
						transactionTotals7Res.setCmltvAmt(transtotal.getCmltvAmt());
					}
				}
				transactionTotalsList.add(transactionTotals7Res);
			}
			return transactionTotalsList;
		}
		return null;
	}

	@Override
	public DccResponse getCurrencyConversionRate(DccRequest dccRequest) {
		return null;
	}

	@Override
	public DccResponse updateCurrencyConversionTransaction(DccRequest dccRequest) {
		return null;
	}
	
	private List<com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7> getReconTotalTransactions(List<com.girmiti.nexo.acceptorreconciliationreqest.TransactionTotals7> transactionTotals7s) {
		List<com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7> transactionTotalsList = new ArrayList<>();
		for (com.girmiti.nexo.acceptorreconciliationreqest.TransactionTotals7 transactionTotals7 : transactionTotals7s) {
				com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7 totals7 = new com.girmiti.nexo.acceptorreconciliationresponse.TransactionTotals7();
				totals7.setCardPdctPrfl(transactionTotals7.getCardPdctPrfl());
				totals7.setCmltvAmt(transactionTotals7.getCmltvAmt());
				totals7.setTtlNb(transactionTotals7.getTtlNb());
				if(transactionTotals7.getTp().name().equals(TypeTransactionTotals2Code.FAIL.value())) {
					totals7.setTp(com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code.FAIL);
				}
				if(transactionTotals7.getTp().name().equals(TypeTransactionTotals2Code.CRDT.value())) {
					totals7.setTp(com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code.CRDT);
				}
				if(transactionTotals7.getTp().name().equals(TypeTransactionTotals2Code.CRDR.value())) {
					totals7.setTp(com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code.CRDR);
				}
				if(transactionTotals7.getTp().name().equals(TypeTransactionTotals2Code.DEBT.value())) {
					totals7.setTp(com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code.DEBT);
				}
				if(transactionTotals7.getTp().name().equals(TypeTransactionTotals2Code.DBTR.value())) {
					totals7.setTp(com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code.DBTR);
				}
				if(transactionTotals7.getTp().name().equals(TypeTransactionTotals2Code.DECL.value())) {
					totals7.setTp(com.girmiti.nexo.acceptorreconciliationresponse.TypeTransactionTotals2Code.DECL);
				}
				transactionTotalsList.add(totals7);
			}
		return transactionTotalsList;

	}

	private void validateReqForRejection(TransactionRequest transactionRequest,
			TransactionResponse transactionResponse) {
		long totAmt = transactionRequest.getTotalTxnAmount();
		String msgFunction = transactionRequest.getMsgFunction();
		String initgPty = transactionRequest.getInitgPty();
		String PrtcolVrsn = transactionRequest.getPrtcolVrsn();
		long totAmtRem = totAmt % 10;

		if (totAmtRem == 3) {
			transactionResponse.setRejectionCode(RejectReason1Code.SECU);
			transactionResponse.setRejectionReason("Security issue: POI may not use the proper cryptographic key");
		} else if (Constants.REJECT_MESSAGE_FUNCTION.equals(msgFunction)) {
			transactionResponse.setRejectionCode(RejectReason1Code.MSGT);
			transactionResponse.setRejectionReason("MessageFunction " + msgFunction + " is not supported by Acquirer");
		} else if (Constants.REJECT_INITIATING_PARTY.equals(initgPty)) {
			transactionResponse.setRejectionCode(RejectReason1Code.IMSG);
			transactionResponse.setRejectionReason("Initiating Party \" + initgPty + \" is not supported by Acquirer");
		} else if (Constants.PROTOCOL_VERSION.equals(PrtcolVrsn)) {
			transactionResponse.setRejectionCode(RejectReason1Code.VERS);
			transactionResponse
					.setRejectionReason("protocol version \" + PrtcolVrsn + \" is not supported by Acquirer");
		}

	}
	
	private void validateRejection(TransactionRequest transactionRequest,
			TransactionResponse transactionResponse) {
		long totAmt = transactionRequest.getTxnAmount();
		String msgFunction = transactionRequest.getMsgFunction();
		String initgPty = transactionRequest.getInitgPty();
		String PrtcolVrsn = transactionRequest.getPrtcolVrsn();
		long totAmtRem = totAmt % 10;

		if (totAmtRem == 3) {
			transactionResponse.setRejectionCode(RejectReason1Code.SECU);
			transactionResponse.setRejectionReason("Security issue: POI may not use the proper cryptographic key");
		} else if (Constants.REJECT_MESSAGE_FUNCTION.equals(msgFunction)) {
			transactionResponse.setRejectionCode(RejectReason1Code.MSGT);
			transactionResponse.setRejectionReason("MessageFunction " + msgFunction + " is not supported by Acquirer");
		} else if (Constants.REJECT_INITIATING_PARTY.equals(initgPty)) {
			transactionResponse.setRejectionCode(RejectReason1Code.IMSG);
			transactionResponse.setRejectionReason("Initiating Party \" + initgPty + \" is not supported by Acquirer");
		} else if (Constants.PROTOCOL_VERSION.equals(PrtcolVrsn)) {
			transactionResponse.setRejectionCode(RejectReason1Code.VERS);
			transactionResponse
					.setRejectionReason("protocol version \" + PrtcolVrsn + \" is not supported by Acquirer");
		}
	}
	
	private void validateCancellationRejection(CancellationAdviceRequest transactionRequest,
			CancellationAdviceResponse transactionResponse) {
		long totAmt = transactionRequest.getTxnAmount();
		String msgFunction = transactionRequest.getMsgFunction();
		String initgPty = transactionRequest.getInitgPty();
		String PrtcolVrsn = transactionRequest.getPrtcolVrsn();
		long totAmtRem = totAmt % 10;

		if (totAmtRem == 3) {
			transactionResponse.setRejectionCode(RejectReason1Code.SECU);
			transactionResponse.setRejectionReason("Security issue: POI may not use the proper cryptographic key");
		} else if (Constants.REJECT_MESSAGE_FUNCTION.equals(msgFunction)) {
			transactionResponse.setRejectionCode(RejectReason1Code.MSGT);
			transactionResponse.setRejectionReason("MessageFunction " + msgFunction + " is not supported by Acquirer");
		} else if (Constants.REJECT_INITIATING_PARTY.equals(initgPty)) {
			transactionResponse.setRejectionCode(RejectReason1Code.IMSG);
			transactionResponse.setRejectionReason("Initiating Party \" + initgPty + \" is not supported by Acquirer");
		} else if (Constants.PROTOCOL_VERSION.equals(PrtcolVrsn)) {
			transactionResponse.setRejectionCode(RejectReason1Code.VERS);
			transactionResponse
					.setRejectionReason("protocol version \" + PrtcolVrsn + \" is not supported by Acquirer");
		}
	}

	@Override
	public List<NexoTxn> findByCaptureStatus(String captrueStatus) {
		// TODO Auto-generated method stub
		return null;
	}
}
