package com.example.projectmanagement.modules.database;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/database")
public class DatabaseSettingController {
	
	private static final String TEMPLATE_ROOT="pages/";

	@GetMapping("/hello")
	public String testRendering(Model model) {
		model.addAttribute("title", "title.db.top");
		return "test";
	}
}
