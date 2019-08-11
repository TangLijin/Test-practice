package sales;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
	@Mock
	SalesDao salesDao;

	@InjectMocks
	SalesApp mockSalesApp;

	@Test
	public void testIsSalesIdEffective_giveYesterdayStartAndTomorrowEnd_thenReturnTrue(){
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);

		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(yesterday.getTime());
		when(sales.getEffectiveTo()).thenReturn(tomorrow.getTime());
		boolean result =mockSalesApp.isSalesIdEffective(sales);

		assertEquals(true,result);
	}

	@Test
	public void testIsSalesIdEffective_giveYesterdayBeforeStartAndYesterdayEnd_thenReturnFalse(){
		Calendar yesterdayBefore = Calendar.getInstance();
		yesterdayBefore.add(Calendar.DATE, -2);
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);

		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(yesterdayBefore.getTime());
		when(sales.getEffectiveTo()).thenReturn(yesterday.getTime());
		boolean result =mockSalesApp.isSalesIdEffective(sales);

		assertEquals(false,result);
	}

	@Test
	public void testIsSalesIdEffective_giveTomorrowStartAndFurtherDayEnd_thenReturnFalse(){
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		Calendar furtherDay = Calendar.getInstance();
		furtherDay.add(Calendar.DATE, 5);

		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(tomorrow.getTime());
		when(sales.getEffectiveTo()).thenReturn(furtherDay.getTime());
		boolean result =mockSalesApp.isSalesIdEffective(sales);

		assertEquals(false,result);
	}


//	@Test
//	public void testGetSales_giveSalesIdIsNull_thenReturnNull(){
//		Sales sales =mockSalesApp.getSales(null);
//		Assert.assertNull(sales);
//	}
//
//	@Test
//	public void testGetSales_giveSalesIdAndisEffectiveSalesId_thenReturnSales(){
//		Sales sales = mock(Sales.class);
//		when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
//		when(mockSalesApp.isSalesIdEffective(any())).thenReturn(true);
//
//		Sales result = mockSalesApp.getSales(anyString());
//
//		Assert.assertNotNull(result);
//	}




}
