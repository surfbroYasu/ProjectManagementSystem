package com.example.projectmanagement.modules.database;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/database")
public class DatabaseSettingController {

	@GetMapping("/hello")
	public String testRendering() {
		System.out.println("here!!!!!!!!!!!!!!!!!!!!!!!");
		return "todo";
	}
}
