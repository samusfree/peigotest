package ec.peigo.test.backend.peigobackend.service;

import ec.peigo.test.backend.peigobackend.dto.TransferDTO;
import ec.peigo.test.backend.peigobackend.dto.TransferResponseDTO;

public interface TransactionService {
	TransferResponseDTO tranfer(String userName, TransferDTO transferDTO);
}
