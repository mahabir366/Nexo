package com.girmiti.nexo.processor.impl;

import javax.xml.stream.FactoryConfigurationError;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.girmiti.nexo.util.AcquirerProperties;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AcceptorDiagnosticProcessorTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptorDiagnosticProcessorTest.class);
	
	@InjectMocks
	AcceptorDiagnosticProcessor acceptorDiagnosticProcessor;
	
	@Mock
	JaxbHelper jaxbHelper;
	
	@Before
	public void setUp() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("ispgmock", "false");
		AcquirerProperties.mergeProperties(properties);
	}
	
	@Test
	public void testProcess() throws Exception {
		com.girmiti.nexo.acceptordiagnosticrequest.Document accDiagnosticReqDocument = null;
		String acceptorDiagnosticReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.013.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrDgnstcReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>DGNP</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:27:02.771+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <DgnstcReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>9</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <POIId>\n" + 
				"                    <Id>8</Id>\n" + 
				"                </POIId>\n" + 
				"            </Envt>\n" + 
				"        </DgnstcReq>\n" + 
				"    </AccptrDgnstcReq>\n" + 
				"</ns2:Document>";
		diagnosticResponse(accDiagnosticReqDocument, acceptorDiagnosticReqXmlRecr);
	}
	
	@Test
	public void testProcessMessageFunction() throws Exception {
		com.girmiti.nexo.acceptordiagnosticrequest.Document accDiagnosticReqDocument = null;
		String acceptorDiagnosticReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.013.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrDgnstcReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:27:02.771+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <DgnstcReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>9</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <POIId>\n" + 
				"                    <Id>8</Id>\n" + 
				"                </POIId>\n" + 
				"            </Envt>\n" + 
				"        </DgnstcReq>\n" + 
				"    </AccptrDgnstcReq>\n" + 
				"</ns2:Document>";
		diagnosticResponse(accDiagnosticReqDocument, acceptorDiagnosticReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws Exception {
		com.girmiti.nexo.acceptordiagnosticrequest.Document accDiagnosticReqDocument = null;
		String acceptorDiagnosticReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.013.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrDgnstcReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:27:02.771+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <DgnstcReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id></Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>9</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <POIId>\n" + 
				"                    <Id>8</Id>\n" + 
				"                </POIId>\n" + 
				"            </Envt>\n" + 
				"        </DgnstcReq>\n" + 
				"    </AccptrDgnstcReq>\n" + 
				"</ns2:Document>";
		diagnosticResponse(accDiagnosticReqDocument, acceptorDiagnosticReqXmlRecr);
	}

	private void diagnosticResponse(com.girmiti.nexo.acceptordiagnosticrequest.Document accDiagnosticReqDocument,
			String acceptorDiagnosticReqXmlRecr) {
		try {
			Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptordiagnosticrequest.Document.class, acceptorDiagnosticReqXmlRecr,
					Constants.CAAA_013_001_07)).thenReturn(accDiagnosticReqDocument);
		} catch (FactoryConfigurationError e) {
			LOGGER.error("ERROR :: AcceptorDiagnosticProcessorTest method diagnosticResponse", e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AcceptorDiagnosticProcessorTest method diagnosticResponse", e);
		}
		String response = null;
		try {
			response = acceptorDiagnosticProcessor.process(acceptorDiagnosticReqXmlRecr);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AcceptorDiagnosticProcessorTest method diagnosticResponse", e);
		}
		Assert.assertNotNull(response);
	}

}
