package com.girmiti.nexo.acquirer.enums;

public enum TransactionStatus {
	APPROVED("00"), PARTIAL("111"), DECLINED("222");

	private final String value;

	TransactionStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
