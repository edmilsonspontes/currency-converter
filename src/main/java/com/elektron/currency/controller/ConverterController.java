package com.elektron.currency.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elektron.currency.dto.ConverterDTO;
import com.elektron.currency.entity.TransactionEntity;
import com.elektron.currency.entity.UserEntity;
import com.elektron.currency.repository.TransactionRepository;
import com.elektron.currency.repository.UserRepository;
import com.elektron.currency.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Converter Rest Controller")
@RestController
@RequestMapping("/converter")
public class ConverterController {
	Logger log = LoggerFactory.getLogger(ConverterController.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@ApiOperation(value = "cconvert currencies", response = ResponseEntity.class, tags = "convert")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> convert(@RequestBody RequestEntity<Map> request) {
		try {
			ConverterDTO converterDTO = transactionService.converter(Long.valueOf(request.getBody().get("userId").toString()), 
					request.getBody().get("sourceCurrency").toString(), request.getBody().get("destinationCurrency").toString());
			
			log.info("INFO: converter FROM " + request.getBody().get("sourceCurrency").toString() + " TO " + request.getBody().get("destinationCurrency").toString());
			if(!converterDTO.isSuccess()) {
				log.error("ERROR: " + converterDTO.getError().get("info"));
				return new ResponseEntity<>(converterDTO.getError(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(converterDTO.getTransaction(), HttpStatus.OK);

		} catch (Exception e) {
	        log.error("ERROR: " + e.getLocalizedMessage());
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Find transaction by id ", response = ResponseEntity.class, tags = "findById")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
		try {
			log.info("INFO: find transaction ID: " + id);
			TransactionEntity transaction = transactionRepository.findById(id).orElse(null);
			
			if(transaction == null) {
				log.error("ERROR: transaction not found. ID = " + id);
				return new ResponseEntity<>("transaction not found. ID = " + id, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(transaction, HttpStatus.OK);
		} catch (Exception e) {
	        log.error("ERROR: " + e.getLocalizedMessage());
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Find all transactions", response = ResponseEntity.class, tags = "findAll")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "Not Authorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll() {
		try {
			log.info("INFO: find all transactions");
			Iterable<TransactionEntity> transactions = transactionRepository.findAll();
			
			if(transactions == null || ((Collection<?>) transactions).size() == 0) {
				log.error("ERROR: transactions not found");
				return new ResponseEntity<>("transactions not found.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(transactions, HttpStatus.OK);
		} catch (Exception e) {
	        log.error("ERROR: " + e.getLocalizedMessage());
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Find transactions by user ", response = ResponseEntity.class, tags = "findTransactionsByUser")
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findTransactionsByUser(@PathVariable("id") Long id) {
		try {
			log.info("INFO: find transactions by user. ID: " + id);
			UserEntity user = userRepository.findById(id).orElse(null);
			
			if(user == null) {
				log.error("User not found. ID = " + id);
				return new ResponseEntity<>("User not found. ID = " + id, HttpStatus.NOT_FOUND);
			}
			
			List<TransactionEntity> transactions = transactionRepository.findByUser(user);
			if(transactions == null || transactions.isEmpty()) {
				log.error("ERROR: transactions not found");
				return new ResponseEntity<>("Transactions not found", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(transactions, HttpStatus.OK);
		} catch (Exception e) {
	        log.error("ERROR: " + e.getLocalizedMessage());
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
