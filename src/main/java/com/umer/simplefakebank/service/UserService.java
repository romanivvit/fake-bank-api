package com.umer.simplefakebank.service;

import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.umer.simplefakebank.dto.request.CreateUserDTO;
import com.umer.simplefakebank.dto.response.ResponseUserDTO;
import com.umer.simplefakebank.entities.User;
import com.umer.simplefakebank.repsitory.UserRepository;
import com.umer.simplefakebank.service.mapper.BankMapper;
import com.umer.simplefakebank.exception.UserException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public ResponseUserDTO createNewUser(@Valid CreateUserDTO createUserDTO) {
		log.debug("Creating a new User - {}", createUserDTO);

		// TODO: Throw error using a generic util class.
		if (Objects.isNull(createUserDTO)) {
			// Throw an exception
			throw new UserException();
		}
		User userInDB = userRepository.findByEmail(createUserDTO.getEmail());
		log.debug("User in DB - {}", userInDB);
		if(userInDB != null){
			throw new UserException();
		}

		User user = BankMapper.toUserEntity(createUserDTO);

		user = userRepository.save(user);

		log.debug("Created User - {}", user);
		return BankMapper.toResponseUserDTO(user);
	}

	public ResponseUserDTO getUserById(Long id) {
		log.debug("Getting User by Id - {}", id);

		Optional<User> userOptional = userRepository.findById(id);

		if (!userOptional.isPresent()) {
			// Throw an exception
			throw new UserException();
		}

		log.debug("Retrieved User - {}", userOptional.get());
		return BankMapper.toResponseUserDTO(userOptional.get());
	}

	public boolean authenticateUser(String email, String password) {
		log.debug("Login UserName - {}", email);
		log.debug("Login UserPassword - {}", password);
		User user = userRepository.findByEmail(email);
		log.debug("Login FoundedUser - {}", user);
		if (user != null && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}
}
