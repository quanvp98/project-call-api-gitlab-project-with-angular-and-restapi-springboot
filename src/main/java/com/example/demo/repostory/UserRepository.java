package com.example.demo.repostory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Account;
import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByAccount(Account account);
}
