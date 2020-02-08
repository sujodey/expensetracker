package com.capg.expensetracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capg.expensetracker.model.UserToDoInformation;

public interface ToDoDao extends JpaRepository<UserToDoInformation, Integer> {

	@Query(value="select * from User_To_Do_Transaction where email=?1", nativeQuery = true)
	List<UserToDoInformation> findByToDo(String email);

	@Query(value="select count(*) from User_To_Do_Transaction where email=?1 and reminder='on' and date_of_transaction>sysdate-1", nativeQuery = true)
	int getCount(String email);

}
