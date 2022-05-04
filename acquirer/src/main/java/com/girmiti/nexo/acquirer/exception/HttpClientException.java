package com.girmiti.nexo.acquirer.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpClientException extends Exception {

	private static final long serialVersionUID = -4822087718708677745L;

	private String httpErrorCode;

	private int statusCode;

	public HttpClientException(String httpErrorCode, int statusCode) {
		super();
		this.httpErrorCode = httpErrorCode;
		this.statusCode = statusCode;
	}

}

