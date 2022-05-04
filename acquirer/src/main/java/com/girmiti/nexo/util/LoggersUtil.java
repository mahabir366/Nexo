package com.girmiti.nexo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class LoggersUtil {
	
private static final String DATE_TIME_FORMAT= "dd-MM-yyyy HH:mm:ss";
	
	private static final String TIME_STAMP="Timestamp: ";
	
	private static final String SOURCE_IP =" | SourceIp: ";
	
	private static final String USER_NAME= " | UserName: ";
	
	private static final String REQUEST_URL= " | RequestUrl: ";
	
	private static final String LOGGING_STATUS=" | Logging Status: ";
	
	private static final String SESSION_ID= "| SessionId: ";
	
	private LoggersUtil() {
		//Do nothing
	}

	private static String request = "requestId";
	//Need to add session Id in Headers in PostRequest
	public static String logIpUrlRequest(HttpServletRequest requestToCache) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date date = new Date();
		requestToCache.setAttribute(request, null);
		return TIME_STAMP + formatter.format(date) + SOURCE_IP + requestToCache.getRemoteAddr()
				+ USER_NAME + SESSION_ID + request + REQUEST_URL + requestToCache.getRequestURI()
				+ LOGGING_STATUS;
	}

	public static String logIpUrl(HttpServletRequest requestToCache) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date date = new Date();
		String requestId = (String) requestToCache.getAttribute(request);
		return TIME_STAMP + formatter.format(date) +SOURCE_IP + requestToCache.getRemoteAddr()
				+ USER_NAME + SESSION_ID + requestId + REQUEST_URL + requestToCache.getRequestURI()
				+ LOGGING_STATUS;
	}

}