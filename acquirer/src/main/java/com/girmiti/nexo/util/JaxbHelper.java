package com.girmiti.nexo.util;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.girmiti.nexo.acceptorrejection.MessageFunction9Code;
import com.girmiti.nexo.acceptorrejection.RejectionMessageRequest;

public class JaxbHelper {
	
	static Logger logger = Logger.getLogger(JaxbHelper.class);
	
	public static String marshall(Object obj,Object obj2) throws JAXBException, XMLStreamException, FactoryConfigurationError {
		Marshaller jaxbMarshaller = JAXBContext.newInstance(obj.getClass()).createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JAXBElement jaxbElement =
	            new JAXBElement( new QName(obj.getClass().getSimpleName()),
//	            		com.girmiti.nexo.authres.Document.class,
	            		obj2.getClass(),
	            		obj);

        StringWriter sw = new StringWriter();			
        jaxbMarshaller.marshal(jaxbElement, sw);
		return sw.toString();
	}

	public Object unmarshall(Class<?> clazz, String xml, String xsdFile)
			throws FactoryConfigurationError, Exception {
		xml = xml.replaceFirst("xmlns:ns2=\"urn:iso:std:iso:20022", "xmlns=\"urn:iso:std:iso:20022");
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		RejectionMessageRequest rejectionMessageRequest = new RejectionMessageRequest();
		rejectionMessageRequest.setMessageFunction(MessageFunction9Code.RJCP);
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
		rejectionMessageRequest.setTxnDate(now);
		Object obj = null;
		try {
			String xmlRequest = xml.replaceAll("True", "true");
			xmlRequest = xmlRequest.replaceAll("False", "false");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			InputStream inputStream = getClass()
					.getClassLoader().getResourceAsStream(xsdFile);
			Schema nexoSchema = sf.newSchema(stream2file(inputStream,xsdFile));
			jaxbUnmarshaller.setSchema(nexoSchema);

			obj = JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xmlRequest.getBytes())));
		} catch (Exception e) {
			logger.error("Exception Occured while Unmarshalling Requset", e);
			throw e;
		}
		return obj;
	}
	
	public File stream2file(InputStream in, String xsdFile) throws IOException {
		final File tempFile = File.createTempFile(xsdFile, ".xsd");
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return tempFile;
	}

}
