package com.girmiti.nexo.util;

import org.slf4j.Logger;

public class LogHelper {

	private LogHelper() {
		// Do nothing
	}

	public static void logEntry(Logger logger, String classMethodInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Entering:: ").append(classMethodInfo);
		String str=sb.toString();
		logger.debug(str,"logEntry Info");
	}

	public static void logExit(Logger logger, String classMethodInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Exiting:: ").append(classMethodInfo);
		String str=sb.toString();
		logger.debug(str,"logExit Info");
	}

	public static void logPerformance(Logger logger, String classMethodInfo, Long startTs, Long endTs) {
		StringBuilder sb = new StringBuilder();
		Double timeDiff = (endTs - startTs) / 1000.0;
		sb.append(classMethodInfo).append(" took ").append(timeDiff).append(" seconds");
		String str=sb.toString();
		logger.debug(str,"logPerformance Info");
	}

	public static void logInfo(Logger logger, String classMethodInfo, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(classMethodInfo).append(": ").append(message);
		String str=sb.toString();
		logger.debug(str,"logInfo Info");
	}

	public static void logDebug(Logger logger, String classMethodInfo, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(classMethodInfo).append(": ").append(message);
		String str=sb.toString();
		logger.debug(str,"logDebug Info");
	}

	public static void logError(Logger logger, String classMethodInfo, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("ERROR:: ").append(classMethodInfo).append(": ").append(message);
		String str=sb.toString();
		logger.debug(str,"logError Info");
	}

	public static void logError(Logger logger, String classMethodInfo, Throwable e, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("ERROR:: ").append(classMethodInfo).append(": ").append(message);
		String str=sb.toString();
		logger.debug(str,"logError Info",e);
	}

}
