package ec.peigo.test.backend.peigobackend.rest;

import ec.peigo.test.backend.peigobackend.dto.AccountDTO;
import ec.peigo.test.backend.peigobackend.dto.ResponseDTO;
import ec.peigo.test.backend.peigobackend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountsController {
	private final AccountService accountService;

	@PreAuthorize("hasAuthority('ACCOUNT_MASTER')")
	@PostMapping("/")
	public ResponseEntity<ResponseDTO<AccountDTO>> saveAccount(@RequestBody AccountDTO accountDTO) {
		return ResponseEntity.ok(new ResponseDTO<>(true, accountService.saveAccount(accountDTO)));
	}

	@PreAuthorize("hasAuthority('ACCOUNT_MASTER')")
	@PutMapping("/{accountNumber}")
	public ResponseEntity<ResponseDTO<AccountDTO>> updateAccount(@PathVariable String accountNumber,
			@RequestBody AccountDTO accountDTO) {
		return ResponseEntity.ok(new ResponseDTO<>(true, accountService.updateAccount(accountNumber, accountDTO)));
	}

	@PreAuthorize("hasAuthority('ACCOUNT_MASTER')")
	@GetMapping("/{accountNumber}")
	public ResponseEntity<ResponseDTO<AccountDTO>> findAccount(@PathVariable String accountNumber) {
		return ResponseEntity.ok(new ResponseDTO<>(true, accountService.findAccount(accountNumber)));
	}
}
