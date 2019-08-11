package sales;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
	@Mock
	SalesDao salesDao;

	@Mock
	SalesReportDao salesReportDao;

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


	@Test
	public void testGetSales_giveSalesIdIsNull_thenReturnNull(){
		Sales sales =mockSalesApp.getSales(null);
		Assert.assertNull(sales);
	}

//	@Test
//	public void testGetSales_giveSalesIdAndisEffectiveSalesId_thenReturnSales(){
//
//		Sales sales = mock(Sales.class);
//
//		when(mockSalesApp.isSalesIdEffective(sales)).thenReturn(true);
//		when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
//
//		Sales result = mockSalesApp.getSales(anyString());
//
//		Assert.assertNotNull(result);
//	}

	@Test
	public void testGetSalesReportDataList_giveSales_thenReturnReportDataList() {
		List<SalesReportData> reportDataList = Arrays.asList(new SalesReportData());
		when(salesReportDao.getReportData(any())).thenReturn(reportDataList);

		List<SalesReportData> result = mockSalesApp.getsalesReportDataList(any());

		Assert.assertEquals(1, result.size());
		verify(salesReportDao, times(1)).getReportData(any());
	}

	@Test
	public void testGetHeaders_giveIsNatTradeIsTrue_thenReturnHeadersContainsStringTime() {
		SalesApp salesApp = new SalesApp();
		List<String> headers = salesApp.getHeaders(true);

		Assert.assertEquals("Time", headers.get(3));
	}

	@Test
	public void testGetHeaders_giveIsNatTradeIsFalse_thenReturnHeadersContainsStringLocalTime() {
		SalesApp salesApp = new SalesApp();
		List<String> headers = salesApp.getHeaders(false);

		Assert.assertEquals("Local Time", headers.get(3));
	}

	@Test
	public void testGenerateReport_giveSaleIdAndIsNatTrade() {
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = mock(Sales.class);
		doReturn(true).when(salesApp).isSalesIdEffective(any());
		doReturn(sales).when(salesApp).getSales(any());
		doReturn(new ArrayList<String>()).when(salesApp).getsalesReportDataList(any());
		doReturn(new ArrayList<String>()).when(salesApp).getHeaders(anyBoolean());
		doReturn(new SalesActivityReport()).when(salesApp).generateReport(anyList(), anyList());
		doNothing().when(salesApp).uploadReportDocument(any());

		salesApp.generateSalesActivityReport("404",5,true, true);

		verify(salesApp, times(1)).getSales(any());
		verify(salesApp, times(1)).getsalesReportDataList(any());
		verify(salesApp, times(1)).generateReport(anyList(), anyList());
		verify(salesApp, times(1)).getHeaders(anyBoolean());
		verify(salesApp, times(1)).uploadReportDocument(any());
	}

}
