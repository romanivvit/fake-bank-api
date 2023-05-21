package com.umer.simplefakebank.service.mapper;

import com.umer.simplefakebank.dto.request.RequestAccountDTO;
import com.umer.simplefakebank.dto.request.RequestOperationDTO;
import com.umer.simplefakebank.dto.response.ResponseAccountDTO;
import com.umer.simplefakebank.dto.response.ResponseOperationDTO;
import com.umer.simplefakebank.dto.request.CreateUserDTO;
import com.umer.simplefakebank.dto.response.ResponseUserDTO;
import com.umer.simplefakebank.entities.Account;
import com.umer.simplefakebank.entities.Operation;
import com.umer.simplefakebank.entities.User;

public class BankMapper {

	public static Account toAccountEntity(RequestAccountDTO requestAccountDTO) {
		return Account
				.builder()
				.initialDepositAmount(requestAccountDTO.getInitailDepositAmount())
				.balance(requestAccountDTO.getInitailDepositAmount())
				.build();
	}

	public static User toUserEntity(CreateUserDTO userDTO) {
		return User
				.builder()
				.username(userDTO.getUsername())
				.password(userDTO.getPassword())
				.email(userDTO.getEmail())
				.build();
	}

	public static ResponseUserDTO toResponseUserDTO(User user) {
		return ResponseUserDTO
				.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();
	}


	public static ResponseAccountDTO toResponseAccountDTO(Account account) {
		return ResponseAccountDTO
				.builder()
				.id(account.getId())
				.userId(account.getUser().getId())
				.balance(account.getBalance())
				.creationTimestamp(account.getCreationTimestamp())
				.build();
	}
	

	public static Operation toOperationEntity(RequestOperationDTO requestOperationDTO) {
		return Operation
				.builder()
				// TODO: Check why sender and receiver are not needed here?
				.value(requestOperationDTO.getValue())
				.build();
	}

	private static Long generateUniqueId() {
		// Generate a unique ID using your preferred method (e.g., UUID, database auto-increment, etc.)
		// For demonstration purposes, I'm using a simple timestamp-based ID generation
		return System.currentTimeMillis();
	}

	public static ResponseOperationDTO toResponseOperationDTO(Operation operation) {
		return ResponseOperationDTO
				.builder()
				.id(operation.getId())
				.senderAccountId(operation.getSenderAccount().getId())
				.receiverAccountId(operation.getReceiverAccount().getId())
				.value(operation.getValue())
				.creationTimestamp(operation.getOperationDateTime())
				.build();		
	}

}
