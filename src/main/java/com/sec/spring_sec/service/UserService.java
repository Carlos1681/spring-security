package com.sec.spring_sec.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sec.spring_sec.model.User;
import com.sec.spring_sec.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User register(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
