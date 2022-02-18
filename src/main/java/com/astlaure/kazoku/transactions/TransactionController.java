package com.astlaure.kazoku.transactions;

import com.astlaure.kazoku.transactions.models.CreateTransactionDto;
import com.astlaure.kazoku.transactions.models.TransactionDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Transaction> transactions = transactionService.findAllBetween(startDate, endDate);
        return ResponseEntity.status(200).body(transactions.stream()
            .map(transaction -> TransactionDto.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .category(transaction.getCategory())
                .transactionDate(transaction.getTransactionDate())
                .total(transaction.getTotal())
                .build())
            .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<?> postTransaction(@Valid @RequestBody CreateTransactionDto createTransaction) {
        Transaction transaction = transactionService.create(createTransaction);
        return ResponseEntity.status(201).body(TransactionDto.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .category(transaction.getCategory())
                .total(transaction.getTotal())
            .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") Long id) {
        transactionService.delete(id);
        return ResponseEntity.status(200).build();
    }
}
