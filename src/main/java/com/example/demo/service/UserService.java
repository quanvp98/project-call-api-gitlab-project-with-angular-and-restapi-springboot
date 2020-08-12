package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repostory.AccountRepository;
import com.example.demo.repostory.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	public String getAccountByUserNameAndPassword(Account account) {
		Account accountFromDb = accountRepository.findByUserNameAndPassword(account.getUserName(), account.getPassword());
		if (accountFromDb == null) {
			return "ERROR";
		}
		String role = accountFromDb.getRole();
		if (role.equals("ADMIN")) return "ADMIN";
		return "MEMBER";
	}
	
	public User getUserByAccount(Account account) {
		User user = userRepository.findByAccount(account);
		return user;
	}
	
	public User getUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public void addUser(User user) {
		accountRepository.save(user.getAccount());
		userRepository.save(user);
	}
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
	public void deleteUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		userRepository.deleteById(id);
		accountRepository.deleteById(user.get().getAccount().getUserName());
	}
	
}
