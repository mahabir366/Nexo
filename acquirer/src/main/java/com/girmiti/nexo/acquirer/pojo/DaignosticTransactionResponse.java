package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import com.girmiti.nexo.acceptorrejection.RejectReason1Code;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DaignosticTransactionResponse implements Serializable{
	
	private static final long serialVersionUID = 2476299171017408948L;

	private String errorMessage;

	private Boolean status;

	private String errorCode;

	private Boolean tmsTrigger;

	private RejectReason1Code rejectionCode;

	private String rejectionReason;

}
