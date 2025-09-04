package com.codearp.application.products.repositories;

import com.codearp.application.products.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsBySkuIgnoreCase(String sku);
}
