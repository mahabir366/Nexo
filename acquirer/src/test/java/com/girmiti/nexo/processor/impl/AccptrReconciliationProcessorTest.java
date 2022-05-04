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
public class AccptrReconciliationProcessorTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccptrReconciliationProcessorTest.class);
	
	@InjectMocks
	AccptrReconciliationProcessor accptrReconciliationProcessor;
	
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
		com.girmiti.nexo.acceptorreconciliationreqest.Document reqDocument = null;
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.009.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrRcncltnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>RCLQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:47:16.814+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <RcncltnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <ParamsVrsn>3</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <RcncltnTxId>\n" + 
				"                    <TxDtTm>2019-12-21T14:47:16.814+05:30</TxDtTm>\n" + 
				"                    <TxRef>4</TxRef>\n" + 
				"                </RcncltnTxId>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxTtls>\n" + 
				"                    <POIGrpId>txn</POIGrpId>\n" + 
				"                    <CardPdctPrfl>yov</CardPdctPrfl>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <Tp>CRDR</Tp>\n" + 
				"                    <TtlNb>10</TtlNb>\n" + 
				"                    <CmltvAmt>1</CmltvAmt>\n" + 
				"                </TxTtls>\n" + 
				"            </Tx>\n" + 
				"        </RcncltnReq>\n" + 
				"    </AccptrRcncltnReq>\n" + 
				"</ns2:Document>";
		reconcilationResponse(reqDocument, reqXml);
	}
	
	@Test
	public void testProcessException() throws Exception {
		com.girmiti.nexo.acceptorreconciliationreqest.Document reqDocument = null;
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.009.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrRcncltnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>RCLQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:47:16.814+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <RcncltnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <ParamsVrsn>3</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <RcncltnTxId>\n" + 
				"                    <TxDtTm>2019-12-21T14:47:16.814+05:30</TxDtTm>\n" + 
				"                    <TxRef></TxRef>\n" + 
				"                </RcncltnTxId>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxTtls>\n" + 
				"                    <POIGrpId>txn</POIGrpId>\n" + 
				"                    <CardPdctPrfl>yov</CardPdctPrfl>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <Tp>CRDR</Tp>\n" + 
				"                    <TtlNb>10</TtlNb>\n" + 
				"                    <CmltvAmt>1</CmltvAmt>\n" + 
				"                </TxTtls>\n" + 
				"            </Tx>\n" + 
				"        </RcncltnReq>\n" + 
				"    </AccptrRcncltnReq>\n" + 
				"</ns2:Document>";
		reconcilationResponse(reqDocument, reqXml);
	}
	
	@Test
	public void testProcessMessageFunction() throws Exception {
		com.girmiti.nexo.acceptorreconciliationreqest.Document reqDocument = null;
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.009.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrRcncltnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:47:16.814+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <RcncltnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <ParamsVrsn>3</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <RcncltnTxId>\n" + 
				"                    <TxDtTm>2019-12-21T14:47:16.814+05:30</TxDtTm>\n" + 
				"                    <TxRef>4</TxRef>\n" + 
				"                </RcncltnTxId>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxTtls>\n" + 
				"                    <POIGrpId>txn</POIGrpId>\n" + 
				"                    <CardPdctPrfl>yov</CardPdctPrfl>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <Tp>CRDR</Tp>\n" + 
				"                    <TtlNb>10</TtlNb>\n" + 
				"                    <CmltvAmt>1</CmltvAmt>\n" + 
				"                </TxTtls>\n" + 
				"            </Tx>\n" + 
				"        </RcncltnReq>\n" + 
				"    </AccptrRcncltnReq>\n" + 
				"</ns2:Document>";
		reconcilationResponse(reqDocument, reqXml);
	}
	
	@Test
	public void testProcessCurrencyError() throws Exception {
		com.girmiti.nexo.acceptorreconciliationreqest.Document reqDocument = null;
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.009.001.07\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrRcncltnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>RCLQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-21T14:47:16.814+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <RcncltnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <ParamsVrsn>3</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <RcncltnTxId>\n" + 
				"                    <TxDtTm>2019-12-21T14:47:16.814+05:30</TxDtTm>\n" + 
				"                    <TxRef>4</TxRef>\n" + 
				"                </RcncltnTxId>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxTtls>\n" + 
				"                    <POIGrpId>txn</POIGrpId>\n" + 
				"                    <CardPdctPrfl>yov</CardPdctPrfl>\n" + 
				"                    <Ccy>NUL</Ccy>\n" + 
				"                    <Tp>CRDR</Tp>\n" + 
				"                    <TtlNb>10</TtlNb>\n" + 
				"                    <CmltvAmt>1</CmltvAmt>\n" + 
				"                </TxTtls>\n" + 
				"            </Tx>\n" + 
				"        </RcncltnReq>\n" + 
				"    </AccptrRcncltnReq>\n" + 
				"</ns2:Document>";
		reconcilationResponse(reqDocument, reqXml);
	}
	
	private void reconcilationResponse(com.girmiti.nexo.acceptorreconciliationreqest.Document reqDocument, String reqXml) {
		try {
			Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorreconciliationreqest.Document.class,reqXml,Constants.CAAA_009_001_07)).thenReturn(reqDocument);
		} catch (FactoryConfigurationError e1) {
			LOGGER.error("ERROR :: AccptrReconciliationProcessorTest method reconcilationResponse", e1);
		} catch (Exception e1) {
			LOGGER.error("ERROR :: AccptrReconciliationProcessorTest method reconcilationResponse", e1);
		}
		String response = "";
		try {
			response = accptrReconciliationProcessor.process(reqXml);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AccptrReconciliationProcessorTest method reconcilationResponse", e);
		}
		Assert.assertNotNull(response);
	}

}