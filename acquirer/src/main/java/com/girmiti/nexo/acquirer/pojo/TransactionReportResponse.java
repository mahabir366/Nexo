package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class TransactionReportResponse extends Response implements Serializable {

	private static final long serialVersionUID = -3436348203117000350L;

	private List<GetTranstionsRequest> transactionList;

	private Integer totalNoOfRecords;

}
