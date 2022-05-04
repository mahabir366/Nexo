package com.girmiti.nexo.acquirer.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@Entity
@Table(name = "nexo_txn")
public class NexoTxn implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "seq_nexo_txn_info", sequenceName = "seq_nexo_txn_info", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nexo_txn_info")
	@Column(name="nexo_txn_id")
	private Long nexoTxnId;
	
	@Column(name="request_type")
	private String requestType;
	
	@Column(name="msgfctn")
	private String msgFctn;

	@Column(name="credttm")
	private Timestamp creDtTm;

	@Column(name="acqrr_id")
	private String acqrrId;
	
	@Column(name="initgpty_id")
	private String initgPtyId;
	
	@Column(name="mrchnt_id")
	private String mrchntId;
	
	@Column(name="tx_txref")
	private String txTxRef;

	@Column(name="request_data")
	private String requestData;

	@Column(name="response_data")
	private String responseData;

	@Column(name="txrspn_rspn")
	private String txRspn;
	
	@Column(name="pg_txnref")
	private String pgTxnRef;
	
	@Column(name="capture_status")
	private String captureStatus;
	
	@Column(name="cg_txnref")
	private String cgTxnRef;
	
	@Column(name="pg_rrn")
	private String pgRrn;

	
}
