package com.n26Test.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26Test.model.StatsSummary;
import com.n26Test.model.Transaction;
import com.n26Test.service.TransactionDataService;

/**
 * @author shaik fareed
 *
 */

@RunWith(SpringRunner.class)
public class TransactionDataControllerTest {
	
	@Mock
	TransactionDataService transactionDataService;
	
	
	@InjectMocks
	TransactionDataController transactionDataController;
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown(){
		transactionDataController = null;
	}
	
	@Test
	public void testSaveTransactionsFor201Status(){
		ResponseEntity<String> expectedResponse = new ResponseEntity<String>("",HttpStatus.CREATED);
		when(transactionDataService.saveTransaction(any(Transaction.class))).thenReturn(expectedResponse);
		
		//Now lets test the controller
		Transaction transaction = new Transaction();
		transaction.setAmount(200.0);
		transaction.setTimestamp(1510238227*1000);//Some Random time
		
		ResponseEntity<String> actualResponse = transactionDataController.saveTransaction(transaction);
		
		assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
		
	}
	
	@Test
	public void testSaveTransactionsFor204Status(){
		ResponseEntity<String> expectedResponse = new ResponseEntity<String>("",HttpStatus.NO_CONTENT);
		when(transactionDataService.saveTransaction(any(Transaction.class))).thenReturn(expectedResponse);
		
		//Now lets test the controller
		Transaction transaction = new Transaction();
		transaction.setAmount(200.0);
		transaction.setTimestamp(15102382*1000);//Some Random time
		
		ResponseEntity<String> actualResponse = transactionDataController.saveTransaction(transaction);
		
		assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
		
	}
	
	
	@Test
	public void testGetStatisticsWithNoTransactions(){
		//Create mock statisticData with no transactions
		StatsSummary statisticMockData = mockDataForNoTransactions();
		when(transactionDataService.getStatistics()).thenReturn(statisticMockData);
		
		//Test for no Data
		StatsSummary statisticActualData = transactionDataController.getStatistics();
		
		assertEquals(statisticMockData.getSum(), statisticActualData.getSum(), 0);
		assertEquals(statisticMockData.getAvg(), statisticActualData.getAvg(), 0);
		assertEquals(statisticMockData.getMax(), statisticActualData.getMax(), 0);
		assertEquals(statisticMockData.getMin(), statisticActualData.getMin(), 0);
		assertEquals(statisticMockData.getCount(), statisticActualData.getCount(), 0);
		
	}
	
	@Test
	public void testGetStatisticsWithTransactions(){
		//Create mock statisticData with no transactions
		StatsSummary statisticMockData = mockDataWithTransactions();
		when(transactionDataService.getStatistics()).thenReturn(statisticMockData);
		
		//Test for no Data
		StatsSummary statisticActualData = transactionDataController.getStatistics();
		
		assertEquals(statisticMockData.getSum(), statisticActualData.getSum(), 100);
		assertEquals(statisticMockData.getAvg(), statisticActualData.getAvg(), 10);
		assertEquals(statisticMockData.getMax(), statisticActualData.getMax(), 10);
		assertEquals(statisticMockData.getMin(), statisticActualData.getMin(), 10);
		assertEquals(statisticMockData.getCount(), statisticActualData.getCount(), 10);
		
	}

	private StatsSummary mockDataWithTransactions() {
		StatsSummary statisticData = new StatsSummary();
		statisticData.setAvg(10);
		statisticData.setCount(10);
		statisticData.setMax(10);
		statisticData.setMin(10);
		statisticData.setSum(100);
		return statisticData;
	}

	private StatsSummary mockDataForNoTransactions() {
		StatsSummary statisticData = new StatsSummary();
		statisticData.setAvg(0);
		statisticData.setCount(0);
		statisticData.setMax(0);
		statisticData.setMin(0);
		statisticData.setSum(0);
		return statisticData;
	}
}
