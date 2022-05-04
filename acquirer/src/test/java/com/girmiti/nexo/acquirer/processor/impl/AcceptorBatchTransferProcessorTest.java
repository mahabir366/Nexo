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
import com.girmiti.nexo.processor.impl.AcceptorBatchTransferProcessor;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AcceptorBatchTransferProcessorTest {

	@InjectMocks
	AcceptorBatchTransferProcessor acceptorBatchTransferProcessor;
	@Mock
	JaxbHelper jaxbHelper;

	@Test
	public void testProcess() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"<AccptrBtchTrf>\n" + 
				"<Hdr>\n" + 
				"<DwnldTrf>true</DwnldTrf>\n" + 
				"<FrmtVrsn>3432</FrmtVrsn>\n" + 
				"<XchgId>5235</XchgId>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"<InitgPty>\n" + 
				"<Id>23523</Id>\n" + 
				"</InitgPty>\n" + 
				"</Hdr>\n" + 
				"<BtchTrf>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<DataSet>\n" + 
				"<DataSetId>\n" + 
				"<Nm>xyz</Nm>\n" + 
				"<Tp>TXCP</Tp>\n" + 
				"<Vrsn>21</Vrsn>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"</DataSetId>\n" + 
				"<DataSetInitr>\n" + 
				"<Id>23523</Id>\n" + 
				"</DataSetInitr>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<CmonData>\n" + 
				"<Envt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>cony</Id>\n" + 
				"</Id>\n" + 
				"<GrpId>mon</GrpId>\n" + 
				"</POI>\n" + 
				"</Envt>\n" + 
				"<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"</CmonData>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cxl>\n" + 
				"<TxSeqCntr>1</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>01</CardSeqNb>\n" + 
				"<FctvDt>2012-01</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>FRA</CardCtryCd>\n" + 
				"<CardPdctPrfl>0017</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<OnLineCntxt>false</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + 
				"<TxRef>130220</TxRef>\n" + 
				"</TxId>\n" + 
				"<OrgnlTx>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + 
				"<TxRef>130213</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<TxRslt>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</TxRslt>\n" + 
				"</OrgnlTx>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>2000</TtlAmt>\n" + 
				"<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"</Tx>\n" + 
				"</Cxl>\n" + 
				"</Tx>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cmpltn>\n" + 
				"<TxSeqCntr>3</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"<Cpblties>\n" + 
				"<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + 
				"<OnLineCpblties>SMON</OnLineCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MDSP</Dstn>\n" + 
				"<NbOfLines>8</NbOfLines>\n" + 
				"<LineWidth>15</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MRCP</Dstn>\n" + 
				"<LineWidth>24</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"</Cpblties>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>TERM</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>ICT250</Id>\n" + 
				"<SrlNb>1122334</SrlNb>\n" + 
				"</Id>\n" + 
				"</Cmpnt>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>APLI</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"</Id>\n" + 
				"<Sts>\n" + 
				"<VrsnNb>04.10</VrsnNb>\n" + 
				"</Sts>\n" + 
				"<StdCmplc>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"<Vrsn>3.1</Vrsn>\n" + 
				"<Issr>OSCAR</Issr>\n" + 
				"</StdCmplc>\n" + 
				"<Assmnt>\n" + 
				"<Tp>CERT</Tp>\n" + 
				"<Assgnr>OSCAR</Assgnr>\n" + 
				"<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n" + 
				"<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + 
				"<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n" + 
				"</Assmnt>\n" + 
				"</Cmpnt>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>00</CardSeqNb>\n" + 
				"<FctvDt>2010-12</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>DEU</CardCtryCd>\n" + 
				"<CardPdctPrfl>0018</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<CrdhldrPres>true</CrdhldrPres>\n" + 
				"<OnLineCntxt>true</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"<SaleCntxt>\n" + 
				"<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + 
				"<CshrId>00000001</CshrId>\n" + 
				"</SaleCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n" + 
				"<TxRef>130218</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<Rvsl>false</Rvsl>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>1000</TtlAmt>\n" + 
				"<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"<AuthstnRslt>\n" + 
				"<AuthstnNtty>\n" + 
				"<Tp>CISS</Tp>\n" + 
				"</AuthstnNtty>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</AuthstnRslt>\n" + 
				"</Tx>\n" + 
				"</Cmpltn>\n" + 
				"</Tx>\n" + 
				"</DataSet>\n" + 
				"</BtchTrf>\n" + 
				"</AccptrBtchTrf>\n" + 
				"</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessInvalidCurrency() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrBtchTrf>\n" + "<Hdr>\n" + "<DwnldTrf>true</DwnldTrf>\n" + "<FrmtVrsn>3432</FrmtVrsn>\n"
				+ "<XchgId>5235</XchgId>\n" + "<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>23523</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<BtchTrf>\n" + "<TxTtls>\n"
				+ "<POIGrpId>uit</POIGrpId>\n" + "<CardPdctPrfl>card</CardPdctPrfl>\n" + "<Ccy>ABC</Ccy>\n"
				+ "<Tp>CRDT</Tp>\n" + "<TtlNb>10</TtlNb>\n" + "<CmltvAmt>1</CmltvAmt>\n" + "</TxTtls>\n" + "<DataSet>\n"
				+ "<DataSetId>\n" + "<Nm>xyz</Nm>\n" + "<Tp>TXCP</Tp>\n" + "<Vrsn>21</Vrsn>\n"
				+ "<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + "</DataSetId>\n" + "<DataSetInitr>\n"
				+ "<Id>23523</Id>\n" + "</DataSetInitr>\n" + "<TxTtls>\n" + "<POIGrpId>uit</POIGrpId>\n"
				+ "<CardPdctPrfl>card</CardPdctPrfl>\n" + "<Ccy>USD</Ccy>\n" + "<Tp>CRDT</Tp>\n" + "<TtlNb>10</TtlNb>\n"
				+ "<CmltvAmt>1</CmltvAmt>\n" + "</TxTtls>\n" + "<CmonData>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>cony</Id>\n" + "</Id>\n" + "<GrpId>mon</GrpId>\n" + "</POI>\n" + "</Envt>\n"
				+ "<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + "<Ccy>USD</Ccy>\n" + "</CmonData>\n" + "\n" + "<Tx>\n"
				+ "<Cxl>\n" + "<TxSeqCntr>1</TxSeqCntr>\n" + "<Envt>\n" + "<Acqrr>\n" + "<Id>\n"
				+ "<Id>000000000123</Id>\n" + "</Id>\n" + "<ParamsVrsn>20110913123456</ParamsVrsn>\n" + "</Acqrr>\n"
				+ "<Mrchnt>\n" + "<Id>\n" + "<Id>666315366631671</Id>\n" + "</Id>\n"
				+ "<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + "</Mrchnt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>66631671</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276037</PAN>\n" + "<CardSeqNb>01</CardSeqNb>\n" + "<FctvDt>2012-01</FctvDt>\n"
				+ "<XpryDt>2200-03-03</XpryDt>\n" + "<SvcCd>982</SvcCd>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>FRA</CardCtryCd>\n" + "<CardPdctPrfl>0017</CardPdctPrfl>\n" + "<CardBrnd>CB</CardBrnd>\n"
				+ "</Card>\n" + "</Envt>\n" + "<Cntxt>\n" + "<PmtCntxt>\n" + "<OnLineCntxt>false</OnLineCntxt>\n"
				+ "<AttndncCntxt>ATTD</AttndncCntxt>\n" + "<CardDataNtryMd>CICC</CardDataNtryMd>\n" + "</PmtCntxt>\n"
				+ "</Cntxt>	 \n" + "<Tx>\n" + "<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + "<TxId>\n"
				+ "<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + "<TxRef>130220</TxRef>\n" + "</TxId>\n" + "<OrgnlTx>\n"
				+ "<TxId>\n" + "<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + "<TxRef>130213</TxRef>\n" + "</TxId>\n"
				+ "<TxTp>CRDP</TxTp>\n" + "<TxRslt>\n" + "<RspnToAuthstn>\n" + "<Rspn>APPR</Rspn>\n"
				+ "</RspnToAuthstn>\n" + "<AuthstnCd>032983</AuthstnCd>\n" + "</TxRslt>\n" + "</OrgnlTx>\n"
				+ "<TxSucss>true</TxSucss>\n" + "<TxDtls>\n" + "<Ccy>EUR</Ccy>\n" + "<TtlAmt>2016</TtlAmt>\n"
				+ "<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + "</TxDtls>\n"
				+ "</Tx>\n" + "</Cxl>\n" + "</Tx>\n" + "\n" + "<Tx>\n" + "<Cmpltn>\n" + "<TxSeqCntr>3</TxSeqCntr>\n"
				+ "<Envt>\n" + "<Acqrr>\n" + "<Id>\n" + "<Id>000000000123</Id>\n" + "</Id>\n"
				+ "<ParamsVrsn>20110913123456</ParamsVrsn>\n" + "</Acqrr>\n" + "<Mrchnt>\n" + "<Id>\n"
				+ "<Id>666315366631671</Id>\n" + "</Id>\n" + "<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n"
				+ "</Mrchnt>\n" + "<POI>\n" + "<Id>\n" + "<Id>66631671</Id>\n" + "</Id>\n" + "<Cpblties>\n"
				+ "<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + "<CardRdngCpblties>MGST</CardRdngCpblties>\n"
				+ "<CardRdngCpblties>CICC</CardRdngCpblties>\n" + "<CardRdngCpblties>ECTL</CardRdngCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + "<OnLineCpblties>SMON</OnLineCpblties>\n"
				+ "<MsgCpblties>\n" + "<Dstn>MDSP</Dstn>\n" + "<NbOfLines>8</NbOfLines>\n"
				+ "<LineWidth>15</LineWidth>\n" + "</MsgCpblties>\n" + "<MsgCpblties>\n" + "<Dstn>MRCP</Dstn>\n"
				+ "<LineWidth>24</LineWidth>\n" + "</MsgCpblties>\n" + "</Cpblties>\n" + "<Cmpnt>\n" + "<Tp>TERM</Tp>\n"
				+ "<Id>\n" + "<PrvdrId>INGENICO</PrvdrId>\n" + "<Id>ICT250</Id>\n" + "<SrlNb>1122334</SrlNb>\n"
				+ "</Id>\n" + "</Cmpnt>\n" + "<Cmpnt>\n" + "<Tp>APLI</Tp>\n" + "<Id>\n"
				+ "<PrvdrId>INGENICO</PrvdrId>\n" + "<Id>SEPA-FAST</Id>\n" + "</Id>\n" + "<Sts>\n"
				+ "<VrsnNb>04.10</VrsnNb>\n" + "</Sts>\n" + "<StdCmplc>\n" + "<Id>SEPA-FAST</Id>\n"
				+ "<Vrsn>3.1</Vrsn>\n" + "<Issr>OSCAR</Issr>\n" + "</StdCmplc>\n" + "<Assmnt>\n" + "<Tp>CERT</Tp>\n"
				+ "<Assgnr>OSCAR</Assgnr>\n" + "<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n"
				+ "<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + "<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n"
				+ "</Assmnt>\n" + "</Cmpnt>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276037</PAN>\n" + "<CardSeqNb>00</CardSeqNb>\n" + "<FctvDt>2010-12</FctvDt>\n"
				+ "<XpryDt>2200-03-03</XpryDt>\n" + "<SvcCd>982</SvcCd>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>DEU</CardCtryCd>\n" + "<CardPdctPrfl>0018</CardPdctPrfl>\n" + "<CardBrnd>CB</CardBrnd>\n"
				+ "</Card>\n" + "</Envt>\n" + "<Cntxt>\n" + "<PmtCntxt>\n" + "<CrdhldrPres>true</CrdhldrPres>\n"
				+ "<OnLineCntxt>true</OnLineCntxt>\n" + "<AttndncCntxt>ATTD</AttndncCntxt>\n"
				+ "<CardDataNtryMd>CICC</CardDataNtryMd>\n" + "</PmtCntxt>\n" + "<SaleCntxt>\n"
				+ "<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + "<CshrId>00000001</CshrId>\n"
				+ "</SaleCntxt>\n" + "</Cntxt>\n" + "<Tx>\n" + "<TxTp>CRDP</TxTp>\n"
				+ "<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n"
				+ "<TxRef>130218</TxRef>\n" + "</TxId>\n" + "<TxSucss>true</TxSucss>\n" + "<Rvsl>false</Rvsl>\n"
				+ "<TxDtls>\n" + "<Ccy>EUR</Ccy>\n" + "<TtlAmt>0.01</TtlAmt>\n"
				+ "<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n"
				+ "</TxDtls>\n" + "<AuthstnRslt>\n" + "<AuthstnNtty>\n" + "<Tp>CISS</Tp>\n" + "</AuthstnNtty>\n"
				+ "<RspnToAuthstn>\n" + "<Rspn>APPR</Rspn>\n" + "</RspnToAuthstn>\n" + "<AuthstnCd>032983</AuthstnCd>\n"
				+ "</AuthstnRslt>\n" + "</Tx>\n" + "</Cmpltn>\n" + "</Tx>\n" + "</DataSet>\n" + "</BtchTrf>\n"
				+ "</AccptrBtchTrf>\n" + "</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessInvalidCompletionCountryCode() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "<AccptrBtchTrf>\n" + "<Hdr>\n" + "<DwnldTrf>true</DwnldTrf>\n" + "<FrmtVrsn>3432</FrmtVrsn>\n"
				+ "<XchgId>5235</XchgId>\n" + "<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + "<InitgPty>\n"
				+ "<Id>23523</Id>\n" + "</InitgPty>\n" + "</Hdr>\n" + "<BtchTrf>\n" + "<TxTtls>\n"
				+ "<POIGrpId>uit</POIGrpId>\n" + "<CardPdctPrfl>card</CardPdctPrfl>\n" + "<Ccy>USD</Ccy>\n"
				+ "<Tp>CRDT</Tp>\n" + "<TtlNb>10</TtlNb>\n" + "<CmltvAmt>1</CmltvAmt>\n" + "</TxTtls>\n" + "<DataSet>\n"
				+ "<DataSetId>\n" + "<Nm>xyz</Nm>\n" + "<Tp>TXCP</Tp>\n" + "<Vrsn>21</Vrsn>\n"
				+ "<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + "</DataSetId>\n" + "<DataSetInitr>\n"
				+ "<Id>23523</Id>\n" + "</DataSetInitr>\n" + "<TxTtls>\n" + "<POIGrpId>uit</POIGrpId>\n"
				+ "<CardPdctPrfl>card</CardPdctPrfl>\n" + "<Ccy>USD</Ccy>\n" + "<Tp>CRDT</Tp>\n" + "<TtlNb>10</TtlNb>\n"
				+ "<CmltvAmt>1</CmltvAmt>\n" + "</TxTtls>\n" + "<CmonData>\n" + "<Envt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>cony</Id>\n" + "</Id>\n" + "<GrpId>mon</GrpId>\n" + "</POI>\n" + "</Envt>\n"
				+ "<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + "<Ccy>USD</Ccy>\n" + "</CmonData>\n" + "\n" + "<Tx>\n"
				+ "<Cmpltn>\n" + "<TxSeqCntr>1</TxSeqCntr>\n" + "<Envt>\n" + "<Acqrr>\n" + "<Id>\n"
				+ "<Id>000000000123</Id>\n" + "</Id>\n" + "<ParamsVrsn>20110913123456</ParamsVrsn>\n" + "</Acqrr>\n"
				+ "<Mrchnt>\n" + "<Id>\n" + "<Id>666315366631671</Id>\n" + "</Id>\n"
				+ "<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + "</Mrchnt>\n" + "<POI>\n" + "<Id>\n"
				+ "<Id>66631671</Id>\n" + "</Id>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276037</PAN>\n" + "<CardSeqNb>01</CardSeqNb>\n" + "<FctvDt>2012-01</FctvDt>\n"
				+ "<XpryDt>2200-03-03</XpryDt>\n" + "<SvcCd>982</SvcCd>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>123</CardCtryCd>\n" + "<CardPdctPrfl>0017</CardPdctPrfl>\n" + "<CardBrnd>CB</CardBrnd>\n"
				+ "</Card>\n" + "</Envt>\n" + "<Cntxt>\n" + "<PmtCntxt>\n" + "<OnLineCntxt>false</OnLineCntxt>\n"
				+ "<AttndncCntxt>ATTD</AttndncCntxt>\n" + "<CardDataNtryMd>CICC</CardDataNtryMd>\n" + "</PmtCntxt>\n"
				+ "</Cntxt>	 \n" + "<Tx>\n" + "<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + "<TxId>\n"
				+ "<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + "<TxRef>130220</TxRef>\n" + "</TxId>\n" + "<OrgnlTx>\n"
				+ "<TxId>\n" + "<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + "<TxRef>130213</TxRef>\n" + "</TxId>\n"
				+ "<TxTp>CRDP</TxTp>\n" + "<TxRslt>\n" + "<RspnToAuthstn>\n" + "<Rspn>APPR</Rspn>\n"
				+ "</RspnToAuthstn>\n" + "<AuthstnCd>032983</AuthstnCd>\n" + "</TxRslt>\n" + "</OrgnlTx>\n"
				+ "<TxSucss>true</TxSucss>\n" + "<TxDtls>\n" + "<Ccy>EUR</Ccy>\n" + "<TtlAmt>2016</TtlAmt>\n"
				+ "<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + "</TxDtls>\n"
				+ "</Tx>\n" + "</Cmpltn>\n" + "</Tx>\n" + "\n" + "<Tx>\n" + "<Cmpltn>\n" + "<TxSeqCntr>3</TxSeqCntr>\n"
				+ "<Envt>\n" + "<Acqrr>\n" + "<Id>\n" + "<Id>000000000123</Id>\n" + "</Id>\n"
				+ "<ParamsVrsn>20110913123456</ParamsVrsn>\n" + "</Acqrr>\n" + "<Mrchnt>\n" + "<Id>\n"
				+ "<Id>666315366631671</Id>\n" + "</Id>\n" + "<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n"
				+ "</Mrchnt>\n" + "<POI>\n" + "<Id>\n" + "<Id>66631671</Id>\n" + "</Id>\n" + "<Cpblties>\n"
				+ "<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + "<CardRdngCpblties>MGST</CardRdngCpblties>\n"
				+ "<CardRdngCpblties>CICC</CardRdngCpblties>\n" + "<CardRdngCpblties>ECTL</CardRdngCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n"
				+ "<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + "<OnLineCpblties>SMON</OnLineCpblties>\n"
				+ "<MsgCpblties>\n" + "<Dstn>MDSP</Dstn>\n" + "<NbOfLines>8</NbOfLines>\n"
				+ "<LineWidth>15</LineWidth>\n" + "</MsgCpblties>\n" + "<MsgCpblties>\n" + "<Dstn>MRCP</Dstn>\n"
				+ "<LineWidth>24</LineWidth>\n" + "</MsgCpblties>\n" + "</Cpblties>\n" + "<Cmpnt>\n" + "<Tp>TERM</Tp>\n"
				+ "<Id>\n" + "<PrvdrId>INGENICO</PrvdrId>\n" + "<Id>ICT250</Id>\n" + "<SrlNb>1122334</SrlNb>\n"
				+ "</Id>\n" + "</Cmpnt>\n" + "<Cmpnt>\n" + "<Tp>APLI</Tp>\n" + "<Id>\n"
				+ "<PrvdrId>INGENICO</PrvdrId>\n" + "<Id>SEPA-FAST</Id>\n" + "</Id>\n" + "<Sts>\n"
				+ "<VrsnNb>04.10</VrsnNb>\n" + "</Sts>\n" + "<StdCmplc>\n" + "<Id>SEPA-FAST</Id>\n"
				+ "<Vrsn>3.1</Vrsn>\n" + "<Issr>OSCAR</Issr>\n" + "</StdCmplc>\n" + "<Assmnt>\n" + "<Tp>CERT</Tp>\n"
				+ "<Assgnr>OSCAR</Assgnr>\n" + "<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n"
				+ "<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + "<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n"
				+ "</Assmnt>\n" + "</Cmpnt>\n" + "</POI>\n" + "<Card>\n" + "<PlainCardData>\n"
				+ "<PAN>1111244455547276037</PAN>\n" + "<CardSeqNb>00</CardSeqNb>\n" + "<FctvDt>2010-12</FctvDt>\n"
				+ "<XpryDt>2200-03-03</XpryDt>\n" + "<SvcCd>982</SvcCd>\n" + "</PlainCardData>\n"
				+ "<CardCtryCd>DEU</CardCtryCd>\n" + "<CardPdctPrfl>0018</CardPdctPrfl>\n" + "<CardBrnd>CB</CardBrnd>\n"
				+ "</Card>\n" + "</Envt>\n" + "<Cntxt>\n" + "<PmtCntxt>\n" + "<CrdhldrPres>true</CrdhldrPres>\n"
				+ "<OnLineCntxt>true</OnLineCntxt>\n" + "<AttndncCntxt>ATTD</AttndncCntxt>\n"
				+ "<CardDataNtryMd>CICC</CardDataNtryMd>\n" + "</PmtCntxt>\n" + "<SaleCntxt>\n"
				+ "<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + "<CshrId>00000001</CshrId>\n"
				+ "</SaleCntxt>\n" + "</Cntxt>\n" + "<Tx>\n" + "<TxTp>CRDP</TxTp>\n"
				+ "<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + "<TxId>\n" + "<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n"
				+ "<TxRef>130218</TxRef>\n" + "</TxId>\n" + "<TxSucss>true</TxSucss>\n" + "<Rvsl>false</Rvsl>\n"
				+ "<TxDtls>\n" + "<Ccy>EUR</Ccy>\n" + "<TtlAmt>0.01</TtlAmt>\n"
				+ "<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n"
				+ "</TxDtls>\n" + "<AuthstnRslt>\n" + "<AuthstnNtty>\n" + "<Tp>CISS</Tp>\n" + "</AuthstnNtty>\n"
				+ "<RspnToAuthstn>\n" + "<Rspn>APPR</Rspn>\n" + "</RspnToAuthstn>\n" + "<AuthstnCd>032983</AuthstnCd>\n"
				+ "</AuthstnRslt>\n" + "</Tx>\n" + "</Cmpltn>\n" + "</Tx>\n" + "</DataSet>\n" + "</BtchTrf>\n"
				+ "</AccptrBtchTrf>\n" + "</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCompletionCRDPTransaction() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"<AccptrBtchTrf>\n" + 
				"<Hdr>\n" + 
				"<DwnldTrf>true</DwnldTrf>\n" + 
				"<FrmtVrsn>3432</FrmtVrsn>\n" + 
				"<XchgId>5235</XchgId>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"<InitgPty>\n" + 
				"<Id>23523</Id>\n" + 
				"</InitgPty>\n" + 
				"</Hdr>\n" + 
				"<BtchTrf>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<DataSet>\n" + 
				"<DataSetId>\n" + 
				"<Nm>xyz</Nm>\n" + 
				"<Tp>TXCP</Tp>\n" + 
				"<Vrsn>21</Vrsn>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"</DataSetId>\n" + 
				"<DataSetInitr>\n" + 
				"<Id>23523</Id>\n" + 
				"</DataSetInitr>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<CmonData>\n" + 
				"<Envt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>cony</Id>\n" + 
				"</Id>\n" + 
				"<GrpId>mon</GrpId>\n" + 
				"</POI>\n" + 
				"</Envt>\n" + 
				"<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"</CmonData>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cxl>\n" + 
				"<TxSeqCntr>1</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>01</CardSeqNb>\n" + 
				"<FctvDt>2012-01</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>FRA</CardCtryCd>\n" + 
				"<CardPdctPrfl>0017</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<OnLineCntxt>false</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + 
				"<TxRef>130220</TxRef>\n" + 
				"</TxId>\n" + 
				"<OrgnlTx>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + 
				"<TxRef>130213</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<TxRslt>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</TxRslt>\n" + 
				"</OrgnlTx>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>2000</TtlAmt>\n" + 
				"<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"</Tx>\n" + 
				"</Cxl>\n" + 
				"</Tx>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cmpltn>\n" + 
				"<TxSeqCntr>3</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"<Cpblties>\n" + 
				"<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + 
				"<OnLineCpblties>SMON</OnLineCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MDSP</Dstn>\n" + 
				"<NbOfLines>8</NbOfLines>\n" + 
				"<LineWidth>15</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MRCP</Dstn>\n" + 
				"<LineWidth>24</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"</Cpblties>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>TERM</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>ICT250</Id>\n" + 
				"<SrlNb>1122334</SrlNb>\n" + 
				"</Id>\n" + 
				"</Cmpnt>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>APLI</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"</Id>\n" + 
				"<Sts>\n" + 
				"<VrsnNb>04.10</VrsnNb>\n" + 
				"</Sts>\n" + 
				"<StdCmplc>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"<Vrsn>3.1</Vrsn>\n" + 
				"<Issr>OSCAR</Issr>\n" + 
				"</StdCmplc>\n" + 
				"<Assmnt>\n" + 
				"<Tp>CERT</Tp>\n" + 
				"<Assgnr>OSCAR</Assgnr>\n" + 
				"<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n" + 
				"<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + 
				"<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n" + 
				"</Assmnt>\n" + 
				"</Cmpnt>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>00</CardSeqNb>\n" + 
				"<FctvDt>2010-12</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>DEU</CardCtryCd>\n" + 
				"<CardPdctPrfl>0018</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<CrdhldrPres>true</CrdhldrPres>\n" + 
				"<OnLineCntxt>true</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"<SaleCntxt>\n" + 
				"<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + 
				"<CshrId>00000001</CshrId>\n" + 
				"</SaleCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n" + 
				"<TxRef>130218</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<Rvsl>false</Rvsl>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>1000</TtlAmt>\n" + 
				"<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"<AuthstnRslt>\n" + 
				"<AuthstnNtty>\n" + 
				"<Tp>CISS</Tp>\n" + 
				"</AuthstnNtty>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</AuthstnRslt>\n" + 
				"</Tx>\n" + 
				"</Cmpltn>\n" + 
				"</Tx>\n" + 
				"</DataSet>\n" + 
				"</BtchTrf>\n" + 
				"</AccptrBtchTrf>\n" + 
				"</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCompletionRFNDTransaction() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"<AccptrBtchTrf>\n" + 
				"<Hdr>\n" + 
				"<DwnldTrf>true</DwnldTrf>\n" + 
				"<FrmtVrsn>3432</FrmtVrsn>\n" + 
				"<XchgId>5235</XchgId>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"<InitgPty>\n" + 
				"<Id>23523</Id>\n" + 
				"</InitgPty>\n" + 
				"</Hdr>\n" + 
				"<BtchTrf>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<DataSet>\n" + 
				"<DataSetId>\n" + 
				"<Nm>xyz</Nm>\n" + 
				"<Tp>TXCP</Tp>\n" + 
				"<Vrsn>21</Vrsn>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"</DataSetId>\n" + 
				"<DataSetInitr>\n" + 
				"<Id>23523</Id>\n" + 
				"</DataSetInitr>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<CmonData>\n" + 
				"<Envt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>cony</Id>\n" + 
				"</Id>\n" + 
				"<GrpId>mon</GrpId>\n" + 
				"</POI>\n" + 
				"</Envt>\n" + 
				"<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"</CmonData>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cxl>\n" + 
				"<TxSeqCntr>1</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>01</CardSeqNb>\n" + 
				"<FctvDt>2012-01</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>FRA</CardCtryCd>\n" + 
				"<CardPdctPrfl>0017</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<OnLineCntxt>false</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + 
				"<TxRef>130220</TxRef>\n" + 
				"</TxId>\n" + 
				"<OrgnlTx>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + 
				"<TxRef>130213</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<TxRslt>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</TxRslt>\n" + 
				"</OrgnlTx>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>2000</TtlAmt>\n" + 
				"<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"</Tx>\n" + 
				"</Cxl>\n" + 
				"</Tx>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cmpltn>\n" + 
				"<TxSeqCntr>3</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"<Cpblties>\n" + 
				"<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + 
				"<OnLineCpblties>SMON</OnLineCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MDSP</Dstn>\n" + 
				"<NbOfLines>8</NbOfLines>\n" + 
				"<LineWidth>15</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MRCP</Dstn>\n" + 
				"<LineWidth>24</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"</Cpblties>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>TERM</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>ICT250</Id>\n" + 
				"<SrlNb>1122334</SrlNb>\n" + 
				"</Id>\n" + 
				"</Cmpnt>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>APLI</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"</Id>\n" + 
				"<Sts>\n" + 
				"<VrsnNb>04.10</VrsnNb>\n" + 
				"</Sts>\n" + 
				"<StdCmplc>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"<Vrsn>3.1</Vrsn>\n" + 
				"<Issr>OSCAR</Issr>\n" + 
				"</StdCmplc>\n" + 
				"<Assmnt>\n" + 
				"<Tp>CERT</Tp>\n" + 
				"<Assgnr>OSCAR</Assgnr>\n" + 
				"<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n" + 
				"<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + 
				"<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n" + 
				"</Assmnt>\n" + 
				"</Cmpnt>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>00</CardSeqNb>\n" + 
				"<FctvDt>2010-12</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>DEU</CardCtryCd>\n" + 
				"<CardPdctPrfl>0018</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<CrdhldrPres>true</CrdhldrPres>\n" + 
				"<OnLineCntxt>true</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"<SaleCntxt>\n" + 
				"<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + 
				"<CshrId>00000001</CshrId>\n" + 
				"</SaleCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n" + 
				"<TxRef>130218</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<Rvsl>false</Rvsl>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>1000</TtlAmt>\n" + 
				"<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"<AuthstnRslt>\n" + 
				"<AuthstnNtty>\n" + 
				"<Tp>CISS</Tp>\n" + 
				"</AuthstnNtty>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</AuthstnRslt>\n" + 
				"</Tx>\n" + 
				"</Cmpltn>\n" + 
				"</Tx>\n" + 
				"</DataSet>\n" + 
				"</BtchTrf>\n" + 
				"</AccptrBtchTrf>\n" + 
				"</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCancellationRFNDTransaction() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"<AccptrBtchTrf>\n" + 
				"<Hdr>\n" + 
				"<DwnldTrf>true</DwnldTrf>\n" + 
				"<FrmtVrsn>3432</FrmtVrsn>\n" + 
				"<XchgId>5235</XchgId>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"<InitgPty>\n" + 
				"<Id>23523</Id>\n" + 
				"</InitgPty>\n" + 
				"</Hdr>\n" + 
				"<BtchTrf>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<DataSet>\n" + 
				"<DataSetId>\n" + 
				"<Nm>xyz</Nm>\n" + 
				"<Tp>TXCP</Tp>\n" + 
				"<Vrsn>21</Vrsn>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"</DataSetId>\n" + 
				"<DataSetInitr>\n" + 
				"<Id>23523</Id>\n" + 
				"</DataSetInitr>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<CmonData>\n" + 
				"<Envt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>cony</Id>\n" + 
				"</Id>\n" + 
				"<GrpId>mon</GrpId>\n" + 
				"</POI>\n" + 
				"</Envt>\n" + 
				"<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"</CmonData>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cxl>\n" + 
				"<TxSeqCntr>1</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>01</CardSeqNb>\n" + 
				"<FctvDt>2012-01</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>FRA</CardCtryCd>\n" + 
				"<CardPdctPrfl>0017</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<OnLineCntxt>false</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + 
				"<TxRef>130220</TxRef>\n" + 
				"</TxId>\n" + 
				"<OrgnlTx>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + 
				"<TxRef>130213</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<TxRslt>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</TxRslt>\n" + 
				"</OrgnlTx>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>2000</TtlAmt>\n" + 
				"<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"</Tx>\n" + 
				"</Cxl>\n" + 
				"</Tx>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cmpltn>\n" + 
				"<TxSeqCntr>3</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"<Cpblties>\n" + 
				"<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + 
				"<OnLineCpblties>SMON</OnLineCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MDSP</Dstn>\n" + 
				"<NbOfLines>8</NbOfLines>\n" + 
				"<LineWidth>15</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MRCP</Dstn>\n" + 
				"<LineWidth>24</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"</Cpblties>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>TERM</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>ICT250</Id>\n" + 
				"<SrlNb>1122334</SrlNb>\n" + 
				"</Id>\n" + 
				"</Cmpnt>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>APLI</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"</Id>\n" + 
				"<Sts>\n" + 
				"<VrsnNb>04.10</VrsnNb>\n" + 
				"</Sts>\n" + 
				"<StdCmplc>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"<Vrsn>3.1</Vrsn>\n" + 
				"<Issr>OSCAR</Issr>\n" + 
				"</StdCmplc>\n" + 
				"<Assmnt>\n" + 
				"<Tp>CERT</Tp>\n" + 
				"<Assgnr>OSCAR</Assgnr>\n" + 
				"<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n" + 
				"<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + 
				"<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n" + 
				"</Assmnt>\n" + 
				"</Cmpnt>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>00</CardSeqNb>\n" + 
				"<FctvDt>2010-12</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>DEU</CardCtryCd>\n" + 
				"<CardPdctPrfl>0018</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<CrdhldrPres>true</CrdhldrPres>\n" + 
				"<OnLineCntxt>true</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"<SaleCntxt>\n" + 
				"<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + 
				"<CshrId>00000001</CshrId>\n" + 
				"</SaleCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n" + 
				"<TxRef>130218</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<Rvsl>false</Rvsl>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>1000</TtlAmt>\n" + 
				"<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"<AuthstnRslt>\n" + 
				"<AuthstnNtty>\n" + 
				"<Tp>CISS</Tp>\n" + 
				"</AuthstnNtty>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</AuthstnRslt>\n" + 
				"</Tx>\n" + 
				"</Cmpltn>\n" + 
				"</Tx>\n" + 
				"</DataSet>\n" + 
				"</BtchTrf>\n" + 
				"</AccptrBtchTrf>\n" + 
				"</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws FactoryConfigurationError, Exception {
		Document acceptorBatchTransferDocument = null;
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.011.001.08\" xmlns:ns2=\"http://document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"<AccptrBtchTrf>\n" + 
				"<Hdr>\n" + 
				"<DwnldTrf>true</DwnldTrf>\n" + 
				"<FrmtVrsn>3432</FrmtVrsn>\n" + 
				"<XchgId>5235</XchgId>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"<InitgPty>\n" + 
				"<Id>23523</Id>\n" + 
				"</InitgPty>\n" + 
				"</Hdr>\n" + 
				"<BtchTrf>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<DataSet>\n" + 
				"<DataSetId>\n" + 
				"<Nm>xyz</Nm>\n" + 
				"<Tp>TXCP</Tp>\n" + 
				"<Vrsn>21</Vrsn>\n" + 
				"<CreDtTm>2019-12-05T10:31:26.241+05:30</CreDtTm>\n" + 
				"</DataSetId>\n" + 
				"<DataSetInitr>\n" + 
				"<Id>23523</Id>\n" + 
				"</DataSetInitr>\n" + 
				"<TxTtls>\n" + 
				"<POIGrpId>uit</POIGrpId>\n" + 
				"<CardPdctPrfl>card</CardPdctPrfl>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<Tp>CRDT</Tp>\n" + 
				"<TtlNb>10</TtlNb>\n" + 
				"<CmltvAmt>1</CmltvAmt>\n" + 
				"</TxTtls>\n" + 
				"<CmonData>\n" + 
				"<Envt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>cony</Id>\n" + 
				"</Id>\n" + 
				"<GrpId>mon</GrpId>\n" + 
				"</POI>\n" + 
				"</Envt>\n" + 
				"<MrchntCtgyCd>mik</MrchntCtgyCd>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"</CmonData>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cxl>\n" + 
				"<TxSeqCntr>1</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>01</CardSeqNb>\n" + 
				"<FctvDt>2012-01</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>FRA</CardCtryCd>\n" + 
				"<CardPdctPrfl>0017</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<OnLineCntxt>false</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:03:50.000</TxDtTm>\n" + 
				"<TxRef>130220</TxRef>\n" + 
				"</TxId>\n" + 
				"<OrgnlTx>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:07:55.000</TxDtTm>\n" + 
				"<TxRef>130213</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<TxRslt>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</TxRslt>\n" + 
				"</OrgnlTx>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>2000</TtlAmt>\n" + 
				"<ICCRltdData>asdffghjklpoiuyxcvbnmfqwertyudfghj7654185542</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"</Tx>\n" + 
				"</Cxl>\n" + 
				"</Tx>\n" + 
				"\n" + 
				"<Tx>\n" + 
				"<Cmpltn>\n" + 
				"<TxSeqCntr>3</TxSeqCntr>\n" + 
				"<Envt>\n" + 
				"<Acqrr>\n" + 
				"<Id>\n" + 
				"<Id>000000000123</Id>\n" + 
				"</Id>\n" + 
				"<ParamsVrsn>20110913123456</ParamsVrsn>\n" + 
				"</Acqrr>\n" + 
				"<Mrchnt>\n" + 
				"<Id>\n" + 
				"<Id>155058594833186</Id>\n" + 
				"</Id>\n" + 
				"<CmonNm>TestLab,certificationAVE,SEPA</CmonNm>\n" + 
				"</Mrchnt>\n" + 
				"<POI>\n" + 
				"<Id>\n" + 
				"<Id>94833186</Id>\n" + 
				"</Id>\n" + 
				"<Cpblties>\n" + 
				"<CardRdngCpblties>PHYS</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>MGST</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>CICC</CardRdngCpblties>\n" + 
				"<CardRdngCpblties>ECTL</CardRdngCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FCPN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>NPIN</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>MNSG</CrdhldrVrfctnCpblties>\n" + 
				"<CrdhldrVrfctnCpblties>FEPN</CrdhldrVrfctnCpblties>\n" + 
				"<OnLineCpblties>SMON</OnLineCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MDSP</Dstn>\n" + 
				"<NbOfLines>8</NbOfLines>\n" + 
				"<LineWidth>15</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"<MsgCpblties>\n" + 
				"<Dstn>MRCP</Dstn>\n" + 
				"<LineWidth>24</LineWidth>\n" + 
				"</MsgCpblties>\n" + 
				"</Cpblties>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>TERM</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>ICT250</Id>\n" + 
				"<SrlNb>1122334</SrlNb>\n" + 
				"</Id>\n" + 
				"</Cmpnt>\n" + 
				"<Cmpnt>\n" + 
				"<Tp>APLI</Tp>\n" + 
				"<Id>\n" + 
				"<PrvdrId>INGENICO</PrvdrId>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"</Id>\n" + 
				"<Sts>\n" + 
				"<VrsnNb>04.10</VrsnNb>\n" + 
				"</Sts>\n" + 
				"<StdCmplc>\n" + 
				"<Id>SEPA-FAST</Id>\n" + 
				"<Vrsn>3.1</Vrsn>\n" + 
				"<Issr>OSCAR</Issr>\n" + 
				"</StdCmplc>\n" + 
				"<Assmnt>\n" + 
				"<Tp>CERT</Tp>\n" + 
				"<Assgnr>OSCAR</Assgnr>\n" + 
				"<DlvryDt>2014-05-14T17:12:05.000Z</DlvryDt>\n" + 
				"<XprtnDt>2021-01-12T09:07:18.000Z</XprtnDt>\n" + 
				"<Nb>PAY.ELI.ING.POI.120.13030002</Nb>\n" + 
				"</Assmnt>\n" + 
				"</Cmpnt>\n" + 
				"</POI>\n" + 
				"<Card>\n" + 
				"<PlainCardData>\n" + 
				"<PAN>1111244455547276037</PAN>\n" + 
				"<CardSeqNb>00</CardSeqNb>\n" + 
				"<FctvDt>2010-12</FctvDt>\n" + 
				"<XpryDt>2020-12-12</XpryDt>\n" + 
				"<SvcCd>982</SvcCd>\n" + 
				"</PlainCardData>\n" + 
				"<CardCtryCd>DEU</CardCtryCd>\n" + 
				"<CardPdctPrfl>0018</CardPdctPrfl>\n" + 
				"<CardBrnd>CB</CardBrnd>\n" + 
				"</Card>\n" + 
				"</Envt>\n" + 
				"<Cntxt>\n" + 
				"<PmtCntxt>\n" + 
				"<CrdhldrPres>true</CrdhldrPres>\n" + 
				"<OnLineCntxt>true</OnLineCntxt>\n" + 
				"<AttndncCntxt>ATTD</AttndncCntxt>\n" + 
				"<CardDataNtryMd>CICC</CardDataNtryMd>\n" + 
				"</PmtCntxt>\n" + 
				"<SaleCntxt>\n" + 
				"<SaleRefNb>0000000011111111/20151203143303</SaleRefNb>\n" + 
				"<CshrId>00000001</CshrId>\n" + 
				"</SaleCntxt>\n" + 
				"</Cntxt>\n" + 
				"<Tx>\n" + 
				"<TxTp>CRDP</TxTp>\n" + 
				"<MrchntCtgyCd>5999</MrchntCtgyCd>\n" + 
				"<TxId>\n" + 
				"<TxDtTm>2015-12-03T14:33:03.000</TxDtTm>\n" + 
				"<TxRef>130218</TxRef>\n" + 
				"</TxId>\n" + 
				"<TxSucss>true</TxSucss>\n" + 
				"<Rvsl>false</Rvsl>\n" + 
				"<TxDtls>\n" + 
				"<Ccy>USD</Ccy>\n" + 
				"<TtlAmt>1000</TtlAmt>\n" + 
				"<ICCRltdData>nwIGAAAAAAABnwMGAAAAAAAAnyYI2nt5MGXoqUlfJQMQEgGCAlgAnzYCESKfCQIAAp8nAUCfNANBAwKEB6AAAAAEEBCfHggwMTEyMjMzNJ8QEgEQAAAAAAAAAAAAAAAAAAAAAJ8zA+D4yJ8aAgAQnzUBIpUFAgAAAABfKgIJeJoDFRIDn0EDEwIYnAEAnzcEfvCtgsoE//xQAMUBAJsC6AA=</ICCRltdData>\n" + 
				"</TxDtls>\n" + 
				"<AuthstnRslt>\n" + 
				"<AuthstnNtty>\n" + 
				"<Tp>CISS</Tp>\n" + 
				"</AuthstnNtty>\n" + 
				"<RspnToAuthstn>\n" + 
				"<Rspn>APPR</Rspn>\n" + 
				"</RspnToAuthstn>\n" + 
				"<AuthstnCd>032983</AuthstnCd>\n" + 
				"</AuthstnRslt>\n" + 
				"</Tx>\n" + 
				"</Cmpltn>\n" + 
				"</Tx>\n" + 
				"</DataSet>\n" + 
				"</BtchTrf>\n" + 
				"</AccptrBtchTrf>\n" + 
				"</ns2:Document>";
		acceptorBatchTransferResponse(acceptorBatchTransferDocument, acceptorAuthReqXmlRecr);
	}

	private void acceptorBatchTransferResponse(Document acceptorBatchTransferDocument, String acceptorAuthReqXmlRecr)
			throws FactoryConfigurationError, Exception {
		Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class, acceptorAuthReqXmlRecr,
				Constants.CAAA_001_001_08)).thenReturn(acceptorBatchTransferDocument);
		String process = acceptorBatchTransferProcessor.process(acceptorAuthReqXmlRecr);
		Assert.assertNotNull(process);
	}
}
