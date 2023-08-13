package com.shopme.admin.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Order;
import com.shopme.common.entity.OrderDetail;
import com.shopme.common.entity.OrderStatus;
import com.shopme.common.entity.PaymentMethod;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepositoryTests {

	@Autowired private OrderRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewOrderWithSingleProduct() {
		
		Customer customer =	entityManager.find(Customer.class, 9);
		Product product = entityManager.find(Product.class, 15);
		
		Order mainOder = new Order();
		mainOder.setOrderTime(new Date());
		mainOder.setCustomer(customer);
		
		mainOder.copyAddressFromCustomer();
		
		mainOder.setShippingCost(10);
		mainOder.setProductCost(product.getCost());
		mainOder.setTax(0);
		mainOder.setSubtotal(product.getPrice());
		mainOder.setTotal(product.getPrice() + 10);
		
		mainOder.setPaymentMethod(PaymentMethod.CREDIT_CAD);
		mainOder.setStatus(OrderStatus.NEW);
		mainOder.setDeliverDate(new Date());
		mainOder.setDeliverDays(1);
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setOrder(mainOder);
		orderDetail.setProductCost(product.getCost());
		orderDetail.setShippingCost(10);
		orderDetail.setQuantity(1);
		orderDetail.setSubtotal(product.getPrice());
		orderDetail.setUnitPrice(product.getPrice());
		
		mainOder.getOrderDetails().add(orderDetail);
		
		Order saveOrder = repo.save(mainOder);
		
		assertThat(saveOrder.getId()).isGreaterThan(0);
	}
	

	@Test
	public void testCreateNewOrderWithMultipleProduct() {
		
		Customer customer =	entityManager.find(Customer.class, 9);
		Product product1 = entityManager.find(Product.class, 18);
		Product product2 = entityManager.find(Product.class, 21);
		
		Order mainOder = new Order();
		mainOder.setOrderTime(new Date());
		mainOder.setCustomer(customer);
		mainOder.copyAddressFromCustomer();
		
		OrderDetail orderDetail1 = new OrderDetail();
		orderDetail1.setProduct(product1);
		orderDetail1.setOrder(mainOder);
		orderDetail1.setProductCost(product1.getCost());
		orderDetail1.setShippingCost(10);
		orderDetail1.setQuantity(1);
		orderDetail1.setSubtotal(product1.getPrice());
		orderDetail1.setUnitPrice(product1.getPrice());
		
		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setProduct(product2);
		orderDetail2.setOrder(mainOder);
		orderDetail2.setProductCost(product2.getCost());
		orderDetail2.setShippingCost(10);
		orderDetail2.setQuantity(1);
		orderDetail2.setSubtotal(product2.getPrice() * 2);
		orderDetail2.setUnitPrice(product2.getPrice());
		
		mainOder.getOrderDetails().add(orderDetail1);
		mainOder.getOrderDetails().add(orderDetail2);
		
		mainOder.setShippingCost(13);
		mainOder.setProductCost(product1.getCost() + product2.getCost());
		mainOder.setTax(0);
		float subtotal = product1.getPrice() + product2.getPrice();
		mainOder.setSubtotal(subtotal);
		mainOder.setTotal(subtotal + 13);
		
		mainOder.setPaymentMethod(PaymentMethod.COD);
		mainOder.setStatus(OrderStatus.PROCESSING);
		mainOder.setDeliverDate(new Date());
		mainOder.setDeliverDays(3);
		
		Order saveOrder = repo.save(mainOder);
		
		assertThat(saveOrder.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListOrders() {
		Iterable<Order> orders = repo.findAll();
		
		assertThat(orders).hasSizeGreaterThan(0);
		
		orders.forEach(System.out::println);
		
	}
	
	@Test
	public void testUpdateOrder() {
		Integer orderId = 2;
		Order order = repo.findById(orderId).get();
		
		order.setStatus(OrderStatus.SHIPPING);
		order.setPaymentMethod(PaymentMethod.COD);
		order.setOrderTime(new Date());
		order.setDeliverDays(6);
		
		Order updatedOrder =repo.save(order);
		
		assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.SHIPPING);
		
	}
	
	@Test
	public void testGetOrder() {
		Integer orderId = 2;
		Order order = repo.findById(orderId).get();
		assertThat(order).isNotNull();
		System.out.println(order);
	}
	
	@Test
	public void deletedOrder() {
		Integer orderId = 2;
		repo.deleteById(orderId);
		Optional<Order> result = repo.findById(orderId);
		assertThat(result).isNotPresent();
	}
	
	
}
