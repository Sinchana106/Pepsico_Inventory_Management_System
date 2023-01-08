
package com.cts.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.cts.authentication.model.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, String> {
	@Query
	public Optional<User> findByUsernameAndPassword(String username, String password);

	@Query
	public MyUser findByUsername(String username);
}
