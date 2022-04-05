package com.elektron.currency.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elektron.currency.entity.UserEntity;
import com.elektron.currency.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Rest Controller")
@RestController
@RequestMapping("/user")
public class UserController {
	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@ApiOperation(value = "Find User by id ", response = ResponseEntity.class, tags = "findById")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
		try {
			log.info("INFO: find user ID: " + id);
			UserEntity user = userRepository.findById(id).orElse(null);
			
			if(user == null) {
				log.error("ERROR: user not found. ID = " + id);
				return new ResponseEntity<>("user not found. ID = " + id, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch (Exception e) {
	        log.error("ERROR: " + e.getLocalizedMessage());
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Find all users ", response = ResponseEntity.class, tags = "findAll")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "Not Authorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not Found!") })
	@GetMapping
	public ResponseEntity<Object> findAll() {
		try {
			log.info("INFO: find all users");
			Iterable<UserEntity> users = userRepository.findAll();
			
			if(users == null || ((Collection<?>) users).size() == 0) {
				log.error("ERROR: users not found");
				return new ResponseEntity<>("users not found.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(users, HttpStatus.OK);
		} catch (Exception e) {
	        log.error("ERROR: " + e.getLocalizedMessage());
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
