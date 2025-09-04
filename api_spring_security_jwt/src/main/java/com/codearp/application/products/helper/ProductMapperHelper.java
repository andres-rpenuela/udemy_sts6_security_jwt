package com.codearp.application.products.helper;

import com.codearp.application.products.domains.Product;
import com.codearp.application.products.dtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

//Eso ya te da un único bean compartido en toda la aplicación (singleton)
@Component
@Slf4j
@AllArgsConstructor
public class ProductMapperHelper {

    private final ModelMapper modelMapper;

    public ProductDto toDto(@NonNull Product product){
          return modelMapper.map(product, ProductDto.class);
    }
}
