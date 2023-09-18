package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Cashier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CashierRepository extends JpaRepository<Cashier, Long> {
    Optional<Cashier> findFirstByIsDeletedIsFalseAndIdCashier(String idCashier);

    Page<Cashier> findAllByIsDeletedIsFalseOrderByName(Pageable pageable);
}
