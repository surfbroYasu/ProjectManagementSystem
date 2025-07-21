package com.example.projectmanagement.client.datastructures.dtos;

/**
 * Integer id,<br/> 
 * String name,<br/> 
 * 	String repName,<br/> 
 * 	String phone,<br/> 
 * 	String note<br/> 
 * @author yasufumimisono
 *
 */
public record ClientDtoRecord(
		Integer id,
		String name,
		String repName,
		String phone,
		String note) {

}
