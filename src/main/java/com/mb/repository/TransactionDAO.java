package com.mb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.model.Transaction;
import com.mb.model.User;

public interface  TransactionDAO extends JpaRepository<Transaction, Long>{
	
	public List<Transaction> getAllTxnByUser(User user);

}
