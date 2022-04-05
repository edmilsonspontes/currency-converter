package com.elektron.currency.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
	
	private Long id;
	private Long userId;
	private Date dateTime;
	private String sourceCurrency;
	private Double sourceValue;
	private String destinationCurrency;
	private Double destinationValue;
	private Double conversionRate;
	
	public TransactionDTO() {
		super();
	}
	
	public TransactionDTO(Long id, Long userId, Date dateTime, String sourceCurrency, Double sourceValue, String destinationCurrency,
			Double destinationValue, Double conversionRate) {
		super();
		this.id = id;
		this.userId = userId;
		this.dateTime = dateTime;
		this.sourceCurrency = sourceCurrency;
		this.sourceValue = sourceValue;
		this.destinationCurrency = destinationCurrency;
		this.destinationValue = destinationValue;
		this.conversionRate = conversionRate;
	}
}
