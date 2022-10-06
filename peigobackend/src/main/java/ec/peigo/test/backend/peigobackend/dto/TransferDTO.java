package ec.peigo.test.backend.peigobackend.dto;

import ec.peigo.test.backend.peigobackend.annotations.Generated;

import java.math.BigDecimal;

@Generated
public record TransferDTO(String originAccountNumber, String destinationAccountNumber, BigDecimal amount) {
}
