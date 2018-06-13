package com.n26Test.model;

/**
 * @author shaik fareed
 *
 */
public class Transaction {

	private double amount;
	
	private long timestamp;

	/**
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
