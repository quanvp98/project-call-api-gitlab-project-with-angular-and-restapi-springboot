package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
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
import org.springframework.http.ResponseEntity;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserControllerWithMockitoTests {
	
	@Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;
	
	@Captor
    private ArgumentCaptor<User> userArgumentCaptor;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;

	@BeforeEach
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		Account account1 = new Account("quannguyen", "quan@gmail.com", "123", "ADMIN");
		User user1 = new User(1, "Nguyen Hong Quan", "male", "Ha noi", "0382739666", account1);
		Account account2 = new Account("tanluu", "tan@gmail.com", "345", "MEMBER");
		User user2 = new User(2, "Luu Quang Tan", "male", "Thanh hoa", "0382565666", account2);
		
		// define userService.getAll method
		Mockito.lenient().when(userService.getAllUser()).thenReturn(Arrays.asList(user1, user2));
		
	}
	
	@Test
	public void testAddUser() {
		Account account = new Account("quannguyen", "quan@gmail.com", "123", "ADMIN");
		User user = new User(1, "Nguyen Hong Quan", "male", "Ha noi", "0382739666", account);
		
		userController.addUser(user);
		
		// check userService.addUser has been invoked
		verify(userService).addUser(user);

		// captures the argument which was passed in to save method.
		verify(userService).addUser(userArgumentCaptor.capture());

		// verify argument
		assertEquals("Nguyen Hong Quan", userArgumentCaptor.getValue().getFullName());
	}
	
	@Test
	public void testGetAllUser() {
		userController.getAllUser();
		
		// check userService.getAll has been invoked
		verify(userService).getAllUser();

		assertEquals(2, ((ResponseEntity<List<User>>) userController.getAllUser()).getBody().size());
	}
}
