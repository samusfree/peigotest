package ec.peigo.test.backend.peigobackend.entity;

import ec.peigo.test.backend.peigobackend.annotations.Generated;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "account_number", unique = true)
	private String accountNumber;
	@Column
	private BigDecimal balance;
}