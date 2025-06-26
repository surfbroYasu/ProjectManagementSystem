package com.example.projectmanagement.history.services.application.loggenerator;

public interface HistoryLogGenerator {
	
	String createLog(String targetObject);
	
	String addObjectLog(String targetObject);
	String editObjectLog(String targetObject);
	String deleteObjectLog(String targetObject);
	
}
