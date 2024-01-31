package com.codebasicz.inventory.repository;

import com.codebasicz.inventory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.name like %:search% or p.category like %:search%")
    Page<Product> getProductsByNameIsLikeOrCategoryIsLike(@Param("search") String search, Pageable pageable);

    List<Product> findAllByNameContains(String name);
}
