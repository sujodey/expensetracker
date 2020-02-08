package com.capg.expensetracker.controller;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.expensetracker.dao.UserTransactionDao;
import com.capg.expensetracker.model.UserInformation;
import com.capg.expensetracker.model.UserToDoInformation;
import com.capg.expensetracker.model.UserTransaction;
import com.capg.expensetracker.security.JwtTokenProvider;
import com.capg.expensetracker.service.ExpenseTrackerService;


@RestController
@RequestMapping("/expensetracker/functions")
public class ExpenseTrackerController {

	@Autowired
	ExpenseTrackerService service;

	@Autowired
	UserTransactionDao repo;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	PasswordEncoder encodedPassword;

	Set<Integer> tidList = new LinkedHashSet<>();
	
	public static long userCurrentBalance=0;

	
	

	/**
	 * method name: updateTransaction return type:UserTransaction object argument:
	 * transObject
	 * 
	 * @param transObject
	 * 
	 *                    author: Capgemini date: 9-Jan-2020 description: This
	 *                    method is used to fetch user's transaction details
	 * @return
	 */
	@PostMapping("/transaction")
	public String updateTransaction(@RequestBody UserTransaction transObject) {

		UserTransaction trans = null;
		
		
		

		try {
			

			long balance=0;
			long currBalance=0;
			
			trans = service.updateTransaction(transObject);
			
			currBalance=service.getCurrentBalance(transObject.getEmail().getEmail());

			
			if(trans.getTransactionType().equalsIgnoreCase("income"))
				{
					balance=currBalance+trans.getAmount();
				}
			
			
			if(trans.getTransactionType().equalsIgnoreCase("expense"))
				{
					balance=currBalance-trans.getAmount();
				}
			
			
			
			  UserInformation userInfo=new  UserInformation(ExpenseTrackerAuthenticationcontroller.name, ExpenseTrackerAuthenticationcontroller.userPassword,
					  ExpenseTrackerAuthenticationcontroller.mobileNumber, ExpenseTrackerAuthenticationcontroller.userEmail, balance);
			  
			 
			
			return "Transaction is successful with Transacation Id: "+trans.getTransactionId();
			
			
			
			
		} catch (Exception e) {
			
			return ""+e.getMessage();

		}



	}

	/**
	 * method name: getAllTransactions return type: UserTransactions argument:
	 * String
	 * 
	 * @param email
	 * 
	 *              author: Capgemini date: 9-Jan-2020 description: This method is
	 *              used to fetch all transaction from the user_transaction table
	 * @return
	 */
	@GetMapping("/printtransaction")
	public String getAllTransactions() {

		List<UserTransaction> getTrans = null;
		Map<StringBuilder, String> transMap=new LinkedHashMap<>();

		try {
			
			
			
			getTrans = service.getAllTransactions();
			
			
			
			for (UserTransaction transaction : getTrans) {
				
				StringBuilder userTId=new StringBuilder("You transaction id is : ");
				StringBuilder userCategory=new StringBuilder("You transaction category is : ");
				StringBuilder ttype=new StringBuilder("You transaction type is : ");
				StringBuilder dot=new StringBuilder("You date of transaction is : ");
				StringBuilder userAmount=new StringBuilder("You transaction amount is : ");
				
				transMap.put(userTId, String.valueOf(transaction.getTransactionId()));
				transMap.put(userCategory, transaction.getCategory());
				transMap.put(ttype, transaction.getTransactionType());
				transMap.put(dot, String.valueOf(transaction.getDateOfTransaction()));
				transMap.put(userAmount, String.valueOf(transaction.getAmount()));
				
				
			}
			
			
		} catch (Exception e) {
			
			return ""+e.getMessage();

		}

		return ""+transMap;

	}

	/**
	 * method name: updateToDo return type:UserToDoInformation object argument:
	 * todoObject
	 * 
	 * @param todoObject
	 * 
	 *                   author: Capgemini date: 9-Jan-2020 description: This method
	 *                   is used to fetch user's to do details
	 * @return
	 */
	@PostMapping("/todo")
	public String updateToDo(@RequestBody UserToDoInformation todoObject) {

		UserToDoInformation insertToDo = null;
		try {
			insertToDo = service.updateToDO(todoObject);
		} catch (Exception e) {
			
			return ""+e.getMessage();

		}

		return "To do list successfully created with transaction id: "+insertToDo.getTransactionId();
	}

	/**
	 * method name: getAllToDO return type: List<UserToDoInformation> argument:
	 * String
	 * 
	 * @param email
	 * 
	 *              author: Capgemini date: 9-Jan-2020 description: This method is
	 *              used to fetch all to do information from the
	 *              user_to_do_transaction table
	 * @return
	 */
	@GetMapping("/printtodo")
	public String getAllToDo() {

		List<UserToDoInformation> list = null;
		try {
			list = service.getAllToDo();

			Iterator<UserToDoInformation> iterator = list.iterator();

			while (iterator.hasNext()) {
				tidList.add(iterator.next().getTransactionId());
			}
		} catch (Exception e) {
			
			return ""+e.getMessage();

		}
		
		Map<StringBuffer, String> userToDoMap=new LinkedHashMap<>();
		
		for (UserToDoInformation info : list) {
			
			StringBuffer tid=new StringBuffer("Your ToDo Id Is : ");
			StringBuffer category=new StringBuffer("Your Selected Category Is :");
			StringBuffer ttype=new StringBuffer("Your Selected ToDo Type Is : ");
			StringBuffer amount=new StringBuffer("Your ToDo Amount Is : ");
			
			
			userToDoMap.put(tid, String.valueOf(info.getTransactionId()));
			userToDoMap.put(category, String.valueOf(info.getCategory()));
			userToDoMap.put(ttype, String.valueOf(info.getTransactionType()));
			userToDoMap.put(amount, String.valueOf(info.getAmount()));
			
			
		}

		return ""+userToDoMap;

	}

	/**
	 * method name: deleteToDo return type: integer argument: transactionId
	 * 
	 * @param transactionId
	 * 
	 *                      author: Capgemini date: 9-Jan-2020 description: This
	 *                      method is used to delete a given to do from the
	 *                      user_to_do_transaction table
	 * @return
	 * @throws Exception 
	 */
	
	@DeleteMapping("/deletetodo/{transactionId}")
	public String deleteToDo(@PathVariable int transactionId) throws Exception {

			try {
				
				service.deleteById(transactionId);
				return "delete successfull";
				
			} catch (Exception e) {

			return "no transasction present with this transaction id";
			
		}


	}

	/**
	 * method name: getCount return type: integer argument: email
	 * 
	 * @param email
	 * 
	 *              author: Capgemini date: 9-Jan-2020 description: This method is
	 *              used to get a count of reminder to do from the
	 *              user_to_do_transaction table
	 * @return
	 */
	@GetMapping("/remindercount")
	public String getReminder() {

		int count = 0;
		try {

			count = service.getReminderToDo();
		} catch (Exception e) {
			
			return "You don't have any to do reminder ";

		}
		
		if(count!=0)
		{

		return "Your reminder to do count is: "+count;
		}
		
		else
		{
			return "You currently don't have any reminder";
		}

	}

	@GetMapping("/monthlytrack/{year}/{month}")
	public String trackByMonth(@PathVariable String year, @PathVariable String month) {
		List<String> list = null;
		
		Map<StringBuilder, String> monthlyTrackMap=new LinkedHashMap<>();

		try {

			list = service.getTrack(month, year);

			if (list.isEmpty()) {
				
				return "You have not done any transactions in this month of the year " +year;
			}
			
			else {
				
				
				
				for (String string : list) {
					
					StringBuilder category=new StringBuilder("Your Category Is : ");
					StringBuilder ttype=new StringBuilder("Your Transaction Type Is : ");
					StringBuilder amount=new StringBuilder("Your Trasnaction Amount Is : ");
					
					
					String[] strings = string.split(",");
					
					monthlyTrackMap.put(category, strings[0]);
					monthlyTrackMap.put(ttype, strings[1]);
					monthlyTrackMap.put(amount, strings[2]);
				}
			}

		} catch (Exception e) {
			
			return ""+e.getMessage();

		}

		return ""+monthlyTrackMap;

	}

	@GetMapping("/yearlytrack/{year}")
	public String trackByYear(@PathVariable String year) {

		
		List<String> list = null;
		
		Map<StringBuilder, String> YearlyTrackMap=new LinkedHashMap<>();

		try {

			list = service.getTrackByYear(year);

			if (list.isEmpty()) {
				
				throw new Exception( "You have not done any transactions in this particular year ");
			}
			
			else {
				
				
				
				for (String string : list) {
					
					StringBuilder category=new StringBuilder("Your Category Is : ");
					StringBuilder ttype=new StringBuilder("Your Transaction Type Is : ");
					StringBuilder amount=new StringBuilder("Your Trasnaction Amount Is : ");
					
					
					String[] strings = string.split(",");
					
					YearlyTrackMap.put(category, strings[0]);
					YearlyTrackMap.put(ttype, strings[1]);
					YearlyTrackMap.put(amount, strings[2]);
				}
			}

		} catch (Exception e) {
			
			return ""+e.getMessage();

		}

		return ""+YearlyTrackMap;
	}
	
	
	@GetMapping("/currentbalance")
	public String getCurrentBalance()
	{
		
		try {
	return("Your Current Balance is: Rs "	+service.getCurrentBalance(ExpenseTrackerAuthenticationcontroller.userEmail));
	
		}
		catch (Exception e) {
			return "You are currently not signed in"+"\n"+"Please sign in first to continue";
		}
		
	}

}
