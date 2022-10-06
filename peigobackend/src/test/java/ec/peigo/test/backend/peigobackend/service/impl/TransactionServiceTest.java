package ec.peigo.test.backend.peigobackend.service.impl;

import ec.peigo.test.backend.peigobackend.dto.TransferDTO;
import ec.peigo.test.backend.peigobackend.dto.TransferResponseDTO;
import ec.peigo.test.backend.peigobackend.entity.Account;
import ec.peigo.test.backend.peigobackend.entity.Transaction;
import ec.peigo.test.backend.peigobackend.enums.TransactionStatus;
import ec.peigo.test.backend.peigobackend.exceptions.AccountDoesNotExistsException;
import ec.peigo.test.backend.peigobackend.exceptions.AccountNotEnoughBalanceException;
import ec.peigo.test.backend.peigobackend.repository.AccountRepository;
import ec.peigo.test.backend.peigobackend.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	@InjectMocks
	private TransactionServiceImpl transactionService;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private TransactionRepository transactionRepository;

	private final String ORIGIN_ACCOUNT_NUMBER = "22222";
	private final String DESTINATION_ACCOUNT_NUMBER = "55555";
	private final BigDecimal BALANCE_ORIGIN = BigDecimal.valueOf(20000);
	private final BigDecimal BALANCE_DESTINATION = BigDecimal.valueOf(30000);
	private final BigDecimal AMOUNT_TEST = BigDecimal.valueOf(314);
	private final String USER_NAME = "Test";
	private final Long TRANSACTION_ID = 1L;

	@Test
	public void originAccountDoesNotExists() {
		TransferDTO transferDTO = new TransferDTO(ORIGIN_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, AMOUNT_TEST);
		when(accountRepository.findByAccountNumber(ORIGIN_ACCOUNT_NUMBER)).thenReturn(null);
		Assertions.assertThrows(AccountDoesNotExistsException.class,
				() -> transactionService.tranfer(USER_NAME, transferDTO));
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void originAccountNotEnoughBalance() {
		TransferDTO transferDTO = new TransferDTO(ORIGIN_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, AMOUNT_TEST);
		when(accountRepository.findByAccountNumber(ORIGIN_ACCOUNT_NUMBER)).thenReturn(
				Account.builder().accountNumber(ORIGIN_ACCOUNT_NUMBER).balance(BigDecimal.ZERO).build());
		Assertions.assertThrows(AccountNotEnoughBalanceException.class,
				() -> transactionService.tranfer(USER_NAME, transferDTO));
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void destinationAccountDoesNotExists() {
		TransferDTO transferDTO = new TransferDTO(ORIGIN_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, AMOUNT_TEST);
		when(accountRepository.findByAccountNumber(ORIGIN_ACCOUNT_NUMBER)).thenReturn(
				Account.builder().accountNumber(ORIGIN_ACCOUNT_NUMBER).balance(BALANCE_ORIGIN).build());
		when(accountRepository.findByAccountNumber(DESTINATION_ACCOUNT_NUMBER)).thenReturn(null);
		Assertions.assertThrows(AccountDoesNotExistsException.class,
				() -> transactionService.tranfer(USER_NAME, transferDTO));
		verify(accountRepository, times(2)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void shouldTransfer() {
		TransferDTO transferDTO = new TransferDTO(ORIGIN_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, AMOUNT_TEST);
		Transaction transactionCreated = Transaction.builder()
				.username(USER_NAME)
				.status(TransactionStatus.CREATED)
				.destinationAccountNumber(DESTINATION_ACCOUNT_NUMBER)
				.originAccountNumber(ORIGIN_ACCOUNT_NUMBER)
				.id(TRANSACTION_ID)
				.amount(AMOUNT_TEST)
				.originAccountBalanceBeforeTransaction(BALANCE_ORIGIN)
				.originAccountBalanceAfterTransaction(BALANCE_ORIGIN.subtract(AMOUNT_TEST))
				.destinationAccountBalanceBeforeTransaction(BALANCE_DESTINATION)
				.destinationAccountBalanceAfterTransaction(BALANCE_DESTINATION.add(AMOUNT_TEST))
				.build();
		when(accountRepository.findByAccountNumber(ORIGIN_ACCOUNT_NUMBER)).thenReturn(
				Account.builder().accountNumber(ORIGIN_ACCOUNT_NUMBER).balance(BALANCE_ORIGIN).build());
		when(accountRepository.findByAccountNumber(DESTINATION_ACCOUNT_NUMBER)).thenReturn(
				Account.builder().accountNumber(DESTINATION_ACCOUNT_NUMBER).balance(BALANCE_DESTINATION).build());
		when(transactionRepository.save(any())).thenReturn(transactionCreated);
		when(transactionRepository.save(any())).thenReturn(transactionCreated);
		when(accountRepository.save(any())).thenReturn(Account.builder()
				.accountNumber(ORIGIN_ACCOUNT_NUMBER)
				.balance(transactionCreated.getOriginAccountBalanceAfterTransaction())
				.build());
		when(accountRepository.save(any())).thenReturn(Account.builder()
				.accountNumber(DESTINATION_ACCOUNT_NUMBER)
				.balance(transactionCreated.getDestinationAccountBalanceAfterTransaction())
				.build());
		final TransferResponseDTO transferResponseDTO = transactionService.tranfer(USER_NAME, transferDTO);
		assertThat(transferResponseDTO.originAccountNumber()).isEqualTo(transactionCreated.getOriginAccountNumber());
		assertThat(transferResponseDTO.destinationAccountNumber()).isEqualTo(transactionCreated.getDestinationAccountNumber());
		assertThat(transferResponseDTO.balanceDestinationAccount()).isEqualByComparingTo(transactionCreated.getDestinationAccountBalanceAfterTransaction());
		assertThat(transferResponseDTO.balanceOriginAccount()).isEqualByComparingTo(transactionCreated.getOriginAccountBalanceAfterTransaction());
		verify(accountRepository, times(2)).findByAccountNumber(anyString());
		verify(transactionRepository, times(2)).save(any());
		verify(accountRepository, times(2)).save(any());
		verifyNoMoreInteractions(accountRepository);
	}
}
