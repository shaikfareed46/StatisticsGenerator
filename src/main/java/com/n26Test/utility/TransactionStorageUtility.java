package com.n26Test.utility;

import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;

/**
 * @author Shaik Fareed
 *
 */
public interface TransactionStorageUtility {

	/**
	 * @param transaction
	 * @return
	 */
	boolean saveTransaction(Transaction transaction);

	/**
	 * @return
	 */
	StatsSummary getStatisticData();

}
