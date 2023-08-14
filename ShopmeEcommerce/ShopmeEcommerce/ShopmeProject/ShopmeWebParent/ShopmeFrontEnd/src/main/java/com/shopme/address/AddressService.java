package com.shopme.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService {

	@Autowired private AddressRepository repo;
	
	
	public List<Address> listAddressBook(Customer customer){
		return repo.findByCustomer(customer);
	}
	
	public void save(Address address) {
		repo.save(address);
	}
	
	public Address get(Integer addressId,Integer customerId) {
		return repo.findByIdAndCustomer(addressId, customerId);
	}

	public void delete(Integer addressId,Integer customerId) {
		repo.deleteByIdAndCustomer(addressId, customerId);
	}
	
	public void setDefaultAddress(Integer defaultAddressId,Integer customerId) {
		
		if(defaultAddressId > 0) {
			repo.setDefaultAddress(defaultAddressId);
		}
		repo.setNoneDefaultForOthers(defaultAddressId, customerId);
	}
	
}