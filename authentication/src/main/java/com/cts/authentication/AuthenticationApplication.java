package com.cts.authentication;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cts.authentication.model.User;
import com.cts.authentication.repository.UserRepository;

@SpringBootApplication
public class AuthenticationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
		
	}

	@Autowired
	private UserRepository repo;
	@Override
	public void run(String... args) throws Exception {
		repo.save(new User("R-111", "Sinchana", "sinchanashettyc@gmail.com", "9741768148",  new Date(2000, 05, 22), "Admin","India@123"));
	}

}
