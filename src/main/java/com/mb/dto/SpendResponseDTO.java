package com.mb.dto;

public class SpendResponseDTO {
	
	private Long txnId;
	private String status;
	
	public Long getTxnId() {
		return txnId;
	}
	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
