package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DccResponse extends Response {

	private static final long serialVersionUID = -146498613875525147L;

	private List<DccRequest> dccs;

	private Integer totalNoDccs;
	
	private String conversionAmount;

}
