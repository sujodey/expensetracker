package com.capg.expensetracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capg.expensetracker.model.UserInformation;

@Repository
public interface ExpenseTrackerDao extends JpaRepository<UserInformation, String> {

	@Query("select email from UserInformation")
	List<String> findEmail();

	
	@Query("select balance from UserInformation where email=?1")
	long getCurrentBalance(String email);

}
