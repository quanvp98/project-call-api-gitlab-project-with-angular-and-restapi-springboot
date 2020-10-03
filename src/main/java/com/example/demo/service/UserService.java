package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.exceptionhandle.ArgumentNullException;
import com.example.demo.exceptionhandle.ListEmptyException;
import com.example.demo.exceptionhandle.LoginFailException;
import com.example.demo.exceptionhandle.ObjectIsExistException;
import com.example.demo.exceptionhandle.ObjectNullException;
import com.example.demo.exceptionhandle.SaveErrorException;
import com.example.demo.exceptionhandle.UpdateErrorException;
import com.example.demo.model.Account;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.repostory.AccountRepository;
import com.example.demo.repostory.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Value("${role.user}")
	private String role;
	
	public Account getAccountByUserNameAndPassword(Account account) {
		if (account.getUserName() == null || account.getPassword() == null) {
			throw new ObjectNullException("Account null");
		}
		Account accountFromDb = accountRepository.findByUserNameAndPassword(account.getUserName(), account.getPassword());
		if (accountFromDb == null) {
			throw new LoginFailException();
		}
		return accountFromDb;
	}
	
	public User getUserByAccount(Account account) {
		User user = userRepository.findByAccount(account);
		if (user == null) {
			throw new ObjectNullException("User has username : " + account.getUserName() + " not exist");
		}
		return user;
	}
	
	public User getUserById(Integer id) {
		if (id == null) {
			throw new ArgumentNullException("ID = null");
		}
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new ObjectNullException("User has Id : " + String.valueOf(id) + " not exist"); 
		}
		return user.get();
	}
	
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new ListEmptyException();
		}
		return users;
	}
	
	public User addUser(User user) {
		accountRepository.save(user.getAccount());
		User userFromDb = userRepository.save(user);
		if (userFromDb == null) {
			throw new SaveErrorException();
		}
		return userFromDb;
	}
	
	public User updateUser(User user) {
		User userFromDb = userRepository.save(user);
		if (userFromDb == null) {
			throw new UpdateErrorException();
		}
		return userFromDb;
	}
	
	public Account getAccountByUserName(String userName) {
		if (userName == null) {
			throw new ArgumentNullException("UserName = null");
		}
		
		Account account = accountRepository.findById(userName).get();
		if (account == null) {
			throw new ObjectNullException("Account have Username : " + userName + " not exist");
		}
		return account;
	}
	
	public Boolean deleteUserById(Integer id) {
		if (id == null) {
			throw new ArgumentNullException("ID = null");
		}
		
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new ObjectNullException("User has Id : " + String.valueOf(id) + " not exist"); 
		}
		userRepository.deleteById(id);
		
		String username = user.get().getAccount().getUserName();
		Optional<Account> account = accountRepository.findById(username);
		if (!account.isPresent()) {
			throw new ObjectNullException("User has username : " + username + " not exist");
		}
		accountRepository.deleteById(username);
		
		return true;
	}
	
	public JwtResponse getInforUserResponse(String userName) {
		Account account = accountRepository.getOne(userName);
		User userFromDb = userRepository.findByAccount(account);
		User user;
		if (userFromDb == null) {
			user = null;
		}
		else {
			user = new User(userFromDb.getId(), userFromDb.getFullName(), userFromDb.getGender(), userFromDb.getAddress(),
			userFromDb.getTel(), null);
		}
		return new JwtResponse(null, user, account.getRole());
	}
	
	public Boolean addAccount(Account account) {
		if (!checkAccountExist(account)) throw new ObjectIsExistException("Username is exist", "Change Username");
		account.setRole(role);
		accountRepository.save(account);
		return true;
	}
	
	private Boolean checkAccountExist(Account account) {
		Optional<Account> accountFromDb = accountRepository.findById(account.getUserName());
		if (accountFromDb.isPresent()) return false;
		return true;
	}
}
