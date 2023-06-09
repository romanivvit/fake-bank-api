package com.umer.simplefakebank.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.umer.simplefakebank.dto.request.RequestOperationDTO;
import com.umer.simplefakebank.dto.response.ResponseOperationDTO;
import com.umer.simplefakebank.dto.response.ResponseOperationsDTO;
import com.umer.simplefakebank.service.OperationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.MediaType;


import lombok.extern.log4j.Log4j2;
/**
 * The operation controller exposes two endpoints: - GET When account Id is
 * given, it retrieves all of the operations. - POST It creates a new transfer
 * operation in your body the sender and receiver accounts ids and the value
 * used in the transaction.
 * 
 * @author umer
 *
 */

@RestController
@Log4j2
public class OperationController extends BaseOperationController{

	public static final String OPERATION_END_POINT_V1 = "/v1/operations";
	public static final String OPERATION_GET_END_POINT = "/fromAccount/{accountId}";

	public OperationController(OperationService operationService) {
		super(operationService);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = OPERATION_GET_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieves the transaction history for a given account.", 
					notes = "Given the account Id, it retriieves the operations/transfer wher this account has participated."
	)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "List of operations."),
			@ApiResponse(code = 404, message = "Account not found.") 
	})
	public ResponseEntity<ResponseOperationsDTO> getOperations(@PathVariable("accountId") final long accountId) {
		log.debug("Retrieving operations for the account: {}", accountId);
		return ResponseEntity.ok(operationService.retrieveOperations(accountId));
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(
			path = OPERATION_END_POINT_V1,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiOperation(
			value = "Creates a new transfer.",
			notes = "Transfers amount between any two accounts, including those owned by different customers."
	)
	@ApiResponses(value= {
			@ApiResponse(code = 201, message = "Transfer created."),
			@ApiResponse(code = 404, message = "Sender/reciever account not found."),
			@ApiResponse(code = 400, message= "Sender/reciever account id is negative, Insufficient balance to transfer, same account used in the transfer operation.")
	})
	public ResponseEntity postOperation(@Valid @RequestBody RequestOperationDTO requestOperationDTO) {
		ResponseOperationDTO responseOperationDTO = operationService.createNewOperation(requestOperationDTO);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(responseOperationDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
