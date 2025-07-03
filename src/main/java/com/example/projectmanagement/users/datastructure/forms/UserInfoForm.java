package com.example.projectmanagement.users.datastructure.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserInfoForm {
	
	@NotBlank
	private String email;

	@NotBlank
	private String firstName;
	
	private String middleName;
	
	@NotBlank
	private String lastName;	
	
}
