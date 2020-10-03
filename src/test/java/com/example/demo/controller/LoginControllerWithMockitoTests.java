package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.Account;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.User;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class LoginControllerWithMockitoTests {
	
	@Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;
	
	@Captor
    private ArgumentCaptor<String> passwordArgumentCaptor;
	
	@Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> userArgumentCaptor;
	
	@Captor
    private ArgumentCaptor<UserDetails> userDetailArgumentCaptor;
	
	@InjectMocks
	private LoginController loginController;
	
	@Mock
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private UserService userService;
	
	@ParameterizedTest
	@CsvFileSource(resources = "/data-test/logincontroller-test-login.csv", numLinesToSkip = 1)
	public void testUserLogin(String userName, String password) {
		JwtRequest jwtRequest = new JwtRequest(userName, password);
		try {
			loginController.login(jwtRequest);
			verify(jwtTokenUtil).generateToken(userDetailArgumentCaptor.capture());
			verify(authenticationManager).authenticate(userArgumentCaptor.capture());
			verify(userService).getInforUserResponse(usernameArgumentCaptor.capture());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// check JwtUserDetailsService.loadUserByUsername(username) has been invoked
		verify(jwtUserDetailsService).loadUserByUsername(jwtRequest.getUsername());
		
		// captures the argument which was passed in to save method.
		verify(jwtUserDetailsService).loadUserByUsername(usernameArgumentCaptor.capture());
		
		// verify argument
		assertEquals(userName, usernameArgumentCaptor.getValue());
	}

}
