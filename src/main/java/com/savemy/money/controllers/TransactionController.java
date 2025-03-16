package com.savemy.money.controllers;

import com.savemy.money.models.dtos.TransactionDTO;
import com.savemy.money.models.entities.Statistics;
import com.savemy.money.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transações")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> save(@Valid @RequestBody TransactionDTO transactionDTO){
        transactionService.save(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> findAll(@PageableDefault(page = 0, size = 15) Pageable pageable){
        return ResponseEntity.ok(transactionService.findAll(pageable));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> stats(){
        return ResponseEntity.ok(transactionService.statisticsList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> edit(@Valid @RequestBody TransactionDTO transactionDTO, @PathVariable UUID id){
        return ResponseEntity.ok(transactionService.edit(transactionDTO, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(){
        transactionService.delete();
    }
}
