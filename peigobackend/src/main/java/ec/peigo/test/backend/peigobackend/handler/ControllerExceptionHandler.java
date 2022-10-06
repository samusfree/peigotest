package ec.peigo.test.backend.peigobackend.handler;

import ec.peigo.test.backend.peigobackend.annotations.Generated;
import ec.peigo.test.backend.peigobackend.dto.ResponseDTO;
import ec.peigo.test.backend.peigobackend.exceptions.PeigoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Generated
public class ControllerExceptionHandler {
	@ExceptionHandler(PeigoException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseDTO<String> peigoException(Throwable ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseDTO<>(false, ex.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseDTO<String> resourceNotFoundException(Throwable ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseDTO<>(false, ex.getMessage());
	}
}
