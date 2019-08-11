package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	SalesDao salesDao;
	SalesReportDao salesReportDao;
	EcmService ecmService;

	public SalesApp() {
	}

	public SalesApp(SalesDao salesDao, SalesReportDao salesReportDao, EcmService ecmService) {
		this.salesDao = salesDao;
		this.salesReportDao = salesReportDao;
		this.ecmService = ecmService;
	}

	public void generateSalesActivityReport(String salesId,int maxRow, boolean isNatTrade, boolean isSupervisor){
		Sales sales = getSales(salesId);
		List<SalesReportData> salesReportDataList = getsalesReportDataList(sales);

		List<SalesReportData> filteredReportDataList = filterReportDataList(salesReportDataList, isSupervisor, maxRow);

		List<String> headers = getHeaders(isNatTrade);

		SalesActivityReport report = generateReport(headers, salesReportDataList);

		uploadReportDocument(report);
	}

	private List<SalesReportData> filterReportDataList(List<SalesReportData> reportDataList,boolean isSupervisor, int maxRow) {
		List<SalesReportData> filteredReportDataList_first = null;
		for (SalesReportData data : reportDataList) {
			if ("SalesActivity".equalsIgnoreCase(data.getType())) {
				if(data.isConfidential() && !isSupervisor) {
					continue;
				}
				filteredReportDataList_first.add(data);
			}
		}
		List<SalesReportData> filteredReportDataList_second = new ArrayList<>();
		for (int i=0; i < filteredReportDataList_first.size() || i < maxRow; i++) {
			filteredReportDataList_second.add(filteredReportDataList_first.get(i));
		}
		return filteredReportDataList_second;
	}

	private void uploadReportDocument(SalesActivityReport report) {
		EcmService ecmService = new EcmService();
		ecmService.uploadDocument(report.toXml());
	}


	private List<String> getHeaders(boolean isNatTrade) {
		return isNatTrade ? Arrays.asList("Sales ID", "Sales Name", "Activity", "Time")
				: Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
	}

	public Sales getSales(String salesId) {
		if (salesId == null) {
			return null;
		}
		Sales sales = salesDao.getSalesBySalesId(salesId);
		if(!isSalesIdEffective(sales)){
			return null;
		}
		return sales;
	}

	public List<SalesReportData> getsalesReportDataList(Sales sales){
		return salesReportDao.getReportData(sales);
	}

	public boolean isSalesIdEffective(Sales sales) {
		Date today = new Date();
		if (today.after(sales.getEffectiveTo()) || today.before(sales.getEffectiveFrom())){
			return false;
		}
		return true;
	}

	private SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}
