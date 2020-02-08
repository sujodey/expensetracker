package com.capg.expensetracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.expensetracker.dao.UserTransactionDao;
import com.capg.expensetracker.model.EmailIdAndPassword;
import com.capg.expensetracker.model.UserInformation;
import com.capg.expensetracker.security.JwtTokenProvider;
import com.capg.expensetracker.service.ExpenseTrackerService;

@RestController
@RequestMapping("/expensetracker/authentication")
public class ExpenseTrackerAuthenticationcontroller {

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

	List<String> existingEmails = new ArrayList<>();
	
	
	public static long mobileNumber=0;
	public static String userPassword="";
	public static long balance=0;
	public static String userEmail="";
	public static String name="";
	

	/**
	 * method name: createAccount return type:UserInformation object argument:
	 * UserInformation
	 * 
	 * @param userObject
	 * 
	 *                   author: Capgemini date: 9-Jan-2020 description: This method
	 *                   is used to fetch user details
	 * @return
	 */
	@PostMapping(path = "/createaccount")
	public String createAccount(@RequestBody UserInformation userObject) {
		

		int userInfo = 0;

		existingEmails = service.getExistingEmails();
		String decryptedPassword="";
		

		if (!existingEmails.contains(userObject.getEmail()))

		{

			try {
				
				decryptedPassword=userObject.getPassword();

				userObject.setPassword(encodedPassword.encode(userObject.getPassword()));

				userInfo = service.createAccount(userObject);
				
				if(userInfo==0)
				{
					
					return "Please enter only digits in your balance";
				}
				
				if(userInfo==2)
				{
					return "Mobile number must strat with 7,8 or 9 and must have only 10 digits";
				}
				
				if(userInfo==3)
				{
					return "Please enter a valid email id.....the email id must have @ and . character";
				}
				
			} catch (Exception e) {
				
				return ""+e.getMessage();

			}

			return "You Have succesfully created you account "+"\n"+"your email id as: "+userObject.getEmail()+"\n"+"your password as: "+decryptedPassword+ "\n" +"your name as: "+userObject.getName()+ "\n"+"your balance as: "+userObject.getBalance();

		}

		else {
			
			return "This email Id already exist";

		}

	}

	/**
	 * method name: userLogin return type:UserInformation object argument: String
	 * 
	 * @param email
	 * 
	 *              author: Capgemini date: 9-Jan-2020 description: This method is
	 *              used to fetch the list of user's information
	 * @return
	 */
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody EmailIdAndPassword credentials) {

		UserInformation userInfo = null;
		String jwt = "";

		try {
			userInfo = service.userLogin(credentials.getEmail()).orElse(new UserInformation());
			
			name=userInfo.getName();
			balance=userInfo.getBalance();
			userPassword=userInfo.getPassword();
			mobileNumber=userInfo.getMobileNumber();
			userEmail=userInfo.getEmail();
			
			

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getEmail(), credentials.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			jwt = tokenProvider.generateToken(authentication);

		} catch (Exception e) {
			
			return ResponseEntity.ok("User Name or Password given is incorrect");

		}

		return ResponseEntity.ok(new com.capg.expensetracker.security.JwtAuthenticationResponse(jwt));
	}

}
