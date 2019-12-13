package com.nest.appuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nest.appuser.entity.User;
import com.nest.appuser.repository.RoleRepository;
import com.nest.appuser.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/userForm")
	public String userForm(Model model) {
		
		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUserss());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("listTab","active");
		return "user-form/user-view";
	}
}
