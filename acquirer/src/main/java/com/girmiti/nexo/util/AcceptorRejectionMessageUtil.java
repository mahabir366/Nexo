package com.girmiti.nexo.util;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.girmiti.nexo.acceptorrejection.AcceptorRejection2;
import com.girmiti.nexo.acceptorrejection.AcceptorRejectionV05;
import com.girmiti.nexo.acceptorrejection.Document;
import com.girmiti.nexo.acceptorrejection.GenericIdentification94;
import com.girmiti.nexo.acceptorrejection.Header26;
import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.PartyType4Code;
import com.girmiti.nexo.acceptorrejection.RejectReason1Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;

public class AcceptorRejectionMessageUtil {
	
	private AcceptorRejectionMessageUtil() {
		//Do nothing
	}
	
	private static Logger logger = LoggerFactory.getLogger(AcceptorRejectionMessageUtil.class);
	
	


	public static String getClientAcceptorRejectionMessage(RejectionMessageRequest rejectionMessageRequest,
			RejectReason1Code code, String message,String requestXml) throws FactoryConfigurationError, JAXBException, XMLStreamException,
			javax.xml.stream.FactoryConfigurationError {
		Document document = getSampleAconvertToAcceptorRejectionMes(rejectionMessageRequest, code, message,requestXml);
		// Object to XML
		String acceptorRejmesXml = JaxbHelper.marshall(document, document.getAccptrRjctn());
		acceptorRejmesXml = acceptorRejmesXml.replace("ns2:Document", "Document");
		acceptorRejmesXml = acceptorRejmesXml.replace("xmlns:ns2", "xmlns");
		logger.info("acceptorRejmesXml");
		acceptorRejmesXml = acceptorRejmesXml.replaceAll("ns2:", "");
		return acceptorRejmesXml;
	}

	private static Document getSampleAconvertToAcceptorRejectionMes(RejectionMessageRequest rejectionMessageRequest,
			RejectReason1Code code, String message,String requestXml) {
		AcceptorRejectionV05 rejectionV05 = new AcceptorRejectionV05();
		Document document = new Document();
		rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCP);
		Header26 header = rejectionMessageRequest.getHeader26();
		if(ObjectUtils.isEmpty(header)) {
			header = new Header26();
		}
		header.setMsgFctn(rejectionMessageRequest.getMessageFunction());
		header.setPrtcolVrsn(rejectionMessageRequest.getProtocolVersion());
		header.setCreDtTm(rejectionMessageRequest.getTxnDate());
		com.girmiti.nexo.acceptorrejection.GenericIdentification53 identification53 = new com.girmiti.nexo.acceptorrejection.GenericIdentification53();
		identification53.setCtry("USA");
		identification53.setId("1002");
		identification53.setIssr(PartyType4Code.ACQR);
		identification53.setTp(com.girmiti.nexo.acceptorrejection.PartyType3Code.OPOI);
		header.setInitgPty(identification53);
		GenericIdentification94 genericIdentification94 = new GenericIdentification94();
		genericIdentification94.setId(" bad-acquirer");
		genericIdentification94.setTp(com.girmiti.nexo.acceptorrejection.PartyType3Code.ACQR);
		header.setRcptPty(genericIdentification94);
		rejectionV05.setHdr(header);
		AcceptorRejection2 request = new AcceptorRejection2();
		request.setRjctRsn(code);
		request.setAddtlInf(message);
		
		if (requestXml != null) {
			request.setMsgInErr(Base64.encodeBase64(requestXml.getBytes()));
		}
		
		rejectionV05.setRjct(request);
		document.setAccptrRjctn(rejectionV05);
		return document;
	}

}
