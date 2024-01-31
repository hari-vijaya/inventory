package com.codebasicz.inventory.repository;

import com.codebasicz.inventory.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StocksRepository extends JpaRepository<Stock, Integer> {
    Page<Stock> findAllByProductIdIn(List<Integer> productIds, Pageable pageable);
    Optional<Stock> findByProductId(Integer productId);
}
