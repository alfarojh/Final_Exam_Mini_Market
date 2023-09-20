package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Query untuk mendapatkan informasi customer yang belum dihapus berdasarkan ID Customer.
    Optional<Customer> findFirstByIsDeletedIsFalseAndIdCustomer(String idCustomer);

    // Query untuk mendapatkan informasi customer yang belum dihapus berdasarkan ID Customer
    // dengan urutan ID Customer, dimulai urutan terbawah.
    // Digunakan untuk mendapatkan ID Customer terbaru.
    Optional<Customer> findFirstByIdCustomerContainingOrderByIdCustomerDesc(String idCustomer);

    // Query untuk mendapatkan daftar customer yang belum dihapus dengan urutan nama customer.
    Page<Customer> findAllByIsDeletedIsFalseOrderByName(Pageable pageable);

    // Query untuk mendapatkan daftar customer yang belum dihapus dan berdasarkan nama customer dengan urutan nama customer.
    Page<Customer> findAllByIsDeletedIsFalseAndNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
