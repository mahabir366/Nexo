package com.girmiti.nexo.acquirer.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SplitTxnData {

	private Long splitAmount;

	private String refMaskedPAN;

	private Long refMobileNumber;
}
