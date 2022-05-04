package com.girmiti.nexo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MaskingData {

	private static final Logger logger = LoggerFactory.getLogger(MaskingData.class);

	public static String getMaskedData(String data) throws Exception {
		Document document = toXmlDocument(data);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StringWriter strWriter = new StringWriter();

		String xpathList = AcquirerProperties.getProperty("chatak.nexo.acquirer.xpath");
		String[] xpathArr = xpathList.split(",");
		for (String path : xpathArr) {
			// Find if this xpath exists in the XML data
			boolean isNodeExisting = checkIfNodeExists(document, path);
			// If exists, find the data of that element
			if (isNodeExisting) {
				XPath xpath = XPathFactory.newInstance().newXPath();
				NodeList nodes = (NodeList) xpath.evaluate(path, document, XPathConstants.NODESET);
				for (int idx = 0; idx < nodes.getLength(); idx++) {
					Node value = nodes.item(idx);
					value.setTextContent("****");
				}
			}
		}
		StreamResult result = new StreamResult(strWriter);
		transformer.transform(source, result);
		return strWriter.getBuffer().toString();
	}

	private static boolean checkIfNodeExists(Document document, String xpathExpression) throws Exception {
		boolean matches = false;
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// Create XPath object
		XPath xpath = xpathFactory.newXPath();
		try {
			// Create XPathExpression object
			XPathExpression expr = xpath.compile(xpathExpression);
			// Evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				matches = true;
			}

		} catch (XPathExpressionException e) {
			logger.info("Error in Masking the Response");
		}
		return matches;
	}

	private static Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		Document document = docBuilder.parse(new InputSource(new StringReader(str)));
		return document;
	}

	public static String trim(String input) {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		StringBuffer result = new StringBuffer();
		try {
			String line;
			while ((line = reader.readLine()) != null)
				result.append(line.trim());
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
