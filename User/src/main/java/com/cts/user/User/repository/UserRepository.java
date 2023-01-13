package com.cts.user.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.user.User.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	public User findByUserName(String userName);
	public User findByContact(String contact);
	public User findByUserNameAndUserPassword(String userName,String userPassword);
}