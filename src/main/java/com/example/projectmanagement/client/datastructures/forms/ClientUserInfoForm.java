package com.example.projectmanagement.client.datastructures.forms;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class ClientUserInfoForm {

	private Integer id;

	@NotNull
	private Integer clientId;
	
	private String position;
	private String note;
	private String email;
	private String phone;

	private String firstName;
	
	private String middleName;
	
	private String lastName;
}
