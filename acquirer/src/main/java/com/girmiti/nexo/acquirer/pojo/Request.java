package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import com.girmiti.nexo.acquirer.enums.EntryModeEnum;
import com.girmiti.nexo.acquirer.enums.ShareModeEnum;
import com.girmiti.nexo.acquirer.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request implements Serializable {

	private static final long serialVersionUID = 3463794052777695279L;

	private String createdBy;

	private String originChannel;

	private String merchantCode;

	private String terminalId;

	private TransactionType transactionType;

	private EntryModeEnum entryMode;

	private ShareModeEnum shareMode;

	private String posEntryMode;

	private String mode;

	private String processorMid;
}
