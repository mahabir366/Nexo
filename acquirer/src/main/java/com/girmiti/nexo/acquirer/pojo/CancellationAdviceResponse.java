package com.girmiti.nexo.acquirer.pojo;

import com.girmiti.nexo.acceptorrejection.RejectReason1Code;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancellationAdviceResponse {

	private String errorMessage;

	private Boolean status;

	private String errorCode;

	private String txnRefNumber;

	private RejectReason1Code rejectionCode;

	private String rejectionReason;
}
