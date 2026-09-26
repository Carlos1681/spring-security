package com.sec.spring_sec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sec.spring_sec.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
