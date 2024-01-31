package com.codebasicz.inventory.service;

import com.codebasicz.inventory.entity.Product;
import com.codebasicz.inventory.entity.Stock;
import com.codebasicz.inventory.exception.custom.ResourceNotFoundException;
import com.codebasicz.inventory.model.StockResponse;
import com.codebasicz.inventory.repository.ProductsRepository;
import com.codebasicz.inventory.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StocksService {
    private final StocksRepository stocksRepository;
    private final ProductsRepository productsRepository;

    public Page<StockResponse> getStocks(String search, Pageable pageable) {
        List<StockResponse> stocksResponse = new ArrayList<>();
        Page<Stock> stockPage;
        if (StringUtils.hasText(search)) {
            List<Product> products = productsRepository.findAllByNameContains(search);
            List<Integer> productIds = products.stream().map(Product::getId).toList();
            stockPage = stocksRepository.findAllByProductIdIn(productIds, pageable);
        } else {
            stockPage = stocksRepository.findAll(pageable);
        }
        stockPage.getContent().forEach(stock -> {
            Product product = productsRepository.findById(stock.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
            StockResponse stockResponse = new StockResponse(product.getName(), stock.getQuantity(), stock.getLastStockUpdate());
            stocksResponse.add(stockResponse);
        });
        return new PageImpl<>(stocksResponse, pageable, stockPage.getTotalElements());

    }
}
