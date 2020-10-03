package com.example.demo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repostory.AccountRepository;
import com.example.demo.repostory.UserRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceWithMockitoTests {
	
	@Captor
    private ArgumentCaptor<User> userArgumentCaptor;
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		Account account1 = new Account("quannguyen", "quan@gmail.com", "123", "ADMIN");
		User user1 = new User(1, "Nguyen Hong Quan", "male", "Ha noi", "0382739666", account1);
		Account account2 = new Account("tanluu", "tan@gmail.com", "345", "MEMBER");
		User user2 = new User(2, "Luu Quang Tan", "male", "Thanh hoa", "0382565666", account2);
		
		// Save 2 User After return user have id
		Mockito.lenient().when(userRepository.save(user1)).thenReturn(user1);
		
		// Find User By Id
		Mockito.lenient().when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		
		// Find All User
		Mockito.lenient().when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
		
		// Find Account by Id
		Mockito.lenient().when(accountRepository.findById("quannguyen")).thenReturn(Optional.of(account1));
		
		// doThrow(StaleObjectStateException.class).when(userRepository).save(user1);
				
	}
	
//	@BeforeEach
//	@CsvFileSource(resources = "/data-test/service-test-data.csv", numLinesToSkip = 1)
//	public void setUp(String id, String fullName, String gender, 
//			String address, String tel, String userName, String password, String role) throws Exception {
//		MockitoAnnotations.initMocks(this);
//		
//		List<User> users = new ArrayList<>();
//		
//		Account account = new Account(userName, password, role);
//		User user = new User(fullName, gender, address, tel, account);
//		user.setId(Integer.valueOf(id));
//		
//		users.add(user);
//		
//		// Save 2 User After return user have id
//		Mockito.lenient().when(userRepository.save(users.get(1))).thenReturn(users.get(1));
//		
//		// Find User By Id
//		Mockito.lenient().when(userRepository.findById(1)).thenReturn(Optional.of(users.get(1)));
//		
//		// Find All User
//		Mockito.lenient().when(userRepository.findAll()).thenReturn(users);
//		
//		// Find Account by Id
//		Mockito.lenient().when(accountRepository.findById("quannguyen")).thenReturn(Optional.of(users.get(1).getAccount()));
//		
//		// doThrow(StaleObjectStateException.class).when(userRepository).save(user1);
//		
//	}
	
	@Test
	public void TestGetAll() {
		List<User> users = (List<User>) userService.getAllUser();
		assertNotNull(users);
		assertEquals(2, users.size());
	}
	
	@Test
	public void TestFindUserById() {
		User user = (User) userService.getUserById(1);
		assertNotNull(user);
		assertEquals("Nguyen Hong Quan", user.getFullName());
	}
	
	@Test
	public void testUpdateUser() {
		User user = userRepository.findById(1).get();
		user.setFullName("Le Hong Quang");
		userService.updateUser(user);		
		
		// captures the argument which was passed in to save method.
		verify(userRepository).save(userArgumentCaptor.capture());
		
		// make sure fullName is edited
        assertEquals(userArgumentCaptor.getValue().getFullName(), "Le Hong Quang");
	}
	
	@Test
	public void testDeleteUser() {
		userService.deleteUserById(1);
		
		// verify that the userRepository.getById had invoked 
		verify(userRepository).findById(1);
		
		// verify that the userRepository.deleteById had invoked 
		verify(userRepository).deleteById(1);
		
		// verify that the accountRepository.findById had invoked 
		verify(accountRepository).findById("quannguyen");
		
		// verify that the accountRepository.deleteById had invoked 
		verify(accountRepository).deleteById("quannguyen");
		
		// verify had invoked 1 time
		verify(userRepository, times(1)).findById(1);
		verify(userRepository, times(1)).deleteById(1);
		verify(accountRepository, times(1)).deleteById("quannguyen");
		
	}
	
}
