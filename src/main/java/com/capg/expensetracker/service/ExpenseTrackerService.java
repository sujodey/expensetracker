package com.capg.expensetracker.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.expensetracker.dao.ExpenseTrackerDao;
import com.capg.expensetracker.dao.ToDoDao;
import com.capg.expensetracker.dao.UserTransactionDao;
import com.capg.expensetracker.model.UserInformation;
import com.capg.expensetracker.model.UserToDoInformation;
import com.capg.expensetracker.model.UserTransaction;

@Service
public class ExpenseTrackerService {

	@Autowired
	ExpenseTrackerDao userInfoRepository;

	@Autowired
	UserTransactionDao userTransactionRepository;

	@Autowired
	ToDoDao toDoRepository;
	
	UserInformation information=new UserInformation();
	
	
	

	/**
	 * method name: createAccount return type:UserInformation object argument:
	 * UserInformation
	 * 
	 * @param userObject
	 * 
	 *                   author: Capgemini date: 9-Jan-2020 description: This method
	 *                   is used to add user's information in user_information table
	 * @return
	 * @throws Exception 
	 */
	public int createAccount(UserInformation userObject) throws Exception {
		
		
		String emailPattern="^[A-Za-z0-9+_.-]+@(.+)$";
		String userEmail=userObject.getEmail();
		
		String mobilePattern="[7-9]{1}[0-9]{9}";
		String userMobile=String.valueOf(userObject.getMobileNumber());
		
		
		boolean emailMatcher=Pattern.matches(emailPattern, userEmail);
		boolean mobileMatcher=Pattern.matches(mobilePattern, userMobile);
	
		
	
		
		if (emailMatcher)

		{
			if(mobileMatcher)
			{
				
				
				userInfoRepository.save(userObject);
			
		return 1;
		
			}
		
		
			else
				{
					return 2;
				}
			
		}
		
		else
		{
			return 3;
		}
		
		
		
	}

	/**
	 * method name: userLogin return type:UserInformation object argument:
	 * String
	 * 
	 * @param email
	 * 
	 *              author: Capgemini date: 9-Jan-2020 description: This method is
	 *              used to fetch all the user's information
	 * @param password 
	 * @return
	 * @throws Exception
	 */
	public Optional<UserInformation> userLogin(String email) throws Exception {

		
		String emailPattern="^[A-Za-z0-9+_.-]+@(.+)$";
		boolean emailMatcher=Pattern.matches(emailPattern, email);
		
		
		Optional<UserInformation> userInfo;
		
		if(emailMatcher)
		{
			
			
		userInfo = userInfoRepository.findById(email);
		
		
		// UNdo The Changes*****************************************************
		 
			/*
			 * if (userInfo.isEmpty()) {
			 * 
			 * throw new Exception("The username and password given by you does not match");
			 * }
			 */
		  	information=userInfo.get();
		  	
		  return userInfo;
		
  
			 
			  
			 }
			 
	
			
		
		else
		{
			throw new Exception("Please enter a valid email id.....the email id must have @ and . character");
		}

		
	}

	/**
	 * method name: updateTransaction return type:UserTransaction object argument:
	 * transObject
	 * 
	 * @param transObject
	 * 
	 *                    author: Capgemini date: 9-Jan-2020 description: This
	 *                    method is used to add user's transaction in
	 *                    user_transaction table
	 * @return
	 * @throws Exception
	 */
	public UserTransaction updateTransaction(UserTransaction userTransactionObject) throws Exception {

		userTransactionObject.setEmail(information);
		String userCategory=userTransactionObject.getCategory();
		String userCategoryPattern="education|health|personal|housing|grocceries|transportaion|Education|Health|Personal|Housing|Grocceries|Transportaion";

		String userTrasactionType=userTransactionObject.getTransactionType();
		String userTransactionTypePattern="income|expense|Income|Expense";
		
		boolean categoryMatch=Pattern.matches(userCategoryPattern, userCategory);
		boolean transactionTypeMatch=Pattern.matches(userTransactionTypePattern, userTrasactionType);
		
		UserTransaction trans = null;
		
			
			
			if(categoryMatch)
			{
				
				if(transactionTypeMatch)
				{
					
					
					trans = userTransactionRepository.save(userTransactionObject);

				}
				
				else
				{
					throw new Exception("plaese choose a transaction type from Income | Expense");
					
					
				}
		
			}
			
			else
			{
				throw new Exception("plaese choose a catgeory from education | health | personal | housing | grocceries or transportaion");
			}

		return trans;
		
		

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
	 * @throws Exception
	 */
	public List<UserTransaction> getAllTransactions() throws Exception {
		List<UserTransaction> transList = null;
		
		String emailPattern="^[A-Za-z0-9+_.-]+@(.+)$";
		boolean emailMatcher=Pattern.matches(emailPattern, information.getEmail());
		
		if(emailMatcher)
		{
		transList = userTransactionRepository.findByTransactions(information.getEmail());

		if (transList.isEmpty()) {
			throw new Exception("no transaction present ");
		}

		}
		
		else
		{
			
			throw new Exception("Please enter a valid email id.....the email id must have @ and . character");
			
		}
		return transList;

	}

	/**
	 * method name: updateToDo return type:UserToDoInformation object argument:
	 * todoObject
	 * 
	 * @param todoObject
	 * 
	 *                   author: Capgemini date: 9-Jan-2020 description: This method
	 *                   is used to add user's to do in user_to_do_transaction table
	 * @return
	 * @throws Exception
	 */
	public UserToDoInformation updateToDO(UserToDoInformation todoObject) throws Exception {

		todoObject.setEmail(information);
		UserToDoInformation toDo = null;
		
		String userCategory=todoObject.getCategory();
		String userCategoryPattern="education|health|personal|housing|grocceries|transportaion|Education|Health|Personal|Housing|Grocceries|Transportaion";

		String userTrasactionType=todoObject.getTransactionType();
		String userTransactionTypePattern="income|expense|Income|Expense";
		
		String userReminder=todoObject.getReminder();
		String userReminderPattern="on|off|On|Off";
		
		
		boolean categoryMatch=Pattern.matches(userCategoryPattern, userCategory);
		boolean transactionTypeMatch=Pattern.matches(userTransactionTypePattern, userTrasactionType);
		boolean reminderTypeMatch=Pattern.matches(userReminderPattern, userReminder);
		
			
			if(categoryMatch)
			{
				
				if(transactionTypeMatch)
				{
					
					if(reminderTypeMatch)
					{
			
		toDo = toDoRepository.save(todoObject);
				
					}
					
					else
					{
						throw new Exception("Plaese choose a valid reminder from either On or Off");
					}
					
				}
				
				else
				{
					throw new Exception("Plaese choose a valid transaction type from either Income or Expense");
				}
				
			
			}
			
			else
			{
				throw new Exception("plaese choose a catgeory from education | health | personal | housing | grocceries or transportaion");
			}

		if (toDo == null) {
			throw new Exception("To Do List can't be created....please try again later");
		}

		return toDo;
		
		
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
	 * @throws Exception
	 */
	public List<UserToDoInformation> getAllToDo() throws Exception {

		List<UserToDoInformation> toDoList = null;
		toDoList = toDoRepository.findByToDo(information.getEmail());
		if (toDoList.isEmpty()) {
			throw new Exception("No To Do List Present ");
		}

		return toDoList;
	}

	/**
	 * method name: deleteById return type: integer argument: transactionId
	 * 
	 * @param transactionId
	 * 
	 *                      author: Capgemini date: 9-Jan-2020 description: This
	 *                      method is used to delete a given to do from the
	 *                      user_to_do_transaction table
	 * @return
	 * @throws Exception 
	 */
	public void deleteById(int transactionId) throws Exception {

		
		try {
		toDoRepository.deleteById(transactionId);

		}catch (Exception e) {
			
			throw new Exception("Please enter a valid transaction id");
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
	 * @throws Exception
	 */
	public int getReminderToDo() throws Exception {

		int count = 0;
		count = toDoRepository.getCount(information.getEmail());

		if (count == 0) {
			throw new Exception("No reminder to do found for this Email Id!!");
		}

		return count;
	}

	public List<String> getTrack(String month, String year) throws Exception {
		
			

		return userTransactionRepository.TrackByMonth(month, year, information.getEmail());
		
		

		

	}

	public List<String> getTrackByYear(String year) throws Exception {

				

			return userTransactionRepository.trackByYear(year, information.getEmail());
			
			
			

	}
	

	public List<String> getExistingEmails() {
		List<String> existingEmailList=userInfoRepository.findEmail();
		return existingEmailList;
	}
	
	

	public long getCurrentBalance(String email) {
		
		return userInfoRepository.getCurrentBalance(email);
	}

	public UserInformation updateAccount(UserInformation userInfo) {
		
	return	userInfoRepository.save(userInfo);
		
	}

}
