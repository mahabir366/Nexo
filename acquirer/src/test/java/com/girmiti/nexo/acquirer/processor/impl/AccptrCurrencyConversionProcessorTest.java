package com.girmiti.nexo.acquirer.processor.impl;

import javax.xml.stream.FactoryConfigurationError;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.girmiti.nexo.acceptorcompletionadvice.Document;
import com.girmiti.nexo.processor.impl.AccptrCurrencyConversionProcessor;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccptrCurrencyConversionProcessorTest {
	
	@InjectMocks
	AccptrCurrencyConversionProcessor accptrCurrencyConversionProcessor;
	
	@Mock
	JaxbHelper jaxbHelper;

	@Test
	public void testProcessExpiryDateError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.016.001.06\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrCcyConvsReq>\n" + "<Hdr>\n" + "<MsgFctn>DCCQ</MsgFctn>\n" + "<PrtcolVrsn>6.0</PrtcolVrsn>\n"
				+ "<XchgId>149</XchgId>\n" + "<CreDtTm>2019-12-18T19:40:05.571+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>1</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<CcyConvsReq>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>155058594833186</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276036</PAN>\n" + "<XpryDt>2021-93-03</XpryDt>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>056</CardCtryCd>\n" + "</Card>\n" + "</Envt>\n" + "<Tx>\n" + "<TxTp>BALC</TxTp>\n"
				+ "<MrchntCtgyCd>899</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2019-12-18T19:40:05.571+05:30</TxDtTm>\n"
				+ "<TxRef>200993</TxRef>\n" + "</TxId>\n" + "<TxDtls>\n" + "<Ccy>USD</Ccy>\n" + "<TtlAmt>123</TtlAmt>\n"
				+ "</TxDtls>\n" + "</Tx>\n" + "</CcyConvsReq>\n" + "</AccptrCcyConvsReq>\n" + "</ns2:Document>";
		accptrCurrencyConversionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcess() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.016.001.06\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrCcyConvsReq>\n" + "<Hdr>\n" + "<MsgFctn>DCCQ</MsgFctn>\n" + "<PrtcolVrsn>6.0</PrtcolVrsn>\n"
				+ "<XchgId>149</XchgId>\n" + "<CreDtTm>2019-12-18T19:40:05.571+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>1</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<CcyConvsReq>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>155058594833186</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276036</PAN>\n" + "<XpryDt>2021-12-03</XpryDt>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>056</CardCtryCd>\n" + "</Card>\n" + "</Envt>\n" + "<Tx>\n" + "<TxTp>BALC</TxTp>\n"
				+ "<MrchntCtgyCd>899</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2019-12-18T19:40:05.571+05:30</TxDtTm>\n"
				+ "<TxRef>200993</TxRef>\n" + "</TxId>\n" + "<TxDtls>\n" + "<Ccy>USD</Ccy>\n" + "<TtlAmt>123</TtlAmt>\n"
				+ "</TxDtls>\n" + "</Tx>\n" + "</CcyConvsReq>\n" + "</AccptrCcyConvsReq>\n" + "</ns2:Document>";
		accptrCurrencyConversionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.016.001.06\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrCcyConvsReq>\n" + "<Hdr>\n" + "<MsgFctn>DCCQ</MsgFctn>\n" + "<PrtcolVrsn>6.0</PrtcolVrsn>\n"
				+ "<XchgId>149</XchgId>\n" + "<CreDtTm>2019-12-18T19:40:05.571+05:30hjhkjh</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>1</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<CcyConvsReq>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>155058594833186</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276036</PAN>\n" + "<XpryDt>2021-12-03</XpryDt>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>056</CardCtryCd>\n" + "</Card>\n" + "</Envt>\n" + "<Tx>\n" + "<TxTp>BALC</TxTp>\n"
				+ "<MrchntCtgyCd>899</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2019-12-18T19:40:05.571+05:30</TxDtTm>\n"
				+ "<TxRef>200993</TxRef>\n" + "</TxId>\n" + "<TxDtls>\n" + "<Ccy>USD</Ccy>\n" + "<TtlAmt>123</TtlAmt>\n"
				+ "</TxDtls>\n" + "</Tx>\n" + "</CcyConvsReq>\n" + "</AccptrCcyConvsReq>\n" + "</ns2:Document>";
		accptrCurrencyConversionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCountryError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.016.001.06\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrCcyConvsReq>\n" + "<Hdr>\n" + "<MsgFctn>DCCQ</MsgFctn>\n" + "<PrtcolVrsn>6.0</PrtcolVrsn>\n"
				+ "<XchgId>149</XchgId>\n" + "<CreDtTm>2019-12-18T19:40:05.571+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>1</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<CcyConvsReq>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>155058594833186</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276036</PAN>\n" + "<XpryDt>2021-12-03</XpryDt>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>UIO</CardCtryCd>\n" + "</Card>\n" + "</Envt>\n" + "<Tx>\n" + "<TxTp>BALC</TxTp>\n"
				+ "<MrchntCtgyCd>899</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2019-12-18T19:40:05.571+05:30</TxDtTm>\n"
				+ "<TxRef>200993</TxRef>\n" + "</TxId>\n" + "<TxDtls>\n" + "<Ccy>USD</Ccy>\n" + "<TtlAmt>123</TtlAmt>\n"
				+ "</TxDtls>\n" + "</Tx>\n" + "</CcyConvsReq>\n" + "</AccptrCcyConvsReq>\n" + "</ns2:Document>";
		accptrCurrencyConversionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCurrencyError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.016.001.06\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrCcyConvsReq>\n" + "<Hdr>\n" + "<MsgFctn>DCCQ</MsgFctn>\n" + "<PrtcolVrsn>6.0</PrtcolVrsn>\n"
				+ "<XchgId>149</XchgId>\n" + "<CreDtTm>2019-12-18T19:40:05.571+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>1</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<CcyConvsReq>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>155058594833186</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276036</PAN>\n" + "<XpryDt>2021-12-03</XpryDt>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>056</CardCtryCd>\n" + "</Card>\n" + "</Envt>\n" + "<Tx>\n" + "<TxTp>BALC</TxTp>\n"
				+ "<MrchntCtgyCd>899</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2019-12-18T19:40:05.571+05:30</TxDtTm>\n"
				+ "<TxRef>200993</TxRef>\n" + "</TxId>\n" + "<TxDtls>\n" + "<Ccy>NUL</Ccy>\n" + "<TtlAmt>123</TtlAmt>\n"
				+ "</TxDtls>\n" + "</Tx>\n" + "</CcyConvsReq>\n" + "</AccptrCcyConvsReq>\n" + "</ns2:Document>";
		accptrCurrencyConversionResponse(acceptorAuthReqXmlRecr);
	}

	private void accptrCurrencyConversionResponse(String acceptorAuthReqXmlRecr)
			throws FactoryConfigurationError, Exception {
		Document accptrCurrencyConversionProcessorDocument = null;
		Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class, acceptorAuthReqXmlRecr,
				Constants.CAAA_001_001_08)).thenReturn(accptrCurrencyConversionProcessorDocument);
		String process = accptrCurrencyConversionProcessor.process(acceptorAuthReqXmlRecr);
		Assert.assertNotNull(process);
	}

}
