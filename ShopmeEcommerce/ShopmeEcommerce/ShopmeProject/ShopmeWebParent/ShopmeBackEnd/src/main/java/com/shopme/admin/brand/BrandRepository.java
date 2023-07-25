package com.shopme.admin.brand;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopme.common.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

	public Long countById(Integer id);

	public Brand findByName(String name);
	
	@Query("SELECT c FROM Brand c WHERE c.name LIKE %?1%")
	public Page<Brand> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT NEW Brand(c.id, c.name) FROM Brand c ORDER BY c.name ASC")
	public List<Brand> findAll();
}
