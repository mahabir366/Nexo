package com.girmiti.nexo.acquirer.exception;

import com.girmiti.nexo.acceptorrejection.RejectReason1Code;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectionException extends Exception {

	private static final long serialVersionUID = -4822087718708677745L;

	private final RejectReason1Code rejectionReason;
	private final String rejectionCause;

	public RejectionException(RejectReason1Code rejectionReason, String rejectionCause) {
		super();
		this.rejectionReason = rejectionReason;
		this.rejectionCause = rejectionCause;
	}
	
	
}
