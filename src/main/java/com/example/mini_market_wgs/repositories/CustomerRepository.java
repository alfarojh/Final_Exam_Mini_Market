package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Cashier;
import com.example.mini_market_wgs.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findFirstByIsDeletedIsFalseAndIdCustomer(String idCustomer);
    Optional<Customer> findFirstByIdCustomerContainingOrderByIdCustomerDesc(String idCustomer);
    Page<Customer> findAllByIsDeletedIsFalseOrderByName(Pageable pageable);
}
