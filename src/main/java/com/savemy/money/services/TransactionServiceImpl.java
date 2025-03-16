package com.savemy.money.services;

import com.savemy.money.mappers.TransactionMapper;
import com.savemy.money.models.dtos.TransactionDTO;
import com.savemy.money.models.entities.Statistics;
import com.savemy.money.models.entities.Transaction;
import com.savemy.money.models.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    @Transactional
    public TransactionDTO save(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        if (transaction.getDate().isAfter(OffsetDateTime.now())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
        } else if (transaction.getValue() < 0) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
        }
        transactions.add(transaction);
        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    @Override
    public Page<TransactionDTO> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(transactionMapper::toDTO);
    }

    @Override
    @Transactional
    public TransactionDTO edit(TransactionDTO transactionDTO, UUID id) {
        Transaction entity = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transacao nao encontrada"));
        Transaction transaction = transactionMapper.toEntity(transactionDTO);

        transaction.setId(entity.getId());
        return transactionMapper.toDTO(transaction);

    }

    @Override
    @Transactional
    public void delete() {
        transactionRepository.deleteAll();
    }

    @Override
    public Statistics statisticsList() {

        List<Transaction> recentTransactions = transactions.stream().filter(t -> t.getDate().isBefore(OffsetDateTime.now().minusSeconds(60))).toList();
        if (recentTransactions.isEmpty()) {
            System.out.println("Lista não vazia?" + "recentes: "+recentTransactions + " todas: " + transactions);

            return Statistics.empty();
        }
        System.out.println("Lista não vazia?" + "recentes: "+recentTransactions + " todas: " + transactions);

        DoubleSummaryStatistics statistics = recentTransactions.stream().mapToDouble(Transaction::getValue).summaryStatistics();
        return new Statistics(statistics.getCount(), statistics.getSum(), statistics.getAverage(), statistics.getMin(), statistics.getMax());

    }
}
