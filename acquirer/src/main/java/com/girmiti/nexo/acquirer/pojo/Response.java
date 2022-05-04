package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class Response implements Serializable {

	private static final long serialVersionUID = 8718242248038766459L;

	private String statusMessage;

	private String errorCode;

	private String errorMessage;

	private String errorMessageExt;

	private Long txnDateTime;

	private String txnRefNumber;

}
