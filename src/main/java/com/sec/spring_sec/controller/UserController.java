package com.sec.spring_sec.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sec.spring_sec.model.User;
import com.sec.spring_sec.service.UserService;

@RestController
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}

	@PostMapping("/login")
	public String login(@RequestBody User user) {
		return userService.verify(user);
	}
}
