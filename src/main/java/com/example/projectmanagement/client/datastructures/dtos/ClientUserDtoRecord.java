package com.example.projectmanagement.client.datastructures.dtos;

/**
 * 
 *  Integer id,<br/> Integer clientId,<br/>String position,<br/>String note,<br/>String email,<br/>
 * 	String phone,<br/>String firstName,<br/>String middleName,<br/>String lastName
 * 	 
 * @author yasufumimisono
 *
 */
public record ClientUserDtoRecord(
		
		 Integer id,
		 Integer clientId,
		 String position,
		 String note,
		 String email,
		 String phone,
		 String firstName,
		 String middleName,
		 String lastName
		
		) {

}
