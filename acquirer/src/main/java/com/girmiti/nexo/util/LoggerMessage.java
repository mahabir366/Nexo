package com.girmiti.nexo.util;

public class LoggerMessage {

	private LoggerMessage() {
		// Do Nothing
	}

	public static String getCallerName() {
		final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement callerObj = stacktrace[2];
		return callerObj.getClassName() + "::" + callerObj.getMethodName();
	}

	public static String testGetCallerName() {
		final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement callerObj = stacktrace[2];
		return "ERROR :: " + callerObj.getClassName() + "::" + callerObj.getMethodName();
	}
}
