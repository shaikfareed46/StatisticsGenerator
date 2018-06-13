package com.n26Test.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.n26Test.model.StatisticData;
import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;

/**
 * @author shaik fareed
 * 
 * This class acts as a repository where we store the computations in a ConcurrentHashMap
 * 
 * ConcurrentHashMap provides thread safety
 * For every new Transaction we are updating existing summary data , By using map.compute
 * In getStatisticData method we are just using this map and ignoring the old stats
 * Hence the getStatisticData can be computed in O(1).
 *
 */

@Component
public class TransactionStorageUtilityImpl implements TransactionStorageUtility {

	private static final int SECONDS_STAT = 60;

	private Logger log = LoggerFactory.getLogger(TransactionStorageUtilityImpl.class);
	
	/**
	 * Java 8 provides has the feature to compute and update a map,So For every new transaction we can update
	 * Concurrent HashMap is used here , to make it thread safe
	 */
	private static Map<Integer, StatisticData> statsLastMinute = new ConcurrentHashMap<>(SECONDS_STAT);

	/**
	 * @param transaction
	 * @return if the transaction is valid adds to already computed statistic
	 * data, hence the transaction save time is O(1)
	 */
	@Override
	public boolean saveTransaction(Transaction transaction) {
		log.info("Saving and computing transaction.....");
		if (isValidTransaction(transaction)) {
			int second = LocalDateTime
					.ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()), ZoneId.systemDefault()).getSecond();
			statsLastMinute.compute(second, (key, value) -> {
				
				// If there are 	
				if (value == null || (System.currentTimeMillis() - value.getTimeStamp()) / 1000 >= SECONDS_STAT) {
					value = new StatisticData();
					System.out.println(transaction.getTimestamp());
					value.setTimestamp(transaction.getTimestamp());
					value.setSum(transaction.getAmount());
					value.setMax(transaction.getAmount());
					value.setMin(transaction.getAmount());
					value.setCount(1l);
					return value;
				}

				value.setCount(value.getCount() + 1);
				value.setSum(value.getSum() + transaction.getAmount());
				if (Double.compare(transaction.getAmount(), value.getMax()) > 0)
					value.setMax(transaction.getAmount());
				if (Double.compare(transaction.getAmount(), value.getMin()) < 0)
					value.setMin(transaction.getAmount());
				return value;
			});
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the Statistics related to all the transaction occurred in last 60 seconds
	 * Here the data computation is already in place and we just have to ignore the old data
	 * 
	 */
	@Override
	public StatsSummary getStatisticData() {
		log.info("Fetching stats.....");
		StatsSummary summary = statsLastMinute.values().stream()
				.filter(s -> (System.currentTimeMillis() - s.getTimeStamp()) / 1000 < SECONDS_STAT)
				.map(StatsSummary::new)
				.reduce(new StatsSummary(), (s1, s2) -> {
					s1.setSum(s1.getSum() + s2.getSum());
					s1.setCount(s1.getCount() + s2.getCount());
					s1.setMax(Double.compare(s1.getMax(), s2.getMax()) > 0 ? s1.getMax() : s2.getMax());
					s1.setMin(Double.compare(s1.getMin(), s2.getMin()) < 0 ? s1.getMin() : s2.getMin());
					return s1;
				});

		summary.setMin(Double.compare(summary.getMin(), Double.MAX_VALUE) == 0 ? 0.0 : summary.getMin());
		summary.setMax(Double.compare(summary.getMax(), Double.MIN_VALUE) == 0 ? 0.0 : summary.getMax());
		summary.setAvg(summary.getCount() > 0l ? summary.getSum() / summary.getCount() : 0.0);

		log.info("Statistics summary for last minute => {}", summary);
		return summary;
	}

	/**
	 * @param transaction
	 * @return check if the timestamp is within 60 seconds of current time
	 */
	private boolean isValidTransaction(Transaction transaction) {
		log.info("Validating transaction.....");
		if (transaction != null) {
			Date currentdate = new Date();
			long timeDiffrence = TimeUnit.MILLISECONDS.toSeconds(currentdate.getTime()) - transaction.getTimestamp()/1000;
			log.info("Transaction is :" + timeDiffrence + " older");
			return timeDiffrence < SECONDS_STAT;
		}

		return false;
	}

}
