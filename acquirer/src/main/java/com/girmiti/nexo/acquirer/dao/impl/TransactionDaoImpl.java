package com.girmiti.nexo.acquirer.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.girmiti.nexo.acquirer.dao.TransactionDao;
import com.girmiti.nexo.acquirer.dao.model.NexoTxn;
import com.girmiti.nexo.acquirer.dao.model.QNexoTxn;
import com.girmiti.nexo.acquirer.dao.repository.TransactionRepository;
import com.girmiti.nexo.acquirer.pojo.GetTranstionsRequest;
import com.girmiti.nexo.acquirer.pojo.TransactionReportResponse;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.DateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

@Repository("acceptorAuthTransactionDao")
public class TransactionDaoImpl implements TransactionDao {

	static Logger logger = Logger.getLogger(TransactionDaoImpl.class);
	
	private static final String PATTERN="dd/MM/yyyy"; 
	@Autowired
	TransactionRepository transactionRepository;

	@PersistenceContext
	EntityManager entityManager;

	public NexoTxn saveTransactionRequest(NexoTxn nexoTxn) {
		try {
			nexoTxn = transactionRepository.save(nexoTxn);
		} catch (Exception e) {
			logger.error("Exception Occured while saving AcceptorTransaction Requset", e);
		}
		return nexoTxn;
	}

	public NexoTxn updateTransactionResponse(NexoTxn nexoTxn) {

		try {
			if (nexoTxn.getNexoTxnId() != null) {
				transactionRepository.save(nexoTxn);
			}
		} catch (Exception e) {
			logger.error("Exception Occured while updating AcceptorTransaction Response", e);
		}

		return nexoTxn;
	}

	@Override
	public TransactionReportResponse getTransactions(GetTranstionsRequest getTransactionsListRequest) {
		List<GetTranstionsRequest> transactions = null;
		TransactionReportResponse res = new TransactionReportResponse();
		try {

			int pageIndexNo = getTransactionsListRequest.getPageIndex();
			int fromIndex = 0;
			int toIndex;
			if (pageIndexNo != 0) {
				toIndex = pageIndexNo * Constants.SIZE;
				fromIndex = toIndex - Constants.SIZE;
			}
			int limit = 0;
			if (getTransactionsListRequest.getPageSize() == 0) {
				limit = Constants.SIZE;
			} else {

				limit = getTransactionsListRequest.getPageSize();
			}

			Timestamp endDate = null;
			Timestamp startDate = null;
			if (getTransactionsListRequest.getFromDate() != null) {
				Timestamp fromDate = DateUtil.toTimestamp(getTransactionsListRequest.getFromDate(),
						PATTERN);
				startDate = fromDate;
			}
			if (getTransactionsListRequest.getToDate() != null) {
				Timestamp toDate = DateUtil.toTimestamp(getTransactionsListRequest.getToDate(), PATTERN);
				endDate = toDate;
			}
			JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
			List<Tuple> tupleList = query
					.from(QNexoTxn.nexoTxn).select(QNexoTxn.nexoTxn.nexoTxnId, QNexoTxn.nexoTxn.requestType,
							QNexoTxn.nexoTxn.msgFctn, QNexoTxn.nexoTxn.creDtTm, QNexoTxn.nexoTxn.initgPtyId,
							QNexoTxn.nexoTxn.acqrrId, QNexoTxn.nexoTxn.mrchntId, QNexoTxn.nexoTxn.txTxRef,
							QNexoTxn.nexoTxn.txRspn)
					.where(isTxnType(getTransactionsListRequest.getRequestType() ),
							isTxnRefNo(getTransactionsListRequest.getTxRef()), isValidDate(startDate, endDate))
					.offset(fromIndex).limit(limit).orderBy(orderByCreatedDateDesc()).fetch();
			if (tupleList != null && !tupleList.isEmpty()) {
				transactions = getTransactionsExisting(tupleList);
			}
		} catch (Exception e) {
			logger.error("TransactionDaoImpl | getTransactions | Exception ", e);
		}
		logger.trace("TransactionDaoImpl | getTransactions | Exiting");
		res.setTransactionList(transactions);
		res.setTotalNoOfRecords(getTotalTransactionCount(getTransactionsListRequest));
		return res;
	}

	private int getTotalTransactionCount(GetTranstionsRequest getTransactionsListRequest) {
		Long count = null;
		Timestamp endDate = null;
		Timestamp startDate = null;
		if (getTransactionsListRequest.getFromDate() != null) {

			Timestamp fromDate = DateUtil.toTimestamp(getTransactionsListRequest.getFromDate(), PATTERN);
			startDate = fromDate;
		}
		if (getTransactionsListRequest.getToDate() != null) {
			Timestamp toDate = DateUtil.toTimestamp(getTransactionsListRequest.getToDate(), PATTERN);
			endDate = toDate;
		}
		JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
		count = query
				.from(QNexoTxn.nexoTxn).select(QNexoTxn.nexoTxn.nexoTxnId, QNexoTxn.nexoTxn.requestType,
						QNexoTxn.nexoTxn.msgFctn, QNexoTxn.nexoTxn.creDtTm, QNexoTxn.nexoTxn.initgPtyId,
						QNexoTxn.nexoTxn.acqrrId, QNexoTxn.nexoTxn.mrchntId, QNexoTxn.nexoTxn.txTxRef,
						QNexoTxn.nexoTxn.txRspn)
				.where(isTxnType(getTransactionsListRequest.getRequestType()),
						isTxnRefNo(getTransactionsListRequest.getTxRef()), isValidDate(startDate, endDate))
				.orderBy(orderByCreatedDateDesc()).fetchCount();
		return count != null ? count.intValue() : 0;
	}

	protected BooleanExpression isTxnType(String txnType) {
		return (txnType != null && !"".equals(txnType)) ? QNexoTxn.nexoTxn.requestType.eq(txnType) : null;

	}

	protected BooleanExpression isTxnRefNo(String txnRefNo) {
		return (txnRefNo != null && !"".equals(txnRefNo)) ? QNexoTxn.nexoTxn.txTxRef.eq(txnRefNo) : null;

	}

	protected BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
		if ((fromDate != null && toDate == null)) {
			return QNexoTxn.nexoTxn.creDtTm.gt(fromDate);
		} else if ((fromDate == null && toDate != null)) {
			return QNexoTxn.nexoTxn.creDtTm.lt(toDate);
		} else if ((fromDate == null)) {
			return null;
		}
		return QNexoTxn.nexoTxn.creDtTm.between(fromDate, toDate);
	}

	protected OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		return QNexoTxn.nexoTxn.creDtTm.asc();
	}

	private List<GetTranstionsRequest> getTransactionsExisting(List<Tuple> tupleList) {
		List<GetTranstionsRequest> transactions;
		transactions = new ArrayList<>();
		for (Tuple tuple : tupleList) {
			GetTranstionsRequest transactions1 = new GetTranstionsRequest();
			transactions1.setNexoTxnId(tuple.get(QNexoTxn.nexoTxn.nexoTxnId));
			transactions1.setRequestType(
					tuple.get(QNexoTxn.nexoTxn.requestType) != null ? tuple.get(QNexoTxn.nexoTxn.requestType) : "");
			transactions1.setMsgFctn(tuple.get(QNexoTxn.nexoTxn.msgFctn));
			transactions1.setCreDtTm(tuple.get(QNexoTxn.nexoTxn.creDtTm));
			transactions1.setInitgPtyId(tuple.get(QNexoTxn.nexoTxn.initgPtyId));
			transactions1.setAcqrId(tuple.get(QNexoTxn.nexoTxn.acqrrId));
			transactions1.setMrchntId(tuple.get(QNexoTxn.nexoTxn.mrchntId));
			transactions1.setTxRef(tuple.get(QNexoTxn.nexoTxn.txTxRef));
			transactions1.setTxRspn(tuple.get(QNexoTxn.nexoTxn.txRspn));
			transactions.add(transactions1);
		}
		return transactions;
	}

	@Override
	public NexoTxn findByPgTxRef(NexoTxn nexoTxn) {
		return transactionRepository.findBypgTxnRef(nexoTxn.getPgTxnRef());
	}

	@Override
	public List<NexoTxn> findByCaptureStatus(String captureStatus) {
		return transactionRepository.findByCaptureStatus(captureStatus);
	}
}
