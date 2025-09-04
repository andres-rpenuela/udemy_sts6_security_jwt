package com.codearp.application.products.services.impl;


import com.codearp.application.products.advoices.exceptions.ProductNotFoundException;
import com.codearp.application.products.domains.Product;
import com.codearp.application.products.dtos.ProductDto;
import com.codearp.application.products.helper.ProductMapperHelper;
import com.codearp.application.products.repositories.ProductRepository;
import com.codearp.application.products.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static com.codearp.application.products.advoices.MessagesError.PRODUCT_NOT_FOUND;

/**
 * Nota sobre las transacciones:
 *
 * Call transactional methods via an injected dependency instead of directly via 'this'.
 *
 * 🔎 ¿Por qué pasa?
 * Cuando anotas un método con @Transactional, Spring crea un proxy alrededor del bean.
 * Ese proxy se encarga de abrir/cerrar la transacción antes y después del método.
 *
 * 👉 Problema:
 * Si llamas un método @Transactional desde otro método del mismo bean usando this, la llamada no pasa por el proxy → la transacción nunca se inicia.
 *
 * ✅ Formas de solucionarlo
 * 1. Llamar a través de una dependencia inyectada
 * <code>
 * @Service
 * public class ProductService {
 *     private final ProductPersistenceService persistenceService;
 *
 *     public ProductService(ProductPersistenceService persistenceService) {
 *         this.persistenceService = persistenceService;
 *     }
 *
 *     public void saveProduct(ProductDto dto) {
 *         // Aquí sí pasa por el proxy
 *         persistenceService.persist(dto);
 *     }
 * }
 *
 * @Service
 * public class ProductPersistenceService {
 *
 *     @Transactional
 *     public void persist(ProductDto dto) {
 *         ...
 *     }
 * }
 * </code>
 *
 * 2. Auto-inyección del propio bean
 * <code>
 *  @Service
 *  public class ProductService {
 *
 *     @Autowired
 *     private ProductService self; // el proxy inyectado a sí mismo
 *
 *     public void saveProduct(ProductDto dto) {
 *         // Ahora pasa por el proxy
 *         self.persist(dto);
 *     }
 *
 *     @Transactional
 *     public void persist(ProductDto dto) {
 *         ...
 *     }
 *  }
 * </code>
 * 3. Usar AopContext.currentProxy() (menos limpio)
 * <code>
 * @Transactional
 * public void saveProduct(ProductDto dto) {
 *     ((ProductService) AopContext.currentProxy()).persist(dto);
 * }
 * </code>
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapperHelper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> findById(Long id) {
        if(id==null){
            return Optional.empty();
        }
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    @Transactional
    public ProductDto save(@NonNull ProductDto productDto) {

        // toDto(), no necesita ser trnasaccional salvo que que accediera a relaciones LAZ o modificara la base de datos
        return productMapper.toDto( mapAndSaveProduct( new Product() ,productDto) );
    }

    @Override
    @Transactional // para inicar la transacción debe ser invocado desde fuera
    public ProductDto update(@NonNull Long id, @NonNull ProductDto productDto) throws ProductNotFoundException {
        String error_1 = MessageFormat.format(PRODUCT_NOT_FOUND, id );
        Product product = productRepository.findById( id ).orElseThrow(() -> new ProductNotFoundException(error_1));

        return productMapper.toDto( mapAndSaveProduct( product ,productDto) );
    }

    /**
     * Explicación de transacciones y proxy:
     * 1. save()/update() está anotado con @Transactional → abre la transacción.
     * 2. mapAndSaveProduct() es protected @Transactional pero se llama internamente con this → no pasa por proxy, no abre otra transacción, pero está dentro de la transacción de update()/save().
     * 3. productMapper.toDto() no necesita transacción, solo mapea los campos del Product a DTO.
     *
     * @param product
     * @param productDto
     * @return
     */
    @Transactional(propagation = Propagation.MANDATORY) // se llama desde un metodo de la clase, lo que impolica que debe ser transcaiontal, this.mapAndSaveProduct(...), no inicia la transacción
    protected Product mapAndSaveProduct(@NonNull Product product,@NonNull ProductDto productDto){
        product.setName( productDto.getName() );
        product.setDescription( productDto.getDescription() );
        product.setPrice( productDto.getPrice() );
        product.setSku( productDto.getSku() );

        return  productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(@NonNull Long id) {
        String error_1 = MessageFormat.format(PRODUCT_NOT_FOUND, id );
        Product product = productRepository.findById( id ).orElseThrow(() -> new ProductNotFoundException(error_1));

        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySkuIgnoreCase(String sku) {

        return Optional.ofNullable(sku)
                .map(StringUtils::trimWhitespace)
                .map(productRepository::existsBySkuIgnoreCase)
                .orElse(false);
    }
}
