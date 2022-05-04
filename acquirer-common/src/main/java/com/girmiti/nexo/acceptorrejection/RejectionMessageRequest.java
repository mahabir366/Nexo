package com.girmiti.nexo.acceptorrejection;

import javax.xml.datatype.XMLGregorianCalendar;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RejectionMessageRequest {
	
	com.girmiti.nexo.acceptorrejection.MessageFunction9Code messageFunction;
	
	String protocolVersion;
	
	XMLGregorianCalendar txnDate;
	
	Header26 header26;
}
