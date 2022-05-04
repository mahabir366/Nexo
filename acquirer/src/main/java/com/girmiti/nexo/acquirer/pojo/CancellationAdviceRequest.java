package com.girmiti.nexo.acquirer.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancellationAdviceRequest {
	
	private Boolean status;
	
	private String merchantCode;

    private String txnRefNumber;
    
    private Long txnAmount;

    private String msgFunction;

    private String initgPty;

    private String PrtcolVrsn;

}
