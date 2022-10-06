package ec.peigo.test.backend.peigobackend.exceptions;

import java.io.Serial;

public class AccountAlreadyExists extends PeigoException {

	@Serial
	private static final long serialVersionUID = -8251664791626044092L;

	public AccountAlreadyExists(String accountNumber) {
		super("Account already exists " + accountNumber);
	}
}
