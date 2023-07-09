package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useRepresentation;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdminRole = entityManager.find(Role.class,1);
		User userPhamLy  = new User("phamly@2202","nam2002","Ly","ABC");
		userPhamLy.addRole(roleAdminRole);
		
		User saveUser = repo.save(userPhamLy);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateNewUserWithTwoRole() {
		User userRavi  = new User("Ravi@2202","ravi2002","Nam","Kumar");
		Role roleEditor = new  Role(2);
		Role roleAssistant = new  Role(4);

		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User savedUser = repo.save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.print(user));

	}
	@Test
	public void testGetUserById() {
	User userPhamLy	= repo.findById(1).get();
	System.out.println(userPhamLy);
	assertThat(userPhamLy).isNotNull();
	}
	@Test
	public void testUpdateUserDetails() {
		User userPhamLy	= repo.findById(1).get();
		userPhamLy.setEnabled(true);
		userPhamLy.setEmail("EmailPhamLyUpdate@gmail.com");
		
		repo.save(userPhamLy);
	}
	@Test
	public void testUpdateUserRoles() {
		User userRavi	= repo.findById(1).get();
		Role roleEditor = new  Role(2);
		Role roleSalepersion= new  Role(1);
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalepersion);
		
		repo.save(userRavi);
	}
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);	
	}
	@Test
	public void testGetUserByEmail() {
		String email = "Ravi@2202";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
}
