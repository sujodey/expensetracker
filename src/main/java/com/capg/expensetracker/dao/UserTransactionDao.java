package com.capg.expensetracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capg.expensetracker.model.UserTransaction;

@Repository
public interface UserTransactionDao extends JpaRepository<UserTransaction, Integer> {

	@Query(value="select * from User_Transaction where email=?1", nativeQuery = true)
	List<UserTransaction> findByTransactions(String email);


	@Query(value="select category,transaction_type,sum(amount) from User_Transaction where to_char(date_of_transaction,'mm')=?1 and to_char(date_of_transaction,'yyyy')=?2 and email=?3 group by category,transaction_type order by category", nativeQuery = true )
	List<String> TrackByMonth(String month, String year, String email);



	@Query(value="select category,transaction_type,sum(amount) from User_Transaction where to_char(date_of_transaction,'yyyy')=?1 and email=?2 group by category,transaction_type order by category", nativeQuery = true)	
	List<String> trackByYear(String year, String email);

}
