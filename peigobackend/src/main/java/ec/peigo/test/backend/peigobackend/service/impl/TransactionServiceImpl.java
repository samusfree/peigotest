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
import ec.peigo.test.backend.peigobackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	@Override
	public TransferResponseDTO tranfer(String userName, TransferDTO transferDTO) {
		Account originAccount = accountRepository.findByAccountNumber(transferDTO.originAccountNumber());
		if (Objects.isNull(originAccount)) {
			throw new AccountDoesNotExistsException(transferDTO.originAccountNumber());
		}
		if (originAccount.getBalance().compareTo(transferDTO.amount()) < 0) {
			throw new AccountNotEnoughBalanceException(transferDTO.originAccountNumber());
		}
		Account destinationAccount = accountRepository.findByAccountNumber(transferDTO.destinationAccountNumber());
		if (Objects.isNull(destinationAccount)) {
			throw new AccountDoesNotExistsException(transferDTO.destinationAccountNumber());
		}
		Transaction transaction = Transaction.builder()
				.amount(transferDTO.amount())
				.originAccountNumber(transferDTO.originAccountNumber())
				.destinationAccountNumber(transferDTO.destinationAccountNumber())
				.originAccountBalanceBeforeTransaction(originAccount.getBalance())
				.originAccountBalanceAfterTransaction(originAccount.getBalance().subtract(transferDTO.amount()))
				.destinationAccountBalanceBeforeTransaction(destinationAccount.getBalance())
				.destinationAccountBalanceAfterTransaction(destinationAccount.getBalance().add(transferDTO.amount()))
				.status(TransactionStatus.CREATED)
				.username(userName)
				.build();
		transaction = transactionRepository.save(transaction);
		originAccount.setBalance(originAccount.getBalance().subtract(transferDTO.amount()));
		destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.amount()));
		accountRepository.save(originAccount);
		accountRepository.save(destinationAccount);
		transaction.setStatus(TransactionStatus.TRANSFERED);
		transactionRepository.save(transaction);
		return new TransferResponseDTO(transaction.getId(), originAccount.getAccountNumber(),
				destinationAccount.getAccountNumber(), originAccount.getBalance(), destinationAccount.getBalance());
	}
}
