package com.mb.service;

import com.mb.dto.BalanceDTO;
import com.mb.model.TxnAccount;
import com.mb.model.User;

public interface UserService {
	
	public TxnAccount create();

	public  BalanceDTO createBalanceDTO();

}
