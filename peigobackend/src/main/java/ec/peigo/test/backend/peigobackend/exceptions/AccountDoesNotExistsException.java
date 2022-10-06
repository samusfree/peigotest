package ec.peigo.test.backend.peigobackend.exceptions;

import ec.peigo.test.backend.peigobackend.annotations.Generated;

import java.io.Serial;
@Generated
public class AccountDoesNotExistsException extends PeigoException {
	@Serial
	private static final long serialVersionUID = 674667890306040381L;

	public AccountDoesNotExistsException(String accountNumber) {
		super("Account doesn't exists " + accountNumber);
	}
}
