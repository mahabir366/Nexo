package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaignosticTransactionRequest implements Serializable {

	private static final long serialVersionUID = 7023305078527473750L;

	private Boolean status;
	private byte[] mac;

}
