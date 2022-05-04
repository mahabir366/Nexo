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
public class AuthTransactionProcessorTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTransactionProcessorTest.class);
	
	@InjectMocks
	AuthTransactionProcessor authTransactionProcessor;
	
	@Mock
	JaxbHelper jaxbHelper;
	
	@Before
	public void setUpProperties() throws IOException {
		java.util.Properties pro = new java.util.Properties();
		pro.setProperty("ispgmock", "false");
		AcquirerProperties.mergeProperties(pro);
	}
	
	@Test
	public void testProcess() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessDa12() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DA12</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessda19() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DA19</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessDa25() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DA25</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessE36C() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E36C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessE36R() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E36R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessE3DC() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E3DC</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessE3DR() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E3DR</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessEA2C() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA2C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessEA2R() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA2R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessEA5C() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessEA5R() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessDGST() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>DGST</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessDATA() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>DATA</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessSign() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FAUQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>SIGN</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20-200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessAuth() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>AUTH</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessEA9C() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA9C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessEA9R() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA9R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessSd5c() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>SD5C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessN108() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>N108</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessUka1() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>UKA1</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessUkpt() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"<PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>UKPT</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>"+
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureStatusTrue() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessNUllCurrency() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>NUL</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessMessageFunction() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTP</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>jfghhjfj</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.001.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrAuthstnReq>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>1</PrtcolVrsn>\n" + 
				"            <XchgId>3</XchgId>\n" + 
				"            <CreDtTm>2019-12-20T17:39:07.554+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <AuthstnReq>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>6</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>125151531453454</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>4</Id>\n" + 
				"                    </Id>\n" + 
				"                    <Cmpnt>\n" + 
				"                        <Tp>APPR</Tp>\n" + 
				"                        <Id>\n" + 
				"                            <ItmNb>57</ItmNb>\n" + 
				"                            <PrvdrId>provided01</PrvdrId>\n" + 
				"                            <Id>terminal33</Id>\n" + 
				"                            <SrlNb>545454454546756</SrlNb>\n" + 
				"                        </Id>\n" + 
				"                    </Cmpnt>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>5454544564564545</PAN>\n" + 
				"                        <XpryDt>20200303</XpryDt>\n" + 
				"                        <SvcCd>369</SvcCd>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCcyCd>USA</CardCcyCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"                <PmtCntxt>\n" + 
				"                    <CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"                </PmtCntxt>\n" + 
				"                <SaleCntxt>\n" + 
				"                    <AllwdNtryMd></AllwdNtryMd>\n" + 
				"                </SaleCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>121</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-20T17:39:07.554+05:30</TxDtTm>\n" + 
				"                    <TxRef>12345</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <RcncltnId>8</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>123</TtlAmt>\n" + 
				"                    <DtldAmt>\n" + 
				"                		<AmtGoodsAndSvcs>8123</AmtGoodsAndSvcs>\n" + 
				"                    </DtldAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </AuthstnReq>\n" + 
				"    </AccptrAuthstnReq>\n" + 
				"</ns2:Document>";
		authTransactionResponse(acceptorAuthReqXmlRecr);
	}

	private void authTransactionResponse(String acceptorAuthReqXmlRecr) {
		com.girmiti.nexo.acceptorauthorizationrequest.Document authReqDocument = null;
		try {
			Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class,
					acceptorAuthReqXmlRecr, Constants.CAAA_001_001_08)).thenReturn(authReqDocument);
		} catch (FactoryConfigurationError e) {
			LOGGER.error("ERROR :: AuthTransactionProcessorTest method authTransactionResponse", e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AuthTransactionProcessorTest method authTransactionResponse", e);
		}
		String response = "";
		try {
			response = authTransactionProcessor.process(acceptorAuthReqXmlRecr);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AuthTransactionProcessorTest method authTransactionResponse", e);
		}
		Assert.assertNotNull(response);
	}

	private void authTransactionResponseWithNull(String acceptorAuthReqXmlRecr) {
		com.girmiti.nexo.acceptorauthorizationrequest.Document authReqDocument = null;
		try {
			Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class,
					acceptorAuthReqXmlRecr, Constants.CAAA_001_001_08)).thenReturn(authReqDocument);
		} catch (FactoryConfigurationError e) {
			LOGGER.error("ERROR :: AuthTransactionProcessorTest method authTransactionResponse", e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AuthTransactionProcessorTest method authTransactionResponse", e);
		}
		String response = "";
		try {
			response = authTransactionProcessor.process(acceptorAuthReqXmlRecr);
		} catch (Exception e) {
			LOGGER.error("ERROR :: AuthTransactionProcessorTest method authTransactionResponse", e);
		}
		Assert.assertNull(response);
	}

}
