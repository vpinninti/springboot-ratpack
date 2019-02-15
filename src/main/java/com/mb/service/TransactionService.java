package com.mb.service;

import java.util.List;

import com.mb.dto.SpendRequestDTO;
import com.mb.dto.SpendResponseDTO;
import com.mb.model.Transaction;

public interface TransactionService {

	public List<Transaction> getAllTransaction(String token);
	public SpendResponseDTO spend(String token,SpendRequestDTO spendRequest);
}
