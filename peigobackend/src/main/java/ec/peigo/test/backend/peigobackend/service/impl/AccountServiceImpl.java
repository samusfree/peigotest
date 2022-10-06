package ec.peigo.test.backend.peigobackend.service.impl;

import ec.peigo.test.backend.peigobackend.dto.AccountDTO;
import ec.peigo.test.backend.peigobackend.entity.Account;
import ec.peigo.test.backend.peigobackend.exceptions.AccountAlreadyExists;
import ec.peigo.test.backend.peigobackend.exceptions.AccountDoesNotExistsException;
import ec.peigo.test.backend.peigobackend.repository.AccountRepository;
import ec.peigo.test.backend.peigobackend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;

	@Override
	public AccountDTO saveAccount(AccountDTO accountDTO) {
		Account accountEntity = accountRepository.findByAccountNumber(accountDTO.accountNumber());
		if (!Objects.isNull(accountEntity)) {
			throw new AccountAlreadyExists(accountDTO.accountNumber());
		}

		Account account = Account.builder()
				.accountNumber(accountDTO.accountNumber())
				.balance(accountDTO.balance())
				.build();
		accountRepository.save(account);
		return accountDTO;
	}

	@Override
	public AccountDTO updateAccount(String accountNumber, AccountDTO accountDTO) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (Objects.isNull(account)) {
			throw new AccountDoesNotExistsException(accountNumber);
		}
		account.setBalance(accountDTO.balance());
		accountRepository.save(account);
		return new AccountDTO(account.getAccountNumber(), account.getBalance());
	}

	@Override
	public AccountDTO findAccount(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (Objects.isNull(account)) {
			throw new AccountDoesNotExistsException(accountNumber);
		}
		return new AccountDTO(account.getAccountNumber(), account.getBalance());
	}
}
