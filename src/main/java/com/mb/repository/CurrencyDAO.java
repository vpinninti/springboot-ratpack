package com.mb.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.model.Currency;

public interface CurrencyDAO extends JpaRepository<Currency, Long>{
	
	public Currency findByCode(String code);

}
