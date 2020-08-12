package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String userLogin(Model model, @ModelAttribute(name = "account") Account account) {
		String check = userService.getAccountByUserNameAndPassword(account);
		if (check.equals("ERROR")) {
			model.addAttribute("errorMessage", "Sai thông tin tài khoản hoặc mật khẩu, Vui lòng thử lại !!!");
			return "login";
		}
		if (check.equals("ADMIN")) return "dashboard";
		return "welcomeMember";
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String getFormLogin(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "login";
	}
	
	@RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
	public String getDashboard(Model model) {
		List<User> users = userService.getAllUser();
		model.addAttribute("users", users);
		return "dashboard";
	}
	
	@RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
	public String getFormEdit(Model model, @PathVariable int id) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "editUser";
	}
	
	@RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
	public String editUser(Model model, @ModelAttribute(name = "user") User user) {
		userService.updateUser(user);
		return "redirect:/user/dashboard";
	}
	
	@RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
	public String deleteUser(Model model, @PathVariable int id) {
		userService.deleteUserById(id);
		return "redirect:/user/dashboard";
	}
	
	@RequestMapping(value = {"/add"}, method = RequestMethod.GET)
	public String getFormAdd(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "addUser";
	}
	
	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String addUser(Model model, @ModelAttribute(name = "user") User user) {
		userService.addUser(user);
		return "redirect:/user/dashboard";
	}
}
