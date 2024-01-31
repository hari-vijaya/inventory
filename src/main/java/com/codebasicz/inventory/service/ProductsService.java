package com.codebasicz.inventory.service;

import com.codebasicz.inventory.entity.Product;
import com.codebasicz.inventory.exception.custom.ResourceNotFoundException;
import com.codebasicz.inventory.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public Page<Product> getProducts(String search, Pageable pageable) {
        if (StringUtils.hasText(search)) {
            return productsRepository.getProductsByNameIsLikeOrCategoryIsLike(search, pageable);
        } else {
            return productsRepository.findAll(pageable);
        }
    }

    public Product getProductById(Integer id) {
        if (Objects.nonNull(id)) {
            return productsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        } else {
            return new Product();
        }
    }

    public void saveProduct(Product product) {
        productsRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productsRepository.deleteById(id);
    }

    public void saveAllProducts(List<Product> products) {
        productsRepository.saveAll(products);
    }

}
