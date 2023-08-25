package com.shopme.admin.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.order.OrderRepository;
import com.shopme.common.entity.order.Order;

@Service
public class MasterOrderReportService {

	@Autowired private OrderRepository repo;
	private DateFormat dateFormatter;
	
	public List<ReportIterm> getReportDataLast7Days(){
		return getReportDataLastXDays(7);
	}
	public List<ReportIterm> getReportDataLast28Days(){
		return getReportDataLastXDays(28);
	}
	
	private List<ReportIterm> getReportDataLastXDays(int days){
		Date endTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -(days -1));
		Date startTime =	cal.getTime();
		
		System.out.println("Start time: " + startTime);
		System.out.println("Start time: " + endTime);

		dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		return getReportDataByDateRange(startTime,endTime);
	}
	
	private List<ReportIterm> getReportDataByDateRange(Date startTime,Date endTime){
		
		List<Order> listOrders	= repo.findByOrderTimeBetween(startTime, endTime);
		printRawDate(listOrders);
		
		List<ReportIterm> listReportIterms = createReportData(startTime,endTime);
		System.out.println();
		
		calculateSalesForReportData(listOrders, listReportIterms);
		
		printReportData(listReportIterms);
		
		return listReportIterms;
		
	}
	
	private void calculateSalesForReportData(List<Order> listOrders,List<ReportIterm> listReportIterms) {
		for(Order order : listOrders) {
			String orderDateString = dateFormatter.format(order.getOrderTime());
			
			ReportIterm reportIterm = new ReportIterm(orderDateString);
			
			int itemIndex =	listReportIterms.indexOf(reportIterm);
			if(itemIndex >= 0) {
				reportIterm = listReportIterms.get(itemIndex);
				
				reportIterm.addGrossSales(order.getTotal());
				reportIterm.addNetSales(order.getSubtotal() - order.getProductCost());
				reportIterm.increaseOrdersCount();
			}
		}
	}

	private void printReportData(List<ReportIterm> listReportIterms) {
		listReportIterms.forEach(item -> {
			System.out.printf("%s, %10.2f, %10.2f, %d \n",item.getIdentifier(),item.getGrossSales(),item.getNetSales(),item.getOrdersCount());
		});
		
	}

	private List<ReportIterm> createReportData(Date startTime, Date endTime) {
		List<ReportIterm> listReportIterms = new ArrayList<>();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startTime);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(endTime);
		
		Date currentDate = startDate.getTime();
		String dateString = dateFormatter.format(currentDate);
		
		listReportIterms.add(new ReportIterm(dateString));
		
		do {
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			currentDate = startDate.getTime();
			dateString = dateFormatter.format(currentDate);
			
			listReportIterms.add(new ReportIterm(dateString));
			
		} while (startDate.before(endDate));
		
		return listReportIterms;
		
	}

	private void printRawDate(List<Order> listOrders) {
		listOrders.forEach(order -> {
			System.out.printf("%3d | %s | %10.2f | %10.2f \n",
					order.getId(),order.getOrderTime(),order.getTotal(),order.getProductCost());
		});
		
	}
	
}
