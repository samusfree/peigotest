package ec.peigo.test.backend.peigobackend.entity;

import ec.peigo.test.backend.peigobackend.annotations.Generated;
import ec.peigo.test.backend.peigobackend.enums.TransactionStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column(name = "origin_account_number")
	private String originAccountNumber;
	@Column(name = "destination_account_number")
	private String destinationAccountNumber;
	@Column(name = "origin_account_balance_before_transaction")
	private BigDecimal originAccountBalanceBeforeTransaction;
	@Column(name = "destination_account_balance_before_transaction")
	private BigDecimal destinationAccountBalanceBeforeTransaction;
	@Column(name = "origin_account_balance_after_transaction")
	private BigDecimal originAccountBalanceAfterTransaction;
	@Column(name = "destination_account_balance_after_transaction")
	private BigDecimal destinationAccountBalanceAfterTransaction;
	@Column
	private BigDecimal amount;
	@Column
	@Enumerated(EnumType.STRING)
	TransactionStatus status;
}