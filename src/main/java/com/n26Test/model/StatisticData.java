package com.n26Test.model;

/**
 * @author shaik fareed
 *
 */
public class StatisticData {

	private double sum;

	private double avg;

	private double max;

	private double min;

	private long count;
	
	private long timeStamp;
	
	public StatisticData(){
		
	}
	
	public StatisticData(StatisticData statistics) {
        this.sum = statistics.getSum();
        this.count = statistics.getCount();
        this.max = statistics.getMax();
        this.min = statistics.getMin();
    }

	/**
	 * @return sum
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * @param sum
	 */
	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * @return avg
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * @param avg
	 */
	public void setAvg(double avg) {
		this.avg = avg;
	}

	/**
	 * @return max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @param max
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * @return
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @param min
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @return count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timeStamp = timestamp;
	}

	/**
	 * @return timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
    public String toString() {
        return "StatisticsSummary{" +
                "sum=" + sum +
                ", count=" + count +
                ", max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                '}';
    }

}
