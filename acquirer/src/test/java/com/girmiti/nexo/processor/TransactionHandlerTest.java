package com.girmiti.nexo.processor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class TransactionHandlerTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TransactionHandlerTest.class);
	
	@InjectMocks
	TransactionHandler transactionHandler;
	
	@Test
	public void testProcessAccptrAuthstnReq() {
		String request = "<AccptrAuthstnReq>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrAuthstnReq method", e);
		}
	}
	
	@Test
	public void testProcessData() {
		String request = "<data>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessData method", e);
		}
	}
	
	@Test
	public void testProcessAccptrCxlAdvc() {
		String request = "<AccptrCxlAdvc>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrCxlAdvc method", e);
		}
	}
	
	@Test
	public void testProcessAccptrBtchTrf() {
		String request = "<AccptrBtchTrf>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrBtchTrf method", e);
		}
	}
	
	@Test
	public void testProcessAccptrCxlReq() {
		String request = "<AccptrCxlReq>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrCxlReq method", e);
		}
	}
	
	@Test
	public void testProcessAccptrDgnstcReq() {
		String request = "<AccptrDgnstcReq>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrDgnstcReq method", e);
		}
	}
	
	@Test
	public void testProcessAccptrCcyConvsAdvc() {
		String request = "<AccptrCcyConvsAdvc>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrCcyConvsAdvc method", e);
		}
	}
	
	@Test
	public void testProcessAccptrRcncltnReq() {
		String request = "<AccptrRcncltnReq>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrRcncltnReq method", e);
		}
	}
	
	@Test
	public void testProcessAccptrCmpltnAdvc() {
		String request = "<AccptrCmpltnAdvc>";
		byte[] byteArray = request.getBytes();
		try {
			String result = TransactionHandler.process(byteArray);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrCmpltnAdvc method", e);
		}
	}
	
	@Test
	public void testProcessAccptrCcyConvsReq() {
		String request = "<AccptrCcyConvsReq>";
		byte[] byteArray = request.getBytes();
		try {String result = TransactionHandler.process(byteArray);
		Assert.assertNotNull(result);
		} catch (Exception e) {
			LOGGER.error("ERROR :: TransactionHandlerTest :: testProcessAccptrCcyConvsReq method", e);
		}
	}

}
