package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findFirstByIdTransactionContainingOrderByIdTransactionDesc(String idTransaction);
    Optional<Transaction> findFirstByIdTransaction(String idTransaction);
    Page<Transaction> findAllByOrderByCreatedAt(Pageable pageable);
}
