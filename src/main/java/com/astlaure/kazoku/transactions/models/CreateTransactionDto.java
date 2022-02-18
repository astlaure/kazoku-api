package com.astlaure.kazoku.transactions.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionDto {
    @NotNull
    private String description;

    @NotNull
    private String category;

    @NotNull
    private LocalDate transactionDate;

    @Min(0)
    private int total;
}
