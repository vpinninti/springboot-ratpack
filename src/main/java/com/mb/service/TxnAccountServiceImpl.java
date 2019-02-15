package com.mb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mb.dto.AccountBalanceDTO;
import com.mb.model.TxnAccount;
import com.mb.model.User;
import com.mb.repository.TxnAccountDAO;

@Service("txnAccountService")
public class TxnAccountServiceImpl implements TxnAccountService{
	
	@Autowired
	private TxnAccountDAO txnAccountDAO;
	
	@Autowired
	private CacheService localCacheService;
	
	@Autowired
	private CacheService redisCacheService;
	
	@Value("${redis.isLocalCache}")
	private boolean isLocalCache;
	
	public TxnAccount getTxnAccountByUser(User user) {
		
		
		if(user == null) {
			return null;
		}
		
		return txnAccountDAO.findByUser(user);
	}
	
	public AccountBalanceDTO getAccBalanceDTO(String token) {
		User user=null;
		AccountBalanceDTO accBal= new AccountBalanceDTO();
	
		if(isLocalCache) {
		
			user=(User)localCacheService.get(token);
		}else {
			user=(User)redisCacheService.get(token);
		}
		
		if(user != null ) {
			TxnAccount account=getTxnAccountByUser(user);
			if(account == null) {
				accBal.setStatus("failed");
			}else {
				accBal.setBalance(account.getBalance());
				accBal.setCurrency(account.getCurrency().getCode());
				accBal.setStatus("success");
			}
		}else{
			accBal.setStatus("invaliduser");
		}
		
		return accBal;
	}
	

}
