
package com.cts.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.authentication.model.User;
import com.cts.authentication.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	@Override
	public User registerUser(User user) {
		String userid=randomGenerator();
		user.setUserid(userid);
		user.setUsertype("Customer");
		return repo.save(user);
	}

	@Override
	public boolean loginUser(String username, String password) {
		Optional<User> user = repo.findByNameAndPassword(username, password);
		if(user.isPresent()) {
			return true;
		}
		return false;
	}
	
	public String randomGenerator() {
		int n = 100 + (int)(Math.random() * ((999 - 100) + 1));
		String uid="R-" + n;
		while(true) {
			Optional<User> id = repo.findById(uid);
			if(id.isPresent()) {
				 n = 100 + (int)(Math.random() * ((999 - 100) + 1));
				 uid="R-" + n;
			}
			else {
				break;
			}
		}
		return uid;
	
	}
@Override
	public boolean validateUserName(String name) {
		for(int i=0;i<name.length();i++)
			if(Character.isDigit(name.charAt(i))) {
				System.out.println(name.charAt(i));
				return false;
			}
		return true;
	}
}
