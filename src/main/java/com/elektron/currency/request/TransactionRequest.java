package com.elektron.currency.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
	private Long userId;
	private String sourceCurrency;
	private String destinationCurrency;
	
	public TransactionRequest() {
		super();
	}

	public TransactionRequest(Long userId, String sourceCurrency, String destinationCurrency) {
		super();
		this.userId = userId;
		this.sourceCurrency = sourceCurrency;
		this.destinationCurrency = destinationCurrency;
	}
	
}
