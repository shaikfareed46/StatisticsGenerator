package com.n26Test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;
import com.n26Test.utility.TransactionStorageUtilityImpl;

/**
 * @author shaik fareed
 *
 */
@Service
public class TransactionServiceImpl implements TransactionDataService {
	
	private Logger log = LoggerFactory.getLogger(TransactionStorageUtilityImpl.class);
	
	@Autowired
	private TransactionStorageUtilityImpl transactionUtility;
	
	
	/* (non-Javadoc)
	 * @see com.n26Test.service.TransactionDataService#getStatistics()
	 */
	@Override
	public StatsSummary getStatistics() {
		log.info("Fetching stats...");
		return transactionUtility.getStatisticData();
	}


	/* (non-Javadoc)
	 * @see com.n26Test.service.TransactionDataService#saveTransaction(com.n26Test.model.Transaction)
	 */
	@Override
	public ResponseEntity<String> saveTransaction(Transaction transaction) {
		log.info("Saving transactions...");
		boolean transactionStatusSuccessful = transactionUtility.saveTransaction(transaction);
		if(transactionStatusSuccessful){
			return new ResponseEntity<String>("",HttpStatus.CREATED);
		}else{
			return new ResponseEntity<String>("",HttpStatus.NO_CONTENT);
		}
	}

}
