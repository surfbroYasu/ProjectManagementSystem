package com.example.projectmanagement.history.services.application.loggenerator.langs;

import com.example.projectmanagement.history.services.application.loggenerator.HistoryLogGenerator;

public class EnglishLogGenerator implements HistoryLogGenerator {

	@Override
	public String createLog(String targetObject) {
		return "[" + targetObject + "] has been created.";
	}

	@Override
	public String addObjectLog(String targetObject) {
		return "[" + targetObject + "] has been added.";
	}

	@Override
	public String editObjectLog(String targetObject) {
		return "[" + targetObject + "] has been eddited.";
	}

	@Override
	public String deleteObjectLog(String targetObject) {
		return "[" + targetObject + "] has been deleted.";
	}


}
