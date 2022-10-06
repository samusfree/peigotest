package ec.peigo.test.backend.peigobackend.service;

import ec.peigo.test.backend.peigobackend.dto.AccountDTO;

public interface AccountService {
	AccountDTO saveAccount(AccountDTO accountDTO);

	AccountDTO updateAccount(String accountNumber, AccountDTO accountDTO);

	AccountDTO findAccount(String accountNumber);
}
