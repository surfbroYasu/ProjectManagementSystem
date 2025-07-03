package com.example.projectmanagement.users.datastructure.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginForm {
	@NotBlank
    private String email;
   
	@NotBlank
	@Size(min=8)
    private String password;
	
}
