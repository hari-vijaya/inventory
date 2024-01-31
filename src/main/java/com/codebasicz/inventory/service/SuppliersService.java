package com.codebasicz.inventory.service;

import com.codebasicz.inventory.entity.Supplier;
import com.codebasicz.inventory.exception.custom.ResourceNotFoundException;
import com.codebasicz.inventory.repository.SuppliersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SuppliersService {
    private final SuppliersRepository suppliersRepository;

    public Page<Supplier> getSuppliers(String search, Pageable pageable) {

        if (StringUtils.hasText(search)) {
            return suppliersRepository.getSuppliersBySupplierNameLikeOrContactNameLikeOrEmailLikeOrPhoneLike(search, pageable);
        } else {
            return suppliersRepository.findAll(pageable);
        }
    }

    public Supplier getSupplierById(Integer id) {

        if (Objects.nonNull(id)) {
            return suppliersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found."));
        } else {
            return new Supplier();
        }

    }

    public void saveSupplier(Supplier supplier) {
        suppliersRepository.save(supplier);
    }

    public void deleteSupplier(Integer id) {
        suppliersRepository.deleteById(id);
    }

    public void saveAllSuppliers(List<Supplier> suppliers) {
        suppliersRepository.saveAll(suppliers);
    }
}
