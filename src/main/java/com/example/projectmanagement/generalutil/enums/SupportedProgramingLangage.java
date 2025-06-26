package com.example.projectmanagement.generalutil.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SupportedProgramingLangage {
	JAVA("java")
//		, PYTHON("python")
//		, TYPESCRIPT("typescript")
	//**** ADD MORE ****
	;

	private final String code;

	SupportedProgramingLangage(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static SupportedProgramingLangage fromCode(String code) {
		for (SupportedProgramingLangage lang : values()) {
			if (lang.code.equalsIgnoreCase(code)) {
				return lang;
			}
		}
		throw new IllegalArgumentException("Unsupported language: " + code);
	}
	
	public static List<String> getStringLanguageCodes() {
	    return Arrays.stream(SupportedProgramingLangage.values())
	        .map(SupportedProgramingLangage::getCode)
	        .collect(Collectors.toList());
	}

}
