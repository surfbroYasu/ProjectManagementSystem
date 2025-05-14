package com.example.projectmanagement.users.forms;

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
