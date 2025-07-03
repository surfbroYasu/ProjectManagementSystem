package com.example.projectmanagement.users.datastructure.forms;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SimpleUserRegistForm {

	@NotBlank
    private String email;
   
	@NotBlank
	@Size(min=8, message = "{error.pass.length}")
    private String password;
    
    @NotBlank
    @Size(min=8, message = "{error.pass.length}")
    private String passwordConf;
    
    @AssertTrue(message = "{error.agree.terms}")
    private Boolean agreedToTerms;
    
    public String getPasswordHash() {return this.password;}
    
    
    @AssertTrue(message = "{error.password.confirmation}")
    public boolean isPasswordConfirmed() {
        return password !=null && password.equals(passwordConf);
    }

}
