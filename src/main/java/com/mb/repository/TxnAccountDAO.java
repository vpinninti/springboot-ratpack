package com.mb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.model.TxnAccount;
import com.mb.model.User;

public interface TxnAccountDAO extends JpaRepository<TxnAccount, Long>{

	public  TxnAccount findByUser(User user);
}
