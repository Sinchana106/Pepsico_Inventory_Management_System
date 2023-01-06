
package com.cts.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.authentication.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Query
	public Optional<User> findByNameAndPassword(String name,String password);
	
}

