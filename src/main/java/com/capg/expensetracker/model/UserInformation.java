package com.capg.expensetracker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "User_Information")
@Entity

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {

	private String name;

	private String password;

	private long mobileNumber;

	@Id
	private String email;

	private long balance;
	


	

	

	
	
}
