package com.mb.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.mb.dto.BalanceDTO;
import com.mb.dto.TransactionDTO;
import com.mb.model.Transaction;
import com.mb.model.TxnAccount;


public class DtoUtil {
	
	
	public static BalanceDTO createBalanceDTO(TxnAccount account) {
		
		BalanceDTO balance =new BalanceDTO();
		balance.setBalance(account.getBalance());
		balance.setCurrency(account.getCurrency().getCode());
		return balance;
	}
	
	
	public static List<TransactionDTO> createTxnDtoList(List<Transaction> txnList) {
		 List<TransactionDTO> list =new ArrayList<TransactionDTO>();
		 for(Transaction txn : txnList) {
			 TransactionDTO txnDto = new TransactionDTO();
			 
			 txnDto.setAmount(txn.getAmount());
			 txnDto.setDescription(txn.getDescription());
			 txnDto.setCurrency(txn.getCurrency().getCode());
			 txnDto.setDate(txn.getDate());
			 list.add(txnDto);
		 }
		 return list;
	}

}
