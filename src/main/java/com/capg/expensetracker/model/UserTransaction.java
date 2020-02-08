package com.capg.expensetracker.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "User_Transaction")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserTransaction {

	@SequenceGenerator(name = "tab", initialValue = 1000, sequenceName = "mySeq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tab")
	@Id
	private int transactionId;

	@ManyToOne
	@JoinColumn(name = "email", referencedColumnName = "email")
	private UserInformation email;

	
	private String name;
	private String category;
	private String transactionType;
	private long amount;
	private Date dateOfTransaction;

	public UserTransaction() {

	}

	

	public UserTransaction(String category, String transactionType, long amount) {
		super();
		this.category = category;
		this.transactionType = transactionType;
		this.amount = amount;
	}







	
	
	
	
	

	
}
