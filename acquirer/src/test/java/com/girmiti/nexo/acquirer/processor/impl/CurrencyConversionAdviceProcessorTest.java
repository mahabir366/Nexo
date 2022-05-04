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
import com.girmiti.nexo.processor.impl.CurrencyConversionAdviceProcessor;
import com.girmiti.nexo.util.Constants;
import com.girmiti.nexo.util.JaxbHelper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CurrencyConversionAdviceProcessorTest {
	
	@InjectMocks
	CurrencyConversionAdviceProcessor currencyConversionAdviceProcessor;
	
	@Mock
	JaxbHelper jaxbHelper;

	@Test
	public void testProcessWithexpDateException() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.018.001.03\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCcyConvsAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>DCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-19T18:32:29.342+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>dszafs</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <AccptrCcyConvsAdvc>\n" + "            <Envt>\n" + "                <POIId>\n"
				+ "                    <Id>dfsad</Id>\n" + "                </POIId>\n" + "                <Card>\n"
				+ "                    <PlainCardData>\n" + "                        <PAN>5346854356</PAN>\n"
				+ "                        <XpryDt>2019-15-19</XpryDt>\n" + "                    </PlainCardData>\n"
				+ "                    <CardCtryCd>usa</CardCtryCd>\n" + "                </Card>\n"
				+ "            </Envt>\n" + "            <Tx>\n" + "                <TxId>\n"
				+ "                    <TxDtTm>2019-12-19T18:32:29.342+05:30</TxDtTm>\n"
				+ "                    <TxRef>sdafs</TxRef>\n" + "                </TxId>\n"
				+ "                <TxDtls>\n" + "                    <Ccy>USD</Ccy>\n"
				+ "                    <TtlAmt>1</TtlAmt>\n" + "                </TxDtls>\n" + "            </Tx>\n"
				+ "            <CcyConvsRslt>\n" + "                <AccptdByCrdhldr>true</AccptdByCrdhldr>\n"
				+ "            </CcyConvsRslt>\n" + "        </AccptrCcyConvsAdvc>\n" + "    </AccptrCcyConvsAdvc>\n"
				+ "</ns2:Document>";
		currencyConversionAdviceResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcess() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.018.001.03\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCcyConvsAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>DCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-19T18:32:29.342+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>dszafs</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <AccptrCcyConvsAdvc>\n" + "            <Envt>\n" + "                <POIId>\n"
				+ "                    <Id>dfsad</Id>\n" + "                </POIId>\n" + "                <Card>\n"
				+ "                    <PlainCardData>\n" + "                        <PAN>5346854356</PAN>\n"
				+ "                        <XpryDt>2019-12-19</XpryDt>\n" + "                    </PlainCardData>\n"
				+ "                    <CardCtryCd>usa</CardCtryCd>\n" + "                </Card>\n"
				+ "            </Envt>\n" + "            <Tx>\n" + "                <TxId>\n"
				+ "                    <TxDtTm>2019-12-19T18:32:29.342+05:30</TxDtTm>\n"
				+ "                    <TxRef>sdafs</TxRef>\n" + "                </TxId>\n"
				+ "                <TxDtls>\n" + "                    <Ccy>USD</Ccy>\n"
				+ "                    <TtlAmt>1</TtlAmt>\n" + "                </TxDtls>\n" + "            </Tx>\n"
				+ "            <CcyConvsRslt>\n" + "                <AccptdByCrdhldr>true</AccptdByCrdhldr>\n"
				+ "            </CcyConvsRslt>\n" + "        </AccptrCcyConvsAdvc>\n" + "    </AccptrCcyConvsAdvc>\n"
				+ "</ns2:Document>";
		currencyConversionAdviceResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessException() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.018.001.03\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCcyConvsAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>DCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-19T18:32:29.342+05:30hththtr</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>dszafs</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <AccptrCcyConvsAdvc>\n" + "            <Envt>\n" + "                <POIId>\n"
				+ "                    <Id>dfsad</Id>\n" + "                </POIId>\n" + "                <Card>\n"
				+ "                    <PlainCardData>\n" + "                        <PAN>5346854356</PAN>\n"
				+ "                        <XpryDt>2019-12-19</XpryDt>\n" + "                    </PlainCardData>\n"
				+ "                    <CardCtryCd>usa</CardCtryCd>\n" + "                </Card>\n"
				+ "            </Envt>\n" + "            <Tx>\n" + "                <TxId>\n"
				+ "                    <TxDtTm>2019-12-19T18:32:29.342+05:30</TxDtTm>\n"
				+ "                    <TxRef>sdafs</TxRef>\n" + "                </TxId>\n"
				+ "                <TxDtls>\n" + "                    <Ccy>USD</Ccy>\n"
				+ "                    <TtlAmt>1</TtlAmt>\n" + "                </TxDtls>\n" + "            </Tx>\n"
				+ "            <CcyConvsRslt>\n" + "                <AccptdByCrdhldr>true</AccptdByCrdhldr>\n"
				+ "            </CcyConvsRslt>\n" + "        </AccptrCcyConvsAdvc>\n" + "    </AccptrCcyConvsAdvc>\n"
				+ "</ns2:Document>";
		currencyConversionAdviceResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCountryError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.018.001.03\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCcyConvsAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>DCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-19T18:32:29.342+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>dszafs</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <AccptrCcyConvsAdvc>\n" + "            <Envt>\n" + "                <POIId>\n"
				+ "                    <Id>dfsad</Id>\n" + "                </POIId>\n" + "                <Card>\n"
				+ "                    <PlainCardData>\n" + "                        <PAN>5346854356</PAN>\n"
				+ "                        <XpryDt>2019-12-19</XpryDt>\n" + "                    </PlainCardData>\n"
				+ "                    <CardCtryCd>uio</CardCtryCd>\n" + "                </Card>\n"
				+ "            </Envt>\n" + "            <Tx>\n" + "                <TxId>\n"
				+ "                    <TxDtTm>2019-12-19T18:32:29.342+05:30</TxDtTm>\n"
				+ "                    <TxRef>sdafs</TxRef>\n" + "                </TxId>\n"
				+ "                <TxDtls>\n" + "                    <Ccy>USD</Ccy>\n"
				+ "                    <TtlAmt>1</TtlAmt>\n" + "                </TxDtls>\n" + "            </Tx>\n"
				+ "            <CcyConvsRslt>\n" + "                <AccptdByCrdhldr>true</AccptdByCrdhldr>\n"
				+ "            </CcyConvsRslt>\n" + "        </AccptrCcyConvsAdvc>\n" + "    </AccptrCcyConvsAdvc>\n"
				+ "</ns2:Document>";
		currencyConversionAdviceResponse(acceptorAuthReqXmlRecr);
	}
	
	@Test
	public void testProcessCurrencyError() throws FactoryConfigurationError, Exception {
		String acceptorAuthReqXmlRecr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns2:Document xsi:type=\"Document\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:caaa.018.001.03\" xmlns:ns2=\"http://Document/bar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "    <AccptrCcyConvsAdvc>\n" + "        <Hdr>\n" + "            <MsgFctn>DCAV</MsgFctn>\n"
				+ "            <PrtcolVrsn>6.0</PrtcolVrsn>\n" + "            <XchgId>149</XchgId>\n"
				+ "            <CreDtTm>2019-12-19T18:32:29.342+05:30</CreDtTm>\n" + "            <InitgPty>\n"
				+ "                <Id>dszafs</Id>\n" + "            </InitgPty>\n" + "        </Hdr>\n"
				+ "        <AccptrCcyConvsAdvc>\n" + "            <Envt>\n" + "                <POIId>\n"
				+ "                    <Id>dfsad</Id>\n" + "                </POIId>\n" + "                <Card>\n"
				+ "                    <PlainCardData>\n" + "                        <PAN>5346854356</PAN>\n"
				+ "                        <XpryDt>2019-12-19</XpryDt>\n" + "                    </PlainCardData>\n"
				+ "                    <CardCtryCd>USA</CardCtryCd>\n" + "                </Card>\n"
				+ "            </Envt>\n" + "            <Tx>\n" + "                <TxId>\n"
				+ "                    <TxDtTm>2019-12-19T18:32:29.342+05:30</TxDtTm>\n"
				+ "                    <TxRef>sdafs</TxRef>\n" + "                </TxId>\n"
				+ "                <TxDtls>\n" + "                    <Ccy>NUL</Ccy>\n"
				+ "                    <TtlAmt>1</TtlAmt>\n" + "                </TxDtls>\n" + "            </Tx>\n"
				+ "            <CcyConvsRslt>\n" + "                <AccptdByCrdhldr>true</AccptdByCrdhldr>\n"
				+ "            </CcyConvsRslt>\n" + "        </AccptrCcyConvsAdvc>\n" + "    </AccptrCcyConvsAdvc>\n"
				+ "</ns2:Document>";
		currencyConversionAdviceResponse(acceptorAuthReqXmlRecr);
	}

	private void currencyConversionAdviceResponse(String acceptorAuthReqXmlRecr)
			throws FactoryConfigurationError, Exception {
		Document currencyConversionAdviceProcessorDocument = null;
		Mockito.when(jaxbHelper.unmarshall(com.girmiti.nexo.acceptorauthorizationrequest.Document.class, acceptorAuthReqXmlRecr,
				Constants.CAAA_001_001_08)).thenReturn(currencyConversionAdviceProcessorDocument);
		String process = currencyConversionAdviceProcessor.process(acceptorAuthReqXmlRecr);
		Assert.assertNotNull(process);
	}
}
