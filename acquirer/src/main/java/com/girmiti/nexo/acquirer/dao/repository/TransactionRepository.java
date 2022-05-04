package com.girmiti.nexo.acquirer.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.girmiti.nexo.acquirer.dao.model.NexoTxn;

public interface TransactionRepository extends JpaRepository<NexoTxn, Long>,QuerydslPredicateExecutor<NexoTxn> {
	
	public NexoTxn findBypgTxnRef(String pgTxnRef);
	
	@Query(value = "select nextval('seq_dummy_test')", nativeQuery = true)
	 public String getNextSeriesId();

	public List<NexoTxn> findByCaptureStatus(String captureStatus);

}
