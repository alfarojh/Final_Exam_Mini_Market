package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Query untuk mendapatkan informasi barang yang belum dihapus berdasarkan ID Barang.
    Optional<Item> findFirstByIsDeletedIsFalseAndIdItem(String idItem);

    // Query untuk mendapatkan informasi barang berdasarkan ID Barang.
    // Digunakan untuk mendapatkan ID Barang terbaru.
    Optional<Item> findFirstByIdItemContainingOrderByIdItemDesc(String idItem);

    // Query untuk mendapatkan daftar 3 barang yang belum dihapus dan sering dibeli.
    List<Item> findTop3ByIsDeletedIsFalseOrderByQuantityPurchasedDesc();

    // Query untuk mendapatkan daftar barang yang belum dihapus dengan urutan nama barang.
    Page<Item> findAllByIsDeletedIsFalseOrderByName(Pageable pageable);

    // Query untuk mendapatkan daftar barang yang belum dihapus dan berdasarkan nama barang dengan urutan nama barang.
    Page<Item> findAllByIsDeletedIsFalseAndNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);

    // Query untuk mendapatkan daftar barang yang belum dihapus dan berdasarkan rentang harga dengan urutan nama barang.
    Page<Item> findAllByIsDeletedIsFalseAndPriceBetweenOrderByName(Integer startPrice, Integer endPrice, Pageable pageable);
}
