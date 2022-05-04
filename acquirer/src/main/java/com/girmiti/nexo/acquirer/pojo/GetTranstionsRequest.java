package com.girmiti.nexo.acquirer.pojo;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class GetTranstionsRequest {

	private Integer pageSize = 10;

	private Integer pageIndex = 1;
	
	private String fromDate;

	private String toDate;
	
	private String requestType;
	
	private String txRef;
	
	private String msgFctn;
	
	private Timestamp creDtTm;
	
	private String acqrId;
	
	private String requestData;
	
	private String txRspn;
	
	private String initgPtyId;
	
	private String mrchntId;
	
	private Long nexoTxnId;

}
