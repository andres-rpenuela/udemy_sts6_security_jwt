package com.codearp.application.products.services;

import com.codearp.application.products.advoices.exceptions.ProductNotFoundException;
import com.codearp.application.products.dtos.ProductDto;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll();
    Optional<ProductDto> findById(Long id);
    ProductDto save(@NonNull ProductDto productDto);
    ProductDto update(@NonNull Long id,@NonNull ProductDto productDto)  throws ProductNotFoundException;
    void delete(@NonNull Long id);

    boolean existsBySkuIgnoreCase(String sku);
}
