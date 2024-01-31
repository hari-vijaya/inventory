package com.codebasicz.inventory.service;

import com.codebasicz.inventory.entity.Product;
import com.codebasicz.inventory.entity.Purchase;
import com.codebasicz.inventory.entity.Stock;
import com.codebasicz.inventory.entity.Supplier;
import com.codebasicz.inventory.exception.custom.ResourceNotFoundException;
import com.codebasicz.inventory.model.DropDownData;
import com.codebasicz.inventory.model.PurchaseDTO;
import com.codebasicz.inventory.repository.ProductsRepository;
import com.codebasicz.inventory.repository.PurchasesRepository;
import com.codebasicz.inventory.repository.StocksRepository;
import com.codebasicz.inventory.repository.SuppliersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchasesService {

    private final PurchasesRepository purchasesRepository;
    private final ProductsRepository productsRepository;
    private final SuppliersRepository suppliersRepository;
    private final StocksRepository stocksRepository;

    public Page<PurchaseDTO> getPurchases(String search, Pageable pageable) {
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        Page<Purchase> purchasePage;
        if (StringUtils.hasText(search)) {
            List<Product> products = productsRepository.findAllByNameContains(search);
            List<Supplier> suppliers = suppliersRepository.findAllBySupplierNameContains(search);
            List<Integer> productIds = products.stream().map(Product::getId).toList();
            List<Integer> supplierIds = suppliers.stream().map(Supplier::getId).toList();
            purchasePage = purchasesRepository.findAllByProductIdInOrSupplierIdIn(productIds, supplierIds, pageable);
        } else {
            purchasePage = purchasesRepository.findAll(pageable);
        }
        purchasePage.getContent().forEach(purchase -> {
            Product product = productsRepository.findById(purchase.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
            Supplier supplier = suppliersRepository.findById(purchase.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found."));
            PurchaseDTO purchaseDTO = PurchaseDTO.builder()
                    .id(purchase.getId())
                    .productName(product.getName())
                    .supplierContactName(supplier.getSupplierName())
                    .purchasedDate(purchase.getPurchasedDate())
                    .quantity(purchase.getQuantity())
                    .build();
            purchaseDTOS.add(purchaseDTO);
        });
        return new PageImpl<>(purchaseDTOS, pageable, purchasePage.getTotalElements());

    }

    public List<DropDownData> getProductsDropDown() {
        return productsRepository.findAll().stream()
                .map(product -> new DropDownData(product.getId(), product.getName()))
                .collect(Collectors.toList());
    }

    public List<DropDownData> getSuppliersDropDown() {
        return suppliersRepository.findAll().stream()
                .map(supplier -> new DropDownData(supplier.getId(), supplier.getContactName()))
                .collect(Collectors.toList());
    }

    public PurchaseDTO getPurchaseById() {
        return PurchaseDTO.builder().purchasedDate(new Date()).build();
    }

    public void savePurchase(Purchase purchase) {
        Stock stock = stocksRepository.findByProductId(purchase.getProductId())
                .orElse(Stock.builder()
                        .productId(purchase.getProductId())
                        .quantity(0)
                        .build());
        stock.setLastStockUpdate(new Date());
        stock.setQuantity(stock.getQuantity() + purchase.getQuantity());
        stocksRepository.save(stock);
        purchasesRepository.save(purchase);
    }

    public void deletePurchase(Integer id) {
        Purchase purchase = purchasesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase not found."));
        Stock stock = stocksRepository.findByProductId(purchase.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Stock not found."));
        stock.setQuantity(stock.getQuantity() - purchase.getQuantity());
        stock.setLastStockUpdate(new Date());
        stocksRepository.save(stock);
        purchasesRepository.deleteById(id);
    }
}
