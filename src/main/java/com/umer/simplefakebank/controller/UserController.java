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
import com.umer.simplefakebank.dto.request.CreateUserDTO;
import com.umer.simplefakebank.dto.response.ResponseUserDTO;
import com.umer.simplefakebank.dto.request.LoginUserDTO;
import com.umer.simplefakebank.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.umer.simplefakebank.repsitory.UserRepository;
import com.umer.simplefakebank.entities.User;
import com.umer.simplefakebank.service.mapper.BankMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

	public static final String USER_GET_END_POINT_V1 = "/v1/user/{id}";
//	private final PasswordEncoder passwordEncoder;

	private final UserService userService;
	private final UserRepository userRepository;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiOperation(
			value = "Create a new user.",
			notes = "Creates a new user."
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 201, message = "User created."),
			}
	)
	public ResponseEntity postUser(@Valid @RequestBody CreateUserDTO createUserDTO) {

		// Call service layer for creating a new account
		ResponseUserDTO response = userService.createNewUser(createUserDTO);

		// Create a URI for the response entity.
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(response.getId())
				.toUri();

		// Pass URI in the response entity
		return ResponseEntity.created(uri).build();
	}

	@GetMapping(
			path = USER_GET_END_POINT_V1,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiOperation(
			value = "Get a user by ID.",
			notes = "Returns a user by their ID."
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "User found."),
					@ApiResponse(code = 404, message = "User not found.")
			}
	)
	public ResponseEntity<ResponseUserDTO> getUser(@PathVariable Long id) {
		// Call service layer for getting a user
		ResponseUserDTO response = userService.getUserById(id);

		// Return the user in the response entity
		return ResponseEntity.ok(response);
	}

	@ApiOperation(
			value = "Login a user.",
			notes = "Creates a user."
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "User logined."),
			}
	)

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/login")
	public ResponseEntity<ResponseUserDTO> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO) {
		boolean authenticated = userService.authenticateUser(loginUserDTO.getEmail(), loginUserDTO.getPassword());
		User user = userRepository.findByEmail(loginUserDTO.getEmail());
		if (authenticated) {
			ResponseUserDTO responseUserDTO = BankMapper.toResponseUserDTO(user);
			return ResponseEntity.ok(responseUserDTO);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
}
