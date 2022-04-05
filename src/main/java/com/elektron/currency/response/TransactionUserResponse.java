package com.elektron.currency.response;

import java.util.List;

import com.elektron.currency.dto.TransactionDTO;
import com.elektron.currency.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionUserResponse {

	private UserDTO user;
	private List<TransactionDTO> transactions;
	
	public TransactionUserResponse() {
		super();
	}

	public TransactionUserResponse(UserDTO user, List<TransactionDTO> transactions) {
		super();
		this.user = user;
		this.transactions = transactions;
	}

}
