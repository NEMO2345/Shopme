package com.shopme.address;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;

public interface AddressRepository extends CrudRepository<Address, Integer> {
	
	public List<Address> findByCustomer(Customer customer);
	
	@Query("select c from Customer c where c.email = ?1")
	public Address findByIdAndCustomer(Integer addressId, Integer customerId);
	
	@Query("delete from Address a where a.id = ?1 and a.customer.id = ?2")
	@Modifying
	public void deleteByIdAndCustomer(Integer addressId,Integer customerId);
	
}