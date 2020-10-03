package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Account;
import com.example.demo.service.UserService;

@CrossOrigin
@Controller
public class SignupController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/signup")
	public ResponseEntity<Boolean> signUp(@RequestBody Account account) {
		return ResponseEntity.ok(userService.addAccount(account));
	}
}
