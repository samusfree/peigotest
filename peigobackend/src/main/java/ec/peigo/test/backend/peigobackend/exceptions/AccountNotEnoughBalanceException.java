package ec.peigo.test.backend.peigobackend.exceptions;

import ec.peigo.test.backend.peigobackend.annotations.Generated;

import java.io.Serial;

@Generated
public class AccountNotEnoughBalanceException extends PeigoException {
	@Serial
	private static final long serialVersionUID = 9183614487082406726L;

	public AccountNotEnoughBalanceException(String accountNumber) {
		super("Account " + accountNumber + "doesn't have enough balance to transfer");
	}
}
