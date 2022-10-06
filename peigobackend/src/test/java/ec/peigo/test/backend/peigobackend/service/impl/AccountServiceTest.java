package ec.peigo.test.backend.peigobackend.service.impl;

import ec.peigo.test.backend.peigobackend.dto.AccountDTO;
import ec.peigo.test.backend.peigobackend.entity.Account;
import ec.peigo.test.backend.peigobackend.exceptions.AccountAlreadyExists;
import ec.peigo.test.backend.peigobackend.exceptions.AccountDoesNotExistsException;
import ec.peigo.test.backend.peigobackend.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	@InjectMocks
	private AccountServiceImpl accountService;
	@Mock
	private AccountRepository accountRepository;
	private final String ACCOUNT_NUMBER = "22222";
	private final BigDecimal BALANCE_DEFAULT = BigDecimal.valueOf(10);

	@Test
	public void shouldSaveAccount() {
		final Account accountToSave = Account.builder().accountNumber(ACCOUNT_NUMBER).balance(BALANCE_DEFAULT).build();
		when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);
		when(accountRepository.save(any(Account.class))).thenReturn(accountToSave);
		final AccountDTO accountSaved = accountService.saveAccount(new AccountDTO(ACCOUNT_NUMBER, BALANCE_DEFAULT));
		assertThat(accountSaved).usingRecursiveComparison().isEqualTo(accountToSave);
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verify(accountRepository, times(1)).save(any(Account.class));
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void accountAlreadyExists() {
		final Account accountToSave = Account.builder().accountNumber(ACCOUNT_NUMBER).balance(BALANCE_DEFAULT).build();
		when(accountRepository.findByAccountNumber(anyString())).thenReturn(accountToSave);
		Assertions.assertThrows(AccountAlreadyExists.class,
				() -> accountService.saveAccount(new AccountDTO(ACCOUNT_NUMBER, BALANCE_DEFAULT)));
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void shouldUpdateAccount() {
		final Account accountToSearch = Account.builder()
				.accountNumber(ACCOUNT_NUMBER)
				.balance(BALANCE_DEFAULT)
				.build();
		when(accountRepository.findByAccountNumber(anyString())).thenReturn(accountToSearch);
		when(accountRepository.save(any(Account.class))).thenReturn(accountToSearch);

		final AccountDTO accountSaved = accountService.updateAccount(ACCOUNT_NUMBER,
				new AccountDTO(ACCOUNT_NUMBER, BigDecimal.valueOf(50)));

		assertThat(accountSaved.accountNumber()).isEqualTo(ACCOUNT_NUMBER);
		assertThat(accountSaved.balance()).isEqualByComparingTo(BigDecimal.valueOf(50));
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verify(accountRepository, times(1)).save(any(Account.class));
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void accountDoesNotExists() {
		when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);
		Assertions.assertThrows(AccountDoesNotExistsException.class,
				() -> accountService.updateAccount(ACCOUNT_NUMBER, new AccountDTO(ACCOUNT_NUMBER, BALANCE_DEFAULT)));
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void shouldFindAccount() {
		final Account accountToSearch = Account.builder()
				.accountNumber(ACCOUNT_NUMBER)
				.balance(BALANCE_DEFAULT)
				.build();
		when(accountRepository.findByAccountNumber(anyString())).thenReturn(accountToSearch);

		final AccountDTO accountFind = accountService.findAccount(ACCOUNT_NUMBER);

		assertThat(accountFind).usingRecursiveComparison().isEqualTo(accountToSearch);
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}

	@Test
	public void accountDoesNotExistsFind() {
		when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);
		Assertions.assertThrows(AccountDoesNotExistsException.class, () -> accountService.findAccount(ACCOUNT_NUMBER));
		verify(accountRepository, times(1)).findByAccountNumber(anyString());
		verifyNoMoreInteractions(accountRepository);
	}
}
