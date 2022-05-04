package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Girmiti Software
 * @Date: 12-Dec-2019
 * @Time: 10:43:54 AM
 * @Version: 1.0
 * @Comments:
 */
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class DccRequest implements Serializable {

	private static final long serialVersionUID = -2066425524810904616L;

	private Long slNo;

	private String sourceCurrency;

	private String targetCurrency;

	private String reason;

	private String status;

	private Timestamp createDate;

	private Timestamp updateDate;

	private String conversionRate;

	private String merchantCode;

	private String conversionAmount;

	private String txnAmount;

	private String txnRefNumber;

}
