package com.example.projectmanagement.index.root;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
//	private static final String TEMPLATE_ROOT="contents/projects/";

	@GetMapping("")
	public String renderProjectIndex(Model model) {
		model.addAttribute("title", "title.todo");
		return "todo";
	}
}
