package ec.peigo.test.backend.peigobackend.exceptions;

import ec.peigo.test.backend.peigobackend.annotations.Generated;

import java.io.Serial;

@Generated
public class PeigoException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 3751035176898017459L;

	public PeigoException(String s) {
		super(s);
	}
}
