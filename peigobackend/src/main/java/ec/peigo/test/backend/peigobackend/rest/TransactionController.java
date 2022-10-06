package ec.peigo.test.backend.peigobackend.rest;

import ec.peigo.test.backend.peigobackend.dto.ResponseDTO;
import ec.peigo.test.backend.peigobackend.dto.TransferDTO;
import ec.peigo.test.backend.peigobackend.dto.TransferResponseDTO;
import ec.peigo.test.backend.peigobackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;

	@PreAuthorize("hasAuthority('TRANSFER_CLIENT')")
	@PostMapping("/")
	public ResponseEntity<ResponseDTO<TransferResponseDTO>> saveAccount(@RequestBody TransferDTO transferDTO,
			Principal principal) {
		return ResponseEntity.ok(new ResponseDTO<>(true, transactionService.tranfer(principal.getName(), transferDTO)));
	}
}
