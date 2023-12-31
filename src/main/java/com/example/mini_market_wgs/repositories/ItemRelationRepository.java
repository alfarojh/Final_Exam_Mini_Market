package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Item;
import com.example.mini_market_wgs.models.ItemRelational;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRelationRepository extends JpaRepository<ItemRelational, Long> {
    // Query untuk mendapatkan daftar 3 barang yang sering dibeli bersamaan.
    List<ItemRelational> findTop3ByOrderByCountDesc();

    // Query untuk mendapatkan daftar 3 barang berdasarkan ID barang.
    List<ItemRelational> findTop3ByItem1_IdItemOrItem2_IdItemOrderByCountDesc(String idItem1, String idItem2);

    // Query untuk mendapatkan informasi relasi antar barang.
    Optional<ItemRelational> findFirstByItem1AndItem2(Item item1, Item item2);
}
