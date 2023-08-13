package com.shopme.admin.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>,JpaRepository<Order, Integer>{
	

}
