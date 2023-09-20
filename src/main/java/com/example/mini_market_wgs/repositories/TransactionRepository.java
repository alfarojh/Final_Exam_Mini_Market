package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Query untuk mendapatkan informasi transaksi berdasarkan ID Transaksi.
    // Digunakan untuk mendapatkan ID Transaksi terbaru.
    Optional<Transaction> findFirstByIdTransactionContainingOrderByIdTransactionDesc(String idTransaction);

    // Query untuk mendapatkan informasi transaksi berdasarkan ID Transaksi.
    Optional<Transaction> findFirstByIdTransaction(String idTransaction);

    // Query untuk mendapatkan daftar transaksi dengan urutan transaksi terbaru.
    Page<Transaction> findAllByOrderByTransactionDateDesc(Pageable pageable);

    // Query untuk mendapatkan daftar transaksi berdasarkan rentang tanggal dengan urutan transaksi terbaru.
    Page<Transaction> findAllByTransactionDateBetweenOrderByTransactionDateDesc(Date startDate, Date endDate, Pageable pageable);

    // Query untuk mendapatkan daftar transaksi berdasarkan rentang tanggal dan ID Customer dengan urutan transaksi terbaru.
    Page<Transaction> findAllByTransactionDateBetweenAndCustomer_IdCustomerOrderByTransactionDateDesc(Date startDate, Date endDate, String idCustomer, Pageable pageable);
}
