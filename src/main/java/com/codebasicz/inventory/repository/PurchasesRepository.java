package com.codebasicz.inventory.repository;

import com.codebasicz.inventory.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchase, Integer> {

    Page<Purchase> findAllByProductIdInOrSupplierIdIn(List<Integer> productIds, List<Integer> supplierIds, Pageable pageable);

}
