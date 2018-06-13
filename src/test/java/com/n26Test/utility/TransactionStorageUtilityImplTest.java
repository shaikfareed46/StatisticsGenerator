package com.n26Test.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;

/**
 * @author shaik fareed
 * 
 */

@RunWith(SpringRunner.class)
public class TransactionStorageUtilityImplTest {


	TransactionStorageUtilityImpl transactionStorageUtility;


	@Before
	public void setUp(){
		try {
			setPrivateStatic(TransactionStorageUtilityImpl.class.getDeclaredField("statsLastMinute"),new ConcurrentHashMap<>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionStorageUtility = new TransactionStorageUtilityImpl();
	}

	@After
	public void tearDown(){
		transactionStorageUtility = null;
	}

	@Test
	public void testSaveTransactionsFor201Status(){


		//Now lets test the controller

		//Transaction with valid timestamp
		Transaction transaction = new Transaction();
		transaction.setAmount(200.0);
		transaction.setTimestamp(System.currentTimeMillis());//Current time which is valid

		boolean actualResponse = transactionStorageUtility.saveTransaction(transaction);

		//Check if the data is succesfullySaved
		assertTrue(actualResponse);

	}

	@Test
	public void testSaveTransactionsFor204Status(){

		//Now lets test the controller

		//Transaction with invalid timestamp
		Transaction transaction = new Transaction();
		transaction.setAmount(200.0);
		transaction.setTimestamp(System.currentTimeMillis()-65000);//Timestamp which is 65 seconds ago

		boolean actualResponse = transactionStorageUtility.saveTransaction(transaction);

		//Check if the data is succesfullySaved
		assertFalse(actualResponse);

	}


	@Test
	public void testGetStatisticsWithNoTransactions() throws NoSuchFieldException, SecurityException, Exception{
		
		StatsSummary actualStatistics = transactionStorageUtility.getStatisticData();


		assertEquals(actualStatistics.getSum(), 0, 0);
		assertEquals(actualStatistics.getAvg(), 0,0);
		assertEquals(actualStatistics.getMax(), 0,0);
		assertEquals(actualStatistics.getMin(), 0,0);
		assertEquals(actualStatistics.getCount(), 0,0);

	}

	@Test
	public void testGetStatisticsWithTransactions() throws NoSuchFieldException, SecurityException, Exception{
		//Create mock statisticData with transactions
		mockTransactionsList();
		
		StatsSummary actualStatistics = transactionStorageUtility.getStatisticData();


		assertEquals(actualStatistics.getSum(), 30,30);
		assertEquals(actualStatistics.getAvg(), 15,15);
		assertEquals(actualStatistics.getCount(), 2,2);

	}

	private void mockTransactionsList() {
		Transaction transaction1 = new Transaction();
		transaction1.setAmount(10.0);
		transaction1.setTimestamp(System.currentTimeMillis());

		Transaction transaction2 = new Transaction();
		transaction2.setAmount(20.0);
		transaction2.setTimestamp(System.currentTimeMillis());


		Transaction transaction3 = new Transaction();
		transaction3.setAmount(10.0);
		transaction3.setTimestamp(System.currentTimeMillis()-65000);// Transaction happend 65 seconds ago, which is invalid

		transactionStorageUtility.saveTransaction(transaction1);
		transactionStorageUtility.saveTransaction(transaction2);
		transactionStorageUtility.saveTransaction(transaction3);

	}

	void setPrivateStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);        
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.PRIVATE);
		field.set(null, newValue);
	}


}
