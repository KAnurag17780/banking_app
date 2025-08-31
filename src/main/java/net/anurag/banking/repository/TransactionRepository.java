package net.anurag.banking.repository;

import net.anurag.banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long>
{
    // query method
    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);
}
