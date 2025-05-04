package com.example.projectmanagement.users.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagement.users.domain.User;
import com.example.projectmanagement.users.domain.UserRoleEnum;
import com.example.projectmanagement.users.forms.SimpleUserRegistForm;
import com.example.projectmanagement.users.security.CustomUserDetails;
import com.example.projectmanagement.users.services.UserService;

/*
 * 次のURLで始まるエンドポイントはセキュリティー保護の対象外となる
 * 　　
 * 　　　"/user/signup/**"
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private static final String PERSONAL_SIGNUP = "contents/users/personal/signup";

	@Autowired
	private UserService userService;

	@ModelAttribute("signupForm")
	public SimpleUserRegistForm setSingupForm() {
		return new SimpleUserRegistForm();
	}

	@GetMapping("/signup/personal")
	public String register(Model model) {
		model.addAttribute("title", "title.signup");
		return PERSONAL_SIGNUP;
	}

	@PostMapping("/signup/personal/register")
	public String register(@Validated @ModelAttribute("signupForm") SimpleUserRegistForm form, BindingResult result,
			Model model) {

		if (userService.existsByEmail(form.getEmail())){
			result.rejectValue("email", "error.email.alreadyexist");
		}
		if (result.hasErrors()) {
			model.addAttribute("title", "title.signup");
			return PERSONAL_SIGNUP;
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);

		user.setIsActive(true);
		user.setRole(UserRoleEnum.ROLE_PERSONAL_USER.toString());

		userService.registerUser(user);
		
		return "redirect:/login";
	}

	@GetMapping("/setup")
	public String renderUserSetupPage(@AuthenticationPrincipal CustomUserDetails loginUser, Model model) {
		model.addAttribute("userInfo", loginUser);
		return "contents/users/setup";
	}

	@GetMapping("/terms")
	public String renderTerms() {
		return "contents/users/terms";
	}

}
