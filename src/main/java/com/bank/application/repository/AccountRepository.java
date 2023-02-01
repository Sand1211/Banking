package com.bank.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.application.entity.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

	List<Accounts> findByCifNumber(Integer cifNumber);

	@Modifying
	@Query(value = "update Accounts a set a.balanceAmount=:finalAmountAfterDeducted where a.accountNumber=:accountNumber")
	void updateAmount(float finalAmountAfterDeducted, int accountNumber);

	@Query(value = "select * from Accounts a  Where a.cif_number=:cifNumber", nativeQuery = true)
	List<Accounts> getAccountList(Integer cifNumber);
	
	@Modifying
	@Query(value = "update  Accounts  a set a.account_Status= 'INACTIVE' where a.account_number=:accountNumber" , nativeQuery = true)
	void InactiveAccountByAccntNumber(Integer accountNumber);
	
	@Modifying
	@Query(value = "update  Accounts  a set a.account_Status= 'ACTIVE' where a.account_number=:accountNumber" , nativeQuery = true)
	void activeAccountByAccntNumber(Integer accountNumber);
	
	

}
