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
import com.girmiti.nexo.processor.impl.CompletionTransactionProcessor;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CompletionTransactionProcessorTest {

	@InjectMocks
	CompletionTransactionProcessor completionTransactionProcessor;

	@Mock
	JaxbHelper jaxbHelper;

	@Test
	public void testProcessExpiryDateError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\"\n" + "xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\"\n"
				+ "xmlns:ns2=\"http://Document/bar\"\n" + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrCmpltnAdvc>\n" + "<Hdr>\n" + "<MsgFctn>FCMV</MsgFctn>\n" + "<PrtcolVrsn>132</PrtcolVrsn>\n"
				+ "<XchgId>149</XchgId>\n" + "<CreDtTm>2019-01-09T11:15:45.377+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>3210000021</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<CmpltnAdvc>\n" + "<Envt>\n" + "<POI>\n"
				+ "<Id>\n" + "<Id>032150</Id>\n" + "</Id>\n" + "<SysNm>1234567890</SysNm>\n" + "<Cpblties>\n"
				+ "<CardRdngCpblties>TAGC</CardRdngCpblties>\n" + "<CardRdngCpblties>PHYS</CardRdngCpblties>\n"
				+ "<CardRdngCpblties>BRCD</CardRdngCpblties>\n" + "<CardRdngCpblties>CICC</CardRdngCpblties>\n"
				+ "<CardRdngCpblties>CTLS</CardRdngCpblties>\n" + "<CardRdngCpblties>DFLE</CardRdngCpblties>\n"
				+ "<CardRdngCpblties>CTLS</CardRdngCpblties>\n" + "<CardRdngCpblties>ECTL</CardRdngCpblties>\n"
				+ "<CardRdngCpblties>CDFL</CardRdngCpblties>\n" + "</Cpblties>\n" + "</POI>\n" + "<Card>\n"
				+ "<PlainCardData>\n" + "<PAN>1111244455547276036</PAN>\n" + "<XpryDt>2021-90-93</XpryDt>\n"
				+ "</PlainCardData>\n" + "<CardCtryCd>USA</CardCtryCd>\n" + "</Card>\n" + "</Envt>\n" + "<Tx>\n"
				+ "<TxCaptr>false</TxCaptr>\n" + "<TxTp>CRDP</TxTp>\n" + "<MrchntCtgyCd>982</MrchntCtgyCd>\n"
				+ "<TxId>\n" + "<TxDtTm>2019-12-09T11:15:45.377+05:30</TxDtTm>\n" + "<TxRef>200262</TxRef>\n"
				+ "</TxId>\n" + "<TxSucss>false</TxSucss>\n" + "<RcncltnId>12</RcncltnId>\n" + "<TxDtls>\n"
				+ "<Ccy>ALL</Ccy>\n" + "<TtlAmt>2090</TtlAmt>\n" + "</TxDtls>\n"
				+ "<MrchntRefData>155058594833186</MrchntRefData>\n" + "</Tx>\n" + "</CmpltnAdvc>\n"
				+ "</AccptrCmpltnAdvc>\n" + "</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessMessageFunction() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTP</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcess() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCapturDGST() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>DGST</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureSign() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>SIGN</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureAUTH() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>AUTH</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureData() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>DATA</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DKP9</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrData() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DA12</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElse() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DA19</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseDA25() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>DA25</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseE36D() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E36C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseE36R() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E36R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseE3DC() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E3DC</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseE3DR() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>E3DR</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseEA2C() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA2C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseEA2R() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA2R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseEA5C() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseEA5R() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA5R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseEA9R() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA9R</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseEA9C() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>EA9C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseSD5C() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>SD5C</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseN108() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>N108</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseUKPT() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>UKPT</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCaptureErrorWithPtrDataWithElseUKA1() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"   <PrtctdCardData> <CnttTp>EVLP</CnttTp> <EnvlpdData> <Rcpt> <KEK> <KEKId> <KeyId>SpecV1TestKey</KeyId> <KeyVrsn>2010060715</KeyVrsn> <DerivtnId>OYclpQE=</DerivtnId> </KEKId> <KeyNcrptnAlgo> <Algo>UKA1</Algo> </KeyNcrptnAlgo> <NcrptdKey>4pAgABc=</NcrptdKey> </KEK> </Rcpt> </EnvlpdData> </PrtctdCardData>" +
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>true</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	
	@Test
	public void testProcessCurrencyError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>USA</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>QWE</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCountryError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>QWE</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.003.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCmpltnAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>FCMV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T17:30:11.024+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CmpltnAdvc>\n" + 
				"            <Envt>\n" + 
				"                <Acqrr>\n" + 
				"                    <Id>\n" + 
				"                        <Id>6</Id>\n" + 
				"                    </Id>\n" + 
				"                    <ParamsVrsn>7</ParamsVrsn>\n" + 
				"                </Acqrr>\n" + 
				"                <Mrchnt>\n" + 
				"                    <Id>\n" + 
				"                        <Id>5</Id>\n" + 
				"                    </Id>\n" + 
				"                </Mrchnt>\n" + 
				"                <POI>\n" + 
				"                    <Id>\n" + 
				"                        <Id>3</Id>\n" + 
				"                    </Id>\n" + 
				"                    <SysNm>4545454545454</SysNm>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardRdngCpblties>TAGC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>BRCD</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>DFLE</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CTLS</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"                        <CardRdngCpblties>CDFL</CardRdngCpblties>\n" + 
				"                    </Cpblties>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>213514545345453453</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>QWE</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"                <Crdhldr>\n" + 
				"                    <Nm>Ashutosh</Nm>\n" + 
				"                </Crdhldr>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <TxCaptr>false</TxCaptr>\n" + 
				"                <TxTp>CRDP</TxTp>\n" + 
				"                <MrchntCtgyCd>444</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T17:30:11.024+05:30</TxDtTm>\n" + 
				"                    <TxRef>5453454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <FailrRsn>false</FailrRsn>\n" + 
				"                <RcncltnId>5</RcncltnId>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>1595</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"                <MrchntRefData>453453453453454534536453645</MrchntRefData>\n" + 
				"            </Tx>\n" + 
				"        </CmpltnAdvc>\n" + 
				"    </AccptrCmpltnAdvc>\n" + 
				"</ns2:Document>";
		completionTransactionResponse(acceptorAuthReqXmlRecr);
	}

	private void completionTransactionResponse(String acceptorAuthReqXmlRecr)
			throws FactoryConfigurationError, Exception {
		Document authReqDocument = null;
		Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class, acceptorAuthReqXmlRecr,
				Constants.CAAA_001_001_08)).thenReturn(authReqDocument);
		String process = completionTransactionProcessor.process(acceptorAuthReqXmlRecr);
		Assert.assertNotNull(process);
	}

}
