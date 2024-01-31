package com.codebasicz.inventory.repository;

import com.codebasicz.inventory.entity.Product;
import com.codebasicz.inventory.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {
    @Query("select s from Supplier s where s.supplierName like %:search% or s.contactName like %:search% or s.phone like %:search% or s.email like %:search% ")
    Page<Supplier> getSuppliersBySupplierNameLikeOrContactNameLikeOrEmailLikeOrPhoneLike(@Param("search") String search, Pageable pageable);

    List<Supplier> findAllBySupplierNameContains(String name);

}
