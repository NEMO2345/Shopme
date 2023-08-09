package com.shopme.customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.CountryRepository;

import jakarta.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
@Service
@Transactional
public class CustomerService {

	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public List<Country> listAllCountries(){
		return	countryRepo.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(String email) {
	Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
	
	public void registerCustomer(Customer customer) {
		encodePassword(customer);
		customer.setEnabled(false);
		customer.setCreateTime(new Date());
		
		String randomCode = RandomStringUtils.randomAlphanumeric(64);
		customer.setVerificationCode(randomCode);
		
		System.out.println("Verification code: " + randomCode);
	}

	private void encodePassword(Customer customer) {
		// TODO Auto-generated method stub
			String encodePassword =	passwordEncoder.encode(customer.getPassword());
			customer.setPassword(encodePassword);
	}
	
	public boolean verify(String verifycationCode) {
		 Customer customer = customerRepo.findByVerificationCode(verifycationCode);
		
		 if(customer == null || customer.isEnabled()) {
			 return false;
		 }else {
			 customerRepo.enable(customer.getId());
			 return true;
		 }
		 
	}
	
	public void updateAuthentication(Customer customer,AuthenticationType type) {
		if(!customer.getAuthenticationType().equals(type)) {
			customerRepo.updateAuthenticationType(customer.getId(), type);
		}
	}
}
