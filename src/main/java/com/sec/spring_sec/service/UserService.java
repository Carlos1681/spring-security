package com.sec.spring_sec.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sec.spring_sec.model.User;
import com.sec.spring_sec.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	private JwtService jwtService;

	AuthenticationManager authenticationManager;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public UserService(UserRepository userRepository, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public User register(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public String verify(User user) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authentication.isAuthenticated())
			return jwtService.generateToken(user.getUsername());

		return "Falha na autenticação";

	}

}
