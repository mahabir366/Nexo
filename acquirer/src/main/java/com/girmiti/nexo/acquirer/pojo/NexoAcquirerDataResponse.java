package com.girmiti.nexo.acquirer.pojo;

import java.util.List;

import com.girmiti.nexo.acquirer.dao.model.NexoTxn;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NexoAcquirerDataResponse {
	
	private String response;
	
	private List<NexoTxn> nexoTxns;
}
