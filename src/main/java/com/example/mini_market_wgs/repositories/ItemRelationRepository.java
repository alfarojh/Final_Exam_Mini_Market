package com.example.mini_market_wgs.repositories;

import com.example.mini_market_wgs.models.Item;
import com.example.mini_market_wgs.models.ItemRelational;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRelationRepository extends JpaRepository<ItemRelational, Long> {
    List<ItemRelational> findTop3ByOrderByCountDesc();
    Optional<ItemRelational> findFirstByItem1AndItem2(Item item1, Item item2);
}
