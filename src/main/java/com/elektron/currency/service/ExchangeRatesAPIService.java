package com.elektron.currency.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.elektron.currency.config.AppConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
@PropertySource("classpath:application.properties")
public class ExchangeRatesAPIService {

	//http://api.exchangeratesapi.io/v1/latest?access_key=8f09d4ce2eb9ea7620ef51976ae0f857&format=1&symbols=BRL, USD

	@Autowired(required=true)
	AppConfig appConfig;

	@Value("${api.exchangeratesapi.url}")
	private String apiUrl;

	@Value("${api.exchangeratesapi.access.key}")
	private String accessKey;

	@Value("${api.exchangeratesapi.base.default}")
	private String baseDefault;

	public Map<String, Object> rates(String currency) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String url = getApiUrl() 
					+ "?access_key=" 
					+ getAccessKey() 
					+ "&format=1" 
					+ "&base="
					+ getBaseDefault() 
					+ "&symbols=" 
					+ currency;
			
			String ratesJson = appConfig.restTemplate().getForObject(url, String.class);
			JsonObject ratesExchange = new Gson().fromJson(ratesJson, JsonObject.class);

			boolean success = ratesExchange.get("success").getAsBoolean();
			
			if(success) {
				resultMap.put("success", success);
				Map rates = new Gson().fromJson(ratesExchange.get("rates"), Map.class);
				resultMap.putAll(rates);
			}
			else {
				Map errorMap = new Gson().fromJson(ratesExchange.get("error"), Map.class);
				resultMap.putAll(errorMap);
			}
		}
		catch (Exception e) {
			Map errorMap = new HashMap<String, Object>();
			resultMap.put("success", false);
			errorMap.put("code", HttpStatus.BAD_GATEWAY.ordinal());
			errorMap.put("info", e.getLocalizedMessage());
			resultMap.putAll(errorMap);
		}
		return resultMap;
	}
	
	public Map<String, Object> exchange(String sourceCurrency, String destinationCurrency) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Map<String, Object> sourceMap = rates(sourceCurrency);
			resultMap.putAll(sourceMap);
			
			if((Boolean)sourceMap.get("success")) {
				Map<String, Object> destinationMap = rates(destinationCurrency);
				resultMap.putAll(destinationMap);
			}
		}
		catch (Exception e) {
			Map errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("code", HttpStatus.BAD_GATEWAY.ordinal());
			errorMap.put("info", e.getLocalizedMessage());
			resultMap.putAll(errorMap);
		}
		return resultMap;
	}

}
