package com.example.projectmanagement.application.context;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ApplicationContextService {
	
	
	/**
	 * 	 [[ モデル一覧 ]]<br/>
	 * 
	 * title： ページタイトル
	 * 
	 * @param model
	 * @param titleProp
	 */
	public void setPageTitle(Model model, String titleProp) {
		model.addAttribute("title", titleProp);
	}

}
