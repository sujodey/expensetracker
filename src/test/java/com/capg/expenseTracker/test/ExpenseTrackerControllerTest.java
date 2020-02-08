package com.capg.expenseTracker.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.capg.expensetracker.dao.ExpenseTrackerDao;
import com.capg.expensetracker.dao.ToDoDao;
import com.capg.expensetracker.dao.UserTransactionDao;
import com.capg.expensetracker.model.UserInformation;
import com.capg.expensetracker.model.UserToDoInformation;
import com.capg.expensetracker.model.UserTransaction;
import com.capg.expensetracker.service.ExpenseTrackerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpenseTrackerControllerTest {
	
	
	UserInformation email=new UserInformation("Agni", "agni@gmail", 8172038170l, "agni@gmail.com", 0);
	UserToDoInformation todoObject=new UserToDoInformation(12, email, "agni", "credit", "credit", 0l, Date.valueOf("2018-09-09"), "on");
	UserTransaction transObject=new UserTransaction(12, email, "agni", "credit", "credit", 0l, Date.valueOf("2018-09-09"));
	UserInformation userObject=new UserInformation("Agni", "agni@03.com", 8172038170l, "agni@gmail.com", 0);
	Optional<UserInformation> optional=Optional.of(userObject);
	@Autowired
	private ExpenseTrackerService service;
	
	@MockBean
	private UserTransactionDao userTransactionRepository;
	@MockBean
	private ExpenseTrackerDao userInfoRepository;
	
	@MockBean
	ToDoDao toDoRepository;
	
	@Test
	public void getAlltransactions() {
		
		when(userTransactionRepository.findByTransactions("abc@gmail.com")).thenReturn(Stream.of
				(new UserTransaction(12, email, "agni", "credit", "credit", 0l, Date.valueOf("2018-09-09"))).collect(Collectors.toList()));
		
		try {
			assertEquals(1, service.getAllTransactions().size());
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	public void createAccount() {
		
		
		
		when(userInfoRepository.save(userObject)).thenReturn(userObject);
		
		try {
			assertEquals(1, service.createAccount(userObject));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	public void findByEmail() {
		
		
		
		when(userInfoRepository.findById("abc@gmail.com")).thenReturn(optional);
		
		try {
			assertEquals(optional, service.userLogin("abc@gmail.com"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void updateTransaction() {
		
		
		
		when(userTransactionRepository.save(transObject)).thenReturn(transObject);
		
		try {
			assertEquals(transObject, service.updateTransaction(transObject));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void updateToDo() {
		
		
		
		when(toDoRepository.save(todoObject)).thenReturn(todoObject);
		
		try {
			assertEquals(todoObject, service.updateToDO(todoObject));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void getAllToDo() {
		
		
		
		when(toDoRepository.findByToDo("abc@gmail.com")).thenReturn(Stream.of
				(new UserToDoInformation(12, email, "agni", "credit", "credit", 0l, Date.valueOf("2018-09-09"), "on")).collect(Collectors.toList()));
		
		try {
			assertEquals(1, service.getAllToDo().size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void getCount() {
		
		
		
		when(toDoRepository.getCount("abc@gmail.com")).thenReturn(2);
		
		try {
			assertEquals(2, service.getReminderToDo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	
	
	

}
