package com.umer.simplefakebank.controller;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.umer.simplefakebank.dto.request.RequestAccountDTO;
import com.umer.simplefakebank.dto.response.ResponseAccountBalanceDTO;
import com.umer.simplefakebank.dto.response.ResponseAccountDTO;
import com.umer.simplefakebank.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Account Controller exposing 2 methods: - GET For the givem account Id, it
 * retrieves the balance. - POST Creates a new account. The response contains
 * user id and initial amount.
 *
 * @author umer
 *
 */
@RestController
public class AccountController extends BaseAccountController {

	public static final String ACCOUNT_END_POINT_V1 = "/v1/accounts";
	public static final String ACCOUNT_GET_END_POINT_V1 = ACCOUNT_END_POINT_V1 + "/{id}";

	public AccountController(AccountService accountService) {
		super(accountService);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(
			path = ACCOUNT_GET_END_POINT_V1,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@ApiOperation(
			value = "Get account balance",
			notes = "Given an account Id, retrieves the balance."
			)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "Account balance."),
					@ApiResponse(code = 404, message = "Account not found.")
					}
			)
	public ResponseEntity<ResponseAccountBalanceDTO> getBalance(@PathVariable("id") final long accountId) {
		return ResponseEntity.ok(accountService.retrieveBalance(accountId));
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(
			path = ACCOUNT_END_POINT_V1,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiOperation(
			value = "Create a new account.",
			notes = "Creates a new bank account with an initial deposit amount for a customer.\n"+
					"A single user may have multiple bank acoounts."
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 201, message = "Account created."),
					@ApiResponse(code = 400, message = "Cutomer not found to create an account."),
					@ApiResponse(code = 404, message = "Invalid request regarding new account information: negative initial amount, negative customer id.")
			}
	)
	public ResponseEntity postAccount(@Valid @RequestBody RequestAccountDTO requestAccountDTO) {

		// Call service layer for creating a new account
		ResponseAccountDTO responseAccountDTO = accountService.createNewAccount(requestAccountDTO);

		// Create a URI for the response entity.
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(responseAccountDTO.getId())
				.toUri();

		// Pass URI in the response entity
		return ResponseEntity.created(uri).build();
	}

}
