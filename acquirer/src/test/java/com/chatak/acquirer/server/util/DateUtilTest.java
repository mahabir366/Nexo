package com.chatak.acquirer.server.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.girmiti.nexo.util.DateUtil;



@RunWith(MockitoJUnitRunner.class)
public class DateUtilTest {

	@Before
	public void setUp() {
		java.util.Properties properties = new Properties();
		properties.setProperty("cs.admin.wallet.date.format", "dd/mm/yy");
		properties.setProperty("cs.admin.wallet.registration.age.limit","18");
		properties.setProperty("chatak.service.vts.date.format.one","02-05");
		properties.setProperty("chatak.service.vts.date.format.two","dd/mm/yyyy");
		properties.setProperty("cs.admin.wallet.date.time.format","dd/mm/yyyy");
		properties.setProperty("payment.loader.script.date.format.mmddyyyy","mm/dd/yyyy");
		properties.setProperty("payment.loader.script.date.formats.yyyymmdd","yyyy/mm/dd");
		//com.girmiti..util.UserProperties.mergeProperties(properties);
	}
	
	
	@Test
	public void testtoDateStringFormat() {
		Timestamp date =new Timestamp(System.currentTimeMillis());
		String pattern ="dd/mm/yy";
		Assert.assertNotNull(DateUtil.toDateStringFormat(date, pattern));
	}
	
	
	@Test
	public void testtoTimestamp() {
		String pattern ="dd/mm/yy";
		String date ="12/05/99";
		Assert.assertNotNull(DateUtil.toTimestamp(date, pattern));
	}
	
	@Test
	public void testisValidDate() {
		String inDate = "1998/11/11";
		Assert.assertNotNull(DateUtil.appendFromTime(inDate));
	}
	
	@Test
	public void testappendToTime() {
		String inDate = "1998/11/11";
		Assert.assertNotNull(DateUtil.appendToTime(inDate));
	}
	
	@Test
	public void testconvertStringToTimestamp() {
		String inDate = "1998/11/11";
		Assert.assertNotNull(DateUtil.convertStringToTimestamp(inDate));
	}
	
	@Test
	public void testsetGenerationDateTimeResponse() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Assert.assertNotNull(DateUtil.setGenerationDateTimeResponse(timestamp));
	}
	
	@Test
	public void testsetGenerationDateStartRequest() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Assert.assertNotNull(DateUtil.setGenerationDateStartRequest(timestamp));
	}
}
