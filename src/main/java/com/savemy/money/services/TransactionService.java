package com.savemy.money.services;

import com.savemy.money.models.dtos.TransactionDTO;
import com.savemy.money.models.entities.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
    TransactionDTO save(TransactionDTO transactionDTO);
    Page<TransactionDTO> findAll(Pageable pageable);
    TransactionDTO edit(TransactionDTO transactionDTO, UUID id);
    void delete();
    Statistics statisticsList();
}
