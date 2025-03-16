package com.savemy.money.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @NotNull(message = "o valor não pode ser nulo")
    @Min(0)
    private double value;

    @NotNull(message = "A data nao pode ser nula")
    private OffsetDateTime date;
}
