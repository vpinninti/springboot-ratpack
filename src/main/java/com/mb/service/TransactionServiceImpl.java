package com.mb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mb.dto.SpendRequestDTO;
import com.mb.dto.SpendResponseDTO;
import com.mb.model.Currency;
import com.mb.model.Transaction;
import com.mb.model.TxnAccount;
import com.mb.model.User;
import com.mb.repository.CurrencyDAO;
import com.mb.repository.TransactionDAO;
import com.mb.repository.TxnAccountDAO;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionDAO transactionDAO;
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
	
	public List<Transaction> getAllTransaction(String token) {
		User user=null;
		
		if(isLocalCache) {
			user=(User)localCacheService.get(token);
		}else {
			user=(User)redisCacheService.get(token);
		}
		return transactionDAO.getAllTxnByUser(user);
	}
	
	public SpendResponseDTO spend(String token,SpendRequestDTO spendRequest) {
		SpendResponseDTO spendRes=new SpendResponseDTO();
		Transaction txn =new Transaction();
		
		User user=null;
		
		if(isLocalCache) {
			user=(User)localCacheService.get(token);
		}else {
			user=(User)redisCacheService.get(token);
		}
		
		if(user == null) {
			spendRes.setStatus("failed");
			return spendRes;
		}
		TxnAccount account=txnAccountDAO.findByUser(user);
		
		if(account.getBalance() < spendRequest.getAmount()) {
			spendRes.setStatus("failed");
			return spendRes;
		}
		
		txn.setAmount(spendRequest.getAmount());
		txn.setDescription(spendRequest.getDescription());
		txn.setDate(spendRequest.getDate());
		txn.setUser(user);
		txn.setTxnAccount(account);
		Currency currency =currencyDAO.findByCode(spendRequest.getCurrency());
		if(currency == null) {
			spendRes.setStatus("failed");
			return spendRes;
		}
			
		txn.setCurrency(currency);
		txn=transactionDAO.saveAndFlush(txn);
		
		account.setBalance(account.getBalance()-txn.getAmount());
		txnAccountDAO.saveAndFlush(account);
		spendRes.setTxnId(txn.getId());
		spendRes.setStatus("success");
		return spendRes;
	}

}
