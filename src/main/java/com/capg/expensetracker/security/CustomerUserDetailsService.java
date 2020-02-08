package com.capg.expensetracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.expensetracker.dao.ExpenseTrackerDao;
import com.capg.expensetracker.model.UserInformation;



@Service
public class CustomerUserDetailsService implements UserDetailsService{

	
	 @Autowired
	 ExpenseTrackerDao userInformationRepo;
	 
	 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		 UserInformation user = userInformationRepo.findById(email).orElseThrow(
		            () -> new UsernameNotFoundException("User not found with this email id  "+email )
		        );
		 
		 

		        return UserPrincipal.create(user);
		        
	}
	
	
    @Transactional
    public UserDetails loadUserById(String email) throws Exception {
        UserInformation user = userInformationRepo.findById(email).orElseThrow(
            () -> new Exception("User not found with this email id  "+email )
        );


        return UserPrincipal.create(user);
    }

}
