package com.n26Test.service;

import org.springframework.http.ResponseEntity;

import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;

/**
 * @author Shaik Fareed
 *
 */
public interface TransactionDataService {

	StatsSummary getStatistics();

	ResponseEntity<String> saveTransaction(Transaction transaction);
	
}
