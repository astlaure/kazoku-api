package com.astlaure.kazoku.transactions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
