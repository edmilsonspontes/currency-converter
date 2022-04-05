package com.elektron.currency.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConverterDTO {
	boolean success;
	TransactionDTO transaction;
	Map error;
	
	public ConverterDTO() {
		super();
	}

	public ConverterDTO(boolean success, TransactionDTO transaction, Map error) {
		super();
		this.success = success;
		this.transaction = transaction;
		this.error = error;
	}
}
