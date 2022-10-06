package ec.peigo.test.backend.peigobackend.repository;

import ec.peigo.test.backend.peigobackend.annotations.Generated;
import ec.peigo.test.backend.peigobackend.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Generated
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}

