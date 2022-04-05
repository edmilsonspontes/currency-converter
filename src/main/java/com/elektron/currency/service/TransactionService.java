package com.elektron.currency.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.elektron.currency.dto.ConverterDTO;
import com.elektron.currency.dto.TransactionDTO;
import com.elektron.currency.entity.TransactionEntity;
import com.elektron.currency.entity.UserEntity;
import com.elektron.currency.repository.TransactionRepository;
import com.elektron.currency.repository.UserRepository;

@Service
public class TransactionService {
	
	@Autowired
	ExchangeRatesAPIService exchangeRatesAPIService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public ConverterDTO converter(Long userId, String source, String destination) {
		
		ConverterDTO result = exchange(userId, source, destination);
		
		return result;
	}
	
	public ConverterDTO exchange(Long userId, String source, String destination) {
		ConverterDTO result = null;
		try {
			UserEntity user = userRepository.findById(userId).orElse(null);
			
			if(user == null) {
				Map<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("code", 1);
				errorMap.put("info", "User not found. Id = " + userId);
				return new ConverterDTO(false, null, errorMap);
			}

			Map<String, Object> ratesMap = exchangeRatesAPIService.exchange(source, destination);
			boolean success = Boolean.valueOf(ratesMap.get("success").toString());

			if(success) {
				Double sourceValue = Double.valueOf(ratesMap.get(source).toString());
				Double destinationValue = Double.valueOf(ratesMap.get(destination).toString());
				Double conversionRate = conversionRate(sourceValue, destinationValue); 
				Date dateTime = Calendar.getInstance().getTime();
				
				TransactionEntity transactionEntity = new TransactionEntity(null, user, source, sourceValue, destination, destinationValue, conversionRate, dateTime );
				transactionEntity = transactionRepository.save(transactionEntity);
				
				TransactionDTO transactionDTO = new TransactionDTO(
						transactionEntity.getId(), 
						user.getId(),
						transactionEntity.getDateTimeConverter(), 
						transactionEntity.getSourceCurrency(), 
						transactionEntity.getSourceValue(), 
						transactionEntity.getDestinationCurrency(), 
						transactionEntity.getDestinationValue(), 
						conversionRate
					);
				result = new ConverterDTO(true, transactionDTO, null);
			}
			else {
				Map errorMap = (Map) ratesMap.get("error");
				result = new ConverterDTO(false, null, errorMap);
			}
		}
		catch (Exception e) {
			Map errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("code", HttpStatus.BAD_GATEWAY.ordinal());
			errorMap.put("info", e.getLocalizedMessage());
			result = new ConverterDTO(false, null, errorMap);
		}
		return result;
	}
	
	public Double conversionRate(Double sourceCurrency, Double destinationCurrency) {
		return destinationCurrency/sourceCurrency;
	}

}
