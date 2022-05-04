package com.girmiti.nexo.acquirer.processor.impl;

import javax.xml.stream.FactoryConfigurationError;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.girmiti.nexo.acceptorcompletionadvice.Document;
import com.girmiti.nexo.processor.impl.AcceptorCancellationAdviceProcessor;
import com.girmiti.nexo.util.AcquirerProperties;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AcceptorCancellationAdviceProcessorTest {
	
	@InjectMocks
	AcceptorCancellationAdviceProcessor acceptorCancellationAdviceProcessor;
	
	@Mock
	JaxbHelper jaxbHelper;
	
	@Before
	public void setUp() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("ispgmock", "false");
		AcquirerProperties.mergeProperties(properties);
	}

	@Test
	public void testProcessCurrencyError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCxlAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>CCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-20T09:42:25.452+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>ytdu</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <CxlAdvc>\n" + "            <Envt>\n" + "                <Acqrr>\n"
				+ "                    <Id>\n" + "                        <Id>dgfds</Id>\n"
				+ "                    </Id>\n" + "                    <ParamsVrsn>gdf</ParamsVrsn>\n"
				+ "                </Acqrr>\n" + "                <Mrchnt>\n" + "                    <Id>\n"
				+ "                        <Id>dfgd</Id>\n" + "                    </Id>\n"
				+ "                </Mrchnt>\n" + "                <POI>\n" + "                    <Id>\n"
				+ "                        <Id>fds</Id>\n" + "                        <Tp>ACCP</Tp>\n"
				+ "                        <Issr>ACCP</Issr>\n" + "                    </Id>\n"
				+ "                </POI>\n" + "                <Card>\n" + "                    <PlainCardData>\n"
				+ "                        <PAN>54464456454</PAN>\n"
				+ "                        <XpryDt>2019-09-20</XpryDt>\n" + "                    </PlainCardData>\n"
				+ "                    <CardCtryCd>056</CardCtryCd>\n" + "                </Card>\n"
				+ "            </Envt>\n" + "            <Tx>\n" + "                <MrchntCtgyCd>USD</MrchntCtgyCd>\n"
				+ "                <TxId>\n" + "                    <TxDtTm>2019-12-20T09:42:25.452+05:30</TxDtTm>\n"
				+ "                    <TxRef>ututuyt</TxRef>\n" + "                </TxId>\n"
				+ "                <TxSucss>false</TxSucss>\n" + "                <TxDtls>\n"
				+ "                    <Ccy>BLS</Ccy>\n" + "                    <TtlAmt>1</TtlAmt>\n"
				+ "                </TxDtls>\n" + "            </Tx>\n" + "        </CxlAdvc>\n"
				+ "    </AccptrCxlAdvc>\n" + "</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcess() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlAdvc>\n" + 
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
				"                        <Id>8</Id>\n" + 
				"                        <Tp>ACCP</Tp>\n" + 
				"                        <Issr>ACCP</Issr>\n" + 
				"                    </Id>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>21534545454</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>056</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>852</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n" + 
				"                    <TxRef>5456454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>56464</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlAdvc>\n" + 
				"    </AccptrCxlAdvc>\n" + 
				"</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessIfElse() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCxlAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>CCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>2</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <CxlAdvc>\n" + "            <Envt>\n" + "                <Acqrr>\n"
				+ "                    <Id>\n" + "                        <Id>6</Id>\n" + "                    </Id>\n"
				+ "                    <ParamsVrsn>7</ParamsVrsn>\n" + "                </Acqrr>\n"
				+ "                <Mrchnt>\n" + "                    <Id>\n" + "                        <Id>5</Id>\n"
				+ "                    </Id>\n" + "                </Mrchnt>\n" + "                <POI>\n"
				+ "                    <Id>\n" + "                        <Id>8</Id>\n"
				+ "                        <Tp>ACCP</Tp>\n" + "                        <Issr>ACCP</Issr>\n"
				+ "                    </Id>\n" + "                </POI>\n" + "               <Card>\n"
				+ "                     <PrtctdCardData>  <CnttTp>DGST</CnttTp> <EnvlpdData><Vrsn>0</Vrsn><OrgtrInf><Cert>AAAAZg==</Cert></OrgtrInf><Rcpt><KeyTrnsprt><RcptId><IssrAndSrlNb><Issr><RltvDstngshdNm><AttrTp>OUAT</AttrTp><AttrVal>p:AttrVal</AttrVal></RltvDstngshdNm></Issr><SrlNb>MA==</SrlNb></IssrAndSrlNb></RcptId><KeyNcrptnAlgo><Algo>ERSA</Algo></KeyNcrptnAlgo><NcrptdKey>MA==</NcrptdKey></KeyTrnsprt></Rcpt></EnvlpdData> </PrtctdCardData>"
				+ "              <CardCtryCd>056</CardCtryCd>\n" + "              <CardPdctPrfl>0003</CardPdctPrfl>\n"
				+ "              <CardBrnd>TestCard</CardBrnd>\n" + "            </Card>" + "            </Envt>\n"
				+ "            <Tx>\n" + "                <MrchntCtgyCd>852</MrchntCtgyCd>\n"
				+ "                <TxId>\n" + "                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n"
				+ "                    <TxRef>5456454</TxRef>\n" + "                </TxId>\n"
				+ "                <TxSucss>false</TxSucss>\n" + "                <TxDtls>\n"
				+ "                    <Ccy>USD</Ccy>\n" + "                    <TtlAmt>56464</TtlAmt>\n"
				+ "                </TxDtls>\n" + "            </Tx>\n" + "        </CxlAdvc>\n"
				+ "    </AccptrCxlAdvc>\n" + "</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessIfElseIf() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCxlAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>CCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>2</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <CxlAdvc>\n" + "            <Envt>\n" + "                <Acqrr>\n"
				+ "                    <Id>\n" + "                        <Id>6</Id>\n" + "                    </Id>\n"
				+ "                    <ParamsVrsn>7</ParamsVrsn>\n" + "                </Acqrr>\n"
				+ "                <Mrchnt>\n" + "                    <Id>\n" + "                        <Id>5</Id>\n"
				+ "                    </Id>\n" + "                </Mrchnt>\n" + "                <POI>\n"
				+ "                    <Id>\n" + "                        <Id>8</Id>\n"
				+ "                        <Tp>ACCP</Tp>\n" + "                        <Issr>ACCP</Issr>\n"
				+ "                    </Id>\n" + "                </POI>\n" + "               <Card>\n"
				+ "                     <PrtctdCardData>  <CnttTp>EVLP</CnttTp> <EnvlpdData><Vrsn>0</Vrsn><OrgtrInf><Cert>AAAAZg==</Cert></OrgtrInf><Rcpt><KeyTrnsprt><RcptId><IssrAndSrlNb><Issr><RltvDstngshdNm><AttrTp>OATT</AttrTp><AttrVal>p:AttrVal</AttrVal></RltvDstngshdNm></Issr><SrlNb>MA==</SrlNb></IssrAndSrlNb></RcptId><KeyNcrptnAlgo><Algo>ERSA</Algo></KeyNcrptnAlgo><NcrptdKey>MA==</NcrptdKey></KeyTrnsprt></Rcpt></EnvlpdData> </PrtctdCardData>"
				+ "              <CardCtryCd>056</CardCtryCd>\n" + "              <CardPdctPrfl>0003</CardPdctPrfl>\n"
				+ "              <CardBrnd>TestCard</CardBrnd>\n" + "            </Card>" + "            </Envt>\n"
				+ "            <Tx>\n" + "                <MrchntCtgyCd>852</MrchntCtgyCd>\n"
				+ "                <TxId>\n" + "                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n"
				+ "                    <TxRef>5456454</TxRef>\n" + "                </TxId>\n"
				+ "                <TxSucss>false</TxSucss>\n" + "                <TxDtls>\n"
				+ "                    <Ccy>USD</Ccy>\n" + "                    <TtlAmt>56464</TtlAmt>\n"
				+ "                </TxDtls>\n" + "            </Tx>\n" + "        </CxlAdvc>\n"
				+ "    </AccptrCxlAdvc>\n" + "</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessIfElseIfElse() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCxlAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>CCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>2</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <CxlAdvc>\n" + "            <Envt>\n" + "                <Acqrr>\n"
				+ "                    <Id>\n" + "                        <Id>6</Id>\n" + "                    </Id>\n"
				+ "                    <ParamsVrsn>7</ParamsVrsn>\n" + "                </Acqrr>\n"
				+ "                <Mrchnt>\n" + "                    <Id>\n" + "                        <Id>5</Id>\n"
				+ "                    </Id>\n" + "                </Mrchnt>\n" + "                <POI>\n"
				+ "                    <Id>\n" + "                        <Id>8</Id>\n"
				+ "                        <Tp>ACCP</Tp>\n" + "                        <Issr>ACCP</Issr>\n"
				+ "                    </Id>\n" + "                </POI>\n" + "               <Card>\n"
				+ "                     <PrtctdCardData>  <CnttTp>SIGN</CnttTp> <EnvlpdData><Vrsn>0</Vrsn><OrgtrInf><Cert>AAAAZg==</Cert></OrgtrInf><Rcpt><KeyTrnsprt><RcptId><IssrAndSrlNb><Issr><RltvDstngshdNm><AttrTp>LATT</AttrTp><AttrVal>p:AttrVal</AttrVal></RltvDstngshdNm></Issr><SrlNb>MA==</SrlNb></IssrAndSrlNb></RcptId><KeyNcrptnAlgo><Algo>ERSA</Algo></KeyNcrptnAlgo><NcrptdKey>MA==</NcrptdKey></KeyTrnsprt></Rcpt></EnvlpdData> </PrtctdCardData>"
				+ "              <CardCtryCd>056</CardCtryCd>\n" + "              <CardPdctPrfl>0003</CardPdctPrfl>\n"
				+ "              <CardBrnd>TestCard</CardBrnd>\n" + "            </Card>" + "            </Envt>\n"
				+ "            <Tx>\n" + "                <MrchntCtgyCd>852</MrchntCtgyCd>\n"
				+ "                <TxId>\n" + "                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n"
				+ "                    <TxRef>5456454</TxRef>\n" + "                </TxId>\n"
				+ "                <TxSucss>false</TxSucss>\n" + "                <TxDtls>\n"
				+ "                    <Ccy>USD</Ccy>\n" + "                    <TtlAmt>56464</TtlAmt>\n"
				+ "                </TxDtls>\n" + "            </Tx>\n" + "        </CxlAdvc>\n"
				+ "    </AccptrCxlAdvc>\n" + "</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessIf() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCxlAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>CCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>2</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <CxlAdvc>\n" + "            <Envt>\n" + "                <Acqrr>\n"
				+ "                    <Id>\n" + "                        <Id>6</Id>\n" + "                    </Id>\n"
				+ "                    <ParamsVrsn>7</ParamsVrsn>\n" + "                </Acqrr>\n"
				+ "                <Mrchnt>\n" + "                    <Id>\n" + "                        <Id>5</Id>\n"
				+ "                    </Id>\n" + "                </Mrchnt>\n" + "                <POI>\n"
				+ "                    <Id>\n" + "                        <Id>8</Id>\n"
				+ "                        <Tp>ACCP</Tp>\n" + "                        <Issr>ACCP</Issr>\n"
				+ "                    </Id>\n" + "                </POI>\n" + "               <Card>\n"
				+ "                     <PrtctdCardData>  <CnttTp>AUTH</CnttTp> <EnvlpdData><Vrsn>0</Vrsn><OrgtrInf><Cert>AAAAZg==</Cert></OrgtrInf><Rcpt><KeyTrnsprt><RcptId><IssrAndSrlNb><Issr><RltvDstngshdNm><AttrTp>CNAT</AttrTp><AttrVal>p:AttrVal</AttrVal></RltvDstngshdNm></Issr><SrlNb>MA==</SrlNb></IssrAndSrlNb></RcptId><KeyNcrptnAlgo><Algo>ERSA</Algo></KeyNcrptnAlgo><NcrptdKey>MA==</NcrptdKey></KeyTrnsprt></Rcpt></EnvlpdData> </PrtctdCardData>"
				+ "              <CardCtryCd>056</CardCtryCd>\n" + "              <CardPdctPrfl>0003</CardPdctPrfl>\n"
				+ "              <CardBrnd>TestCard</CardBrnd>\n" + "            </Card>" + "            </Envt>\n"
				+ "            <Tx>\n" + "                <MrchntCtgyCd>852</MrchntCtgyCd>\n"
				+ "                <TxId>\n" + "                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n"
				+ "                    <TxRef>5456454</TxRef>\n" + "                </TxId>\n"
				+ "                <TxSucss>false</TxSucss>\n" + "                <TxDtls>\n"
				+ "                    <Ccy>USD</Ccy>\n" + "                    <TtlAmt>56464</TtlAmt>\n"
				+ "                </TxDtls>\n" + "            </Tx>\n" + "        </CxlAdvc>\n"
				+ "    </AccptrCxlAdvc>\n" + "</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessMessageFunction() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>AUTQ</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlAdvc>\n" + 
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
				"                        <Id>8</Id>\n" + 
				"                        <Tp>ACCP</Tp>\n" + 
				"                        <Issr>ACCP</Issr>\n" + 
				"                    </Id>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>21534545454</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>056</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>852</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n" + 
				"                    <TxRef>5456454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>56464</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlAdvc>\n" + 
				"    </AccptrCxlAdvc>\n" + 
				"</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCapabilities() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlAdvc>\n" + 
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
				"                        <Id>8</Id>\n" + 
				"                        <Tp>ACCP</Tp>\n" + 
				"                        <Issr>ACCP</Issr>\n" + 
				"                    </Id>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardCaptrCpbl>false</CardCaptrCpbl>\n" + 
				"                    </Cpblties>\n" +
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>21534545454</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>056</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>852</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n" + 
				"                    <TxRef>5456454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>56464</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlAdvc>\n" + 
				"    </AccptrCxlAdvc>\n" + 
				"</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessContextError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlAdvc>\n" + 
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
				"                        <Id>8</Id>\n" + 
				"                        <Tp>ACCP</Tp>\n" + 
				"                        <Issr>ACCP</Issr>\n" + 
				"                    </Id>\n" + 
				"                    <Cpblties>\n" + 
				"                        <CardCaptrCpbl>false</CardCaptrCpbl>\n" + 
				"                    </Cpblties>\n" +
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>21534545454</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>056</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Cntxt>\n" + 
				"            	<SaleCntxt>\n" + 
				"            		<AddtlSaleData>545456</AddtlSaleData>\n" + 
				"            	</SaleCntxt>\n" + 
				"            </Cntxt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>852</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n" + 
				"                    <TxRef>5456454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>56464</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlAdvc>\n" + 
				"    </AccptrCxlAdvc>\n" + 
				"</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlAdvc>\n" + 
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
				"                        <Id>8</Id>\n" + 
				"                        <Tp>ACCP</Tp>\n" + 
				"                        <Issr>ACCP</Issr>\n" + 
				"                    </Id>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>21534545454</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>QWE</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>852</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T14:47:37.152+05:30dvddfgdfg</TxDtTm>\n" + 
				"                    <TxRef>5456454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>56464</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlAdvc>\n" + 
				"    </AccptrCxlAdvc>\n" + 
				"</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCountryCodeError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.007.001.08\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"    <AccptrCxlAdvc>\n" + 
				"        <Hdr>\n" + 
				"            <MsgFctn>CCAV</MsgFctn>\n" + 
				"            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + 
				"            <XchgId>149</XchgId>\n" + 
				"            <CreDtTm>2019-12-24T14:47:37.152+05:30</CreDtTm>\n" + 
				"            <InitgPty>\n" + 
				"                <Id>2</Id>\n" + 
				"            </InitgPty>\n" + 
				"        </Hdr>\n" + 
				"        <CxlAdvc>\n" + 
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
				"                        <Id>8</Id>\n" + 
				"                        <Tp>ACCP</Tp>\n" + 
				"                        <Issr>ACCP</Issr>\n" + 
				"                    </Id>\n" + 
				"                </POI>\n" + 
				"                <Card>\n" + 
				"                    <PlainCardData>\n" + 
				"                        <PAN>21534545454</PAN>\n" + 
				"                        <XpryDt>2020-03-03</XpryDt>\n" + 
				"                    </PlainCardData>\n" + 
				"                    <CardCtryCd>QWE</CardCtryCd>\n" + 
				"                </Card>\n" + 
				"            </Envt>\n" + 
				"            <Tx>\n" + 
				"                <MrchntCtgyCd>852</MrchntCtgyCd>\n" + 
				"                <TxId>\n" + 
				"                    <TxDtTm>2019-12-24T14:47:37.152+05:30</TxDtTm>\n" + 
				"                    <TxRef>5456454</TxRef>\n" + 
				"                </TxId>\n" + 
				"                <TxSucss>false</TxSucss>\n" + 
				"                <TxDtls>\n" + 
				"                    <Ccy>USD</Ccy>\n" + 
				"                    <TtlAmt>56464</TtlAmt>\n" + 
				"                </TxDtls>\n" + 
				"            </Tx>\n" + 
				"        </CxlAdvc>\n" + 
				"    </AccptrCxlAdvc>\n" + 
				"</ns2:Document>";
		acceptorCancellationAdviceRespopnse(acceptorAuthReqXmlRecr);
	}

	private void acceptorCancellationAdviceRespopnse(String acceptorAuthReqXmlRecr)
			throws FactoryConfigurationError, Exception {
		Document acceptorCancellationAdviceDocument = null;
		Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class, acceptorAuthReqXmlRecr,
				Constants.CAAA_001_001_08)).thenReturn(acceptorCancellationAdviceDocument);
		String process = acceptorCancellationAdviceProcessor.process(acceptorAuthReqXmlRecr);
		Assert.assertNotNull(process);
	}

}
