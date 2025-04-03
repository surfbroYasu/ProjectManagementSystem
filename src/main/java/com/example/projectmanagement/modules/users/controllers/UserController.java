package com.example.projectmanagement.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.modules.users.domain.User;
import com.example.projectmanagement.modules.users.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String register() {
        return "contents/users/register"; 
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user, Model model) {
	    userService.registerUser(user);
	    return "redirect:/login";
	}

}
