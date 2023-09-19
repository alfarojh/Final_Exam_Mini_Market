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
    Optional<Item> findFirstByIsDeletedIsFalseAndIdItem(String idItem);
    Optional<Item> findFirstByIdItemContainingOrderByIdItemDesc(String idItem);
    List<Item> findTop3ByOrderByQuantityPurchasedDesc();
    Page<Item> findAllByIsDeletedIsFalseOrderByName(Pageable pageable);
}
