package com.example.projectmanagement.client.datastructures.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientForm {

	private Integer id;
	
	@NotBlank
	private String name;
	
	private String repName;
	
	
	private String phone;
	private String note;
	
}
