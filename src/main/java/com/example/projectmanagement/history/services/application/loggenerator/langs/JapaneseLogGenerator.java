package com.example.projectmanagement.history.services.application.loggenerator.langs;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.history.services.application.loggenerator.HistoryLogGenerator;

@Component("jaLog")
public class JapaneseLogGenerator implements HistoryLogGenerator {

	@Override
	public String createLog(String targetObject) {
		return "「" + targetObject + "」が作成されました。";
	}

	@Override
	public String addObjectLog(String targetObject) {
		return "「" + targetObject + "」が追加されました。";
	}

	@Override
	public String editObjectLog(String targetObject) {
		return "「" + targetObject + "」が編集されました。";
	}

	@Override
	public String deleteObjectLog(String targetObject) {
		return "「" + targetObject + "」が削除されました。";
	}

}
