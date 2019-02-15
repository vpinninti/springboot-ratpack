package com.mb.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mb.dto.BalanceDTO;
import com.mb.model.TxnAccount;
import com.mb.model.User;
import com.mb.repository.CurrencyDAO;
import com.mb.repository.TxnAccountDAO;
import com.mb.repository.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private TxnAccountDAO txnAccountDAO;
	
	@Autowired
	private CacheService localCacheService;
	
	@Autowired
	private CacheService redisCacheService;
	
	@Value("${redis.isLocalCache}")
	private boolean isLocalCache;
	

	public TxnAccount create() {
		User user = new User();
		user.setCreationDate(new Date());
		user = userDAO.saveAndFlush(user);

		TxnAccount account = new TxnAccount();
		account.setBalance(new Double("100.00"));
		account.setCurrency(currencyDAO.findOne(1L));
		account.setUser(user);
		account = txnAccountDAO.saveAndFlush(account);
		
		return account;

	}
	
	
	public  BalanceDTO createBalanceDTO() {
		TxnAccount account = create();
		String token=null;
		
		if(isLocalCache) {
			
			token= localCacheService.getToken();
			localCacheService.put(token, account.getUser());
		}else {
			
			token= redisCacheService.getToken();
			redisCacheService.put(token, account.getUser());
		}
		
		
		BalanceDTO balance =new BalanceDTO();
		balance.setBalance(account.getBalance());
		balance.setCurrency(account.getCurrency().getCode());
		balance.setToken(token);
		return balance;
	}

}
