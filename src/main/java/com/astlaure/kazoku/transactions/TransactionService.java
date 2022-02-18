package com.astlaure.kazoku.transactions;

import com.astlaure.kazoku.transactions.models.CreateTransactionDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return this.transactionRepository.findAll();
    }

    public List<Transaction> findAllBetween(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAllByTransactionDateBetween(startDate, endDate);
    }

    public Optional<Transaction> findById(Long id) {
        return this.transactionRepository.findById(id);
    }

    public Transaction create(CreateTransactionDto createTransaction) {
        Transaction transaction = Transaction.builder()
            .description(createTransaction.getDescription())
            .category(createTransaction.getCategory())
            .transactionDate(createTransaction.getTransactionDate())
            .total(createTransaction.getTotal())
            .build();

        transactionRepository.save(transaction);
        return transaction;
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
