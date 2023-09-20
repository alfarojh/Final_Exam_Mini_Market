package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Cashier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashierRepository extends JpaRepository<Cashier, Long> {
    // Query untuk mendapatkan informasi kasir yang belum dihapus berdasarkan ID Kasir.
    Optional<Cashier> findFirstByIsDeletedIsFalseAndIdCashier(String idCashier);

    // Query untuk mendapatkan informasi kasir dengan urutan ID Kasir, dimulai urutan terbawah.
    // Digunakan untuk mendapatkan ID Kasir terbaru.
    Optional<Cashier> findFirstByOrderByIdCashierDesc();

    // Query untuk mendapatkan daftar kasir yang belum dihapus dengan urutan nama kasir.
    Page<Cashier> findAllByIsDeletedIsFalseOrderByName(Pageable pageable);

    // Query untuk mendapatkan daftar kasir yang belum dihapus berdasarkan nama kasir dengan urutan nama kasir.
    Page<Cashier> findAllByIsDeletedIsFalseAndNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
