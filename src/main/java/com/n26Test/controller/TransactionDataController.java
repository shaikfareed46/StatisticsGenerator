package com.n26Test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;
import com.n26Test.service.TransactionDataService;

/**
 * @author shaik fareed
 *
 */
@RestController
public class TransactionDataController {
	
	private Logger logger = LoggerFactory.getLogger(TransactionDataController.class);

	@Autowired
	private TransactionDataService transactionDataService;

	/**
	 * @param transaction
	 * @return If transaction is valid then it returns 201 , else it will return 204
	 */
	@RequestMapping(value="transactions",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveTransaction(@RequestBody Transaction transaction){
		logger.info("Saving transactions.....");
		return transactionDataService.saveTransaction(transaction);
	}
	
	/**
	 * @return the statistics of transactions that occurred within last 60 seconds
	 */
	@RequestMapping(value="statistics",method=RequestMethod.GET)
	public @ResponseBody StatsSummary getStatistics(){
		logger.info("Fetching statistics.....");
		StatsSummary statisticData = transactionDataService.getStatistics();
		return statisticData;
	}


}
