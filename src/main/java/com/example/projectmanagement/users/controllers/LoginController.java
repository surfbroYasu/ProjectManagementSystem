package com.example.projectmanagement.users.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectmanagement.users.datastructure.forms.LoginForm;

@Controller
public class LoginController {

	@ModelAttribute("loginForm")
	public LoginForm setLoginForm() {
		return new LoginForm();
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
	    if (error != null) {
	        model.addAttribute("loginError", true);
	    }
	    model.addAttribute("title", "title.login");
	    return "contents/users/login";
	}

}
