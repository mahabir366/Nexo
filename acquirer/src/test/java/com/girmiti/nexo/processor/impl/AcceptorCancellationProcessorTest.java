package com.girmiti.nexo.processor.impl;

import java.io.IOException;

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
public class AcceptorCancellationProcessorTest {

	@InjectMocks
	AcceptorCancellationProcessor acceptorCancellationProcessor;

	@Mock
	JaxbHelper jaxbHelper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptorCancellationProcessorTest.class);
	
	@Before
	public void setUpProperties() throws IOException {
		java.util.Properties pro = new java.util.Properties();
		pro.setProperty("ispgmock", "false");
		AcquirerProperties.mergeProperties(pro);
	}
	
	@Test
	public void testProcess() {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>XXM</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>msg\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>26364</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>26364</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessException() {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30gnfg</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>99</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>26364</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>26364</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessMessageFunction() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>99</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>26364</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>26364</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessISCurrencyNull() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>200303</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>200301</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>NUL</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessForNullMerchant() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>200303</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>200301</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessForContextError() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>XXM</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>true</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>26364</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>26364</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessForSalesError() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>XXM</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>true</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"                <SaleCntxt>\n" + 
				"                </SaleCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>26364</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>26364</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessForCaptureStatusFalse() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>XXM</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>210598</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>200301</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessForCaptureStatusTrue() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>XXM</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>200303</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>200301</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	@Test
	public void testProcessForCardCountryCode() throws Exception {
		String acceptorCancellationReqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.005.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T12:09:09.384+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlReq>\n" + 
				"            <Envt>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>XXM</Id>\n" + 
				"                        <Tp>MERC</Tp>\n" + 
				"                        <Issr>MERC</Issr>\n" + 
				"                        <ShrtNm>AllMechant</ShrtNm>\n" + 
				"                    </Id>\n" + 
				"                    <CmonNm>Merchant</CmonNm>\n" + 
				"                    <LctnCtgy>FIXD</LctnCtgy>\n" + 
				"                    <SchmeData>123456789265465</SchmeData>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>65466545</SysNm>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>123456789</PAN>\n" + 
				"                        <CardSeqNb>99</CardSeqNb>\n" + 
				"                        <FctvDt>Card</FctvDt>\n" + 
				"                        <XpryDt>2028</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>123</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <AttndntMsgCpbl>false</AttndntMsgCpbl>\n" + 
				"                    <CardDataNtryMd>CDFL</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>111</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                    <TxRef>26364</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <OrgnlTx>\n" + 
				"                    <TxId>\n" + 
				"                        <TxDtTm>2019-12-20T12:09:09.384+05:30</TxDtTm>\n" + 
				"                        <TxRef>26364</TxRef>\n" + 
				"                    </TxId>\n" + 
				"                    <TxTp>BALC</TxTp>\n" + 
				"                </OrgnlTx>\n" + 
				"                <RcncltnId>7</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1234</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlReq>\n" + 
				"    </AccptrCxlReq>\n" + 
				"</ns2:Document>";
		acceptorCancellationResponse(acceptorCancellationReqxml);
	}
	
	private void acceptorCancellationResponse(String acceptorCancellationReqxml) {
		com.girmiti.nexo.acceptorcancellationreqest.Document acceptorcancelationDocument = null;
		try {
			Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorcancellationreqest.Document.class,
					acceptorCancellationReqxml, Constants.CAAA_005_001_08)).thenReturn(acceptorcancelationDocument);
		} catch (FactoryConfigurationError e) {
			LOGGER.error("ERROR :: AcceptorCancellationProcessorTest method acceptorCancellationResponse1", e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AcceptorCancellationProcessorTest method acceptorCancellationResponse2", e);
		}
		String response = null;
		try {
			response = acceptorCancellationProcessor.process(acceptorCancellationReqxml);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AcceptorCancellationProcessorTest method acceptorCancellationResponse3", e);
		}
		Assert.assertNotNull(response);
	}

}