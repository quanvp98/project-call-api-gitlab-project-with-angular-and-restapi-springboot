package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping(value = {"/users"})
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
	}
	
	@PutMapping(value = {"/users"})
	public ResponseEntity<User> editUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user));
	}
	
	@DeleteMapping(value = {"/users/{id}"})
	public ResponseEntity deleteUser(@PathVariable Integer id) {
		userService.deleteUserById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping(value = {"/users"})
	public ResponseEntity<User> addUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(user));
	}
	
	@GetMapping(value = {"/users/{id}"})
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
	}
	
	@PostMapping(value = {"/users/comments"})
	public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.addComment(comment));
	}
	
	@GetMapping(value = {"/projects/{idProject}/comments"})
	public ResponseEntity<List<Comment>> getCommentsByIdProject(@PathVariable Integer idProject) {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.getCommentsByIdProject(idProject));
	}
	
	@GetMapping(value = {"/projects/newcomments"})
	public ResponseEntity<List<String>> getIdProjectsHaveNewComment() {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.getIdProjectHaveNewComment());
	} 
	
}
