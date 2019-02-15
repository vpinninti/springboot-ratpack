package com.mb.service;

import com.mb.dto.AccountBalanceDTO;
import com.mb.model.TxnAccount;
import com.mb.model.User;

public interface TxnAccountService {
	public TxnAccount getTxnAccountByUser(User use);
	public AccountBalanceDTO getAccBalanceDTO(String token);

}