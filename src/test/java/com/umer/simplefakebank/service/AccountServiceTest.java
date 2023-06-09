package com.umer.simplefakebank.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.umer.simplefakebank.dto.request.RequestAccountDTO;
import com.umer.simplefakebank.dto.response.ResponseAccountBalanceDTO;
import com.umer.simplefakebank.dto.response.ResponseAccountDTO;
import com.umer.simplefakebank.entities.Account;
import com.umer.simplefakebank.entities.User;
import com.umer.simplefakebank.exception.AccountNotFoundException;
import com.umer.simplefakebank.exception.InsufficientBalanceException;
import com.umer.simplefakebank.exception.InvalidRequestAccountException;
import com.umer.simplefakebank.exception.TransferNotAllowedException;
import com.umer.simplefakebank.exception.UserNotFoundException;
import com.umer.simplefakebank.repsitory.AccountReposoitory;
import com.umer.simplefakebank.repsitory.UserRepository;
import com.umer.simplefakebank.service.mapper.BankMapper;


@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

	@Mock
	private AccountReposoitory accountReposoitory;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BankMapper bankMapper;

	@Spy
	@InjectMocks
	private AccountService accountService;

//	@Test
	void testCreateNewAccountSuccessfully() {

		Long userId = 1L;
		String username = "Username";
		Long accountId = 2L;
		BigDecimal initialAmount = BigDecimal.valueOf(10.01);

		RequestAccountDTO requestAccountDTO = RequestAccountDTO.builder()
				.userId(userId)
				.initailDepositAmount(initialAmount)
				.build();

		User user = User.builder()
				.username(username)
				.id(userId)
				.email("email@email.em")
				.password("asfasdf")
				.build();

		Account accountWithId =  Account.builder()
				.id(accountId)
                .initialDepositAmount(initialAmount)
                .creationTimestamp(LocalDateTime.MIN)
                .balance(initialAmount)
                .user(user)
                .build();

		Account accountWithoutId = Account.builder()
                .initialDepositAmount(initialAmount)
                .creationTimestamp(LocalDateTime.MIN)
                .balance(initialAmount)
                .user(user)
                .build();

		ResponseAccountDTO expectedResponseAccountDTO = ResponseAccountDTO.builder()
				.userId(userId)
                .id(accountId)
                .balance(initialAmount)
                .creationTimestamp(LocalDateTime.MIN)
                .build();

		when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
		when(accountReposoitory.save(accountWithoutId)).thenReturn(accountWithId);
		doReturn(LocalDateTime.MAX).when(accountService).getCurrentTimestamp();
		when(bankMapper.toResponseAccountDTO(accountWithId)).thenReturn(expectedResponseAccountDTO);
//		doReturn(expectedResponseAccountDTO).when(bankMapper).toResponseAccountDTO(accountWithId);

		ResponseAccountDTO actualResponseAccountDTO = accountService.createNewAccount(requestAccountDTO);
		Assertions.assertThat(actualResponseAccountDTO).isEqualTo(expectedResponseAccountDTO);

	}
	
	@Test
	void testCreateNewAccountWithNullRequest() {
		Throwable throwable = Assertions.catchThrowable(() -> accountService.createNewAccount(null));
		Assertions.assertThat(throwable).isInstanceOf(InvalidRequestAccountException.class);
	}
	
	@Test
	void testRequestAccountNotFoundForUserId() {
		Long someId = 1L;
		RequestAccountDTO requestAccountDTO=RequestAccountDTO.builder()
				.userId(someId)
				.build();
		when(userRepository.findById(someId)).thenReturn(Optional.empty());
		Throwable throwable = Assertions.catchThrowable(() -> accountService.createNewAccount(requestAccountDTO));
		Assertions.assertThat(throwable).isInstanceOf(UserNotFoundException.class);
	}
	
	@Test
	void testGetAccountnotFound() {
		Long someId=1L;
		when(userRepository.findById(someId)).thenReturn(Optional.empty());
		Throwable throwable=Assertions.catchThrowable(() -> accountService.getAccountById(someId));
		Assertions.assertThat(throwable).isInstanceOf(AccountNotFoundException.class);
	}
	
	@Test
	void testSuccessfulTransfer() {
		Account sender=Account.builder()
				.id(1L)
				.balance(BigDecimal.valueOf(10.00))
				.build();
		
		Account reciever=Account.builder()
				.id(2L)
				.balance(BigDecimal.valueOf(5.00))
				.build();
		accountService.transfer(sender, reciever, BigDecimal.valueOf(0.50));
		Assertions.assertThat(sender.getBalance()).isEqualTo("9.5");
		Assertions.assertThat(reciever.getBalance()).isEqualTo("5.5");
	}
	
	@Test
	void testUnsuccessfulTransfer() {
		Account sender=Account.builder()
				.id(1L)
				.balance(BigDecimal.valueOf(00.00))
				.build();
		
		Account reciever=Account.builder()
				.id(2L)
				.balance(BigDecimal.valueOf(5.00))
				.build();
		Throwable throwable = Assertions.catchThrowable(() ->accountService.transfer(sender, reciever, BigDecimal.valueOf(0.01)));
		Assertions.assertThat(throwable).isInstanceOf(InsufficientBalanceException.class);
		Assertions.assertThat(reciever.getBalance()).isEqualTo("5.0");
	}
	
	@Test
	void testTransferToSameAccountNotAllowed() {
		Account sender=Account.builder()
				.id(2L)
				.balance(BigDecimal.valueOf(5.00))
				.build();
		Throwable throwable=Assertions.catchThrowable(()->accountService.transfer(sender, sender, BigDecimal.valueOf(1.01)));
		Assertions.assertThat(throwable).isInstanceOf(TransferNotAllowedException.class);
	}


}
