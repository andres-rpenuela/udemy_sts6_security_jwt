package com.codearp.application.products.controllers;

import com.codearp.application.products.dtos.ProductDto;
import com.codearp.application.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductApiController {

    private final ProductService productService;
    private final View error;


    @GetMapping
    public List<ProductDto> getProducts(){
        return productService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        Optional<ProductDto> productDtoOptional = productService.findById(productId);

        if( productDtoOptional.isPresent() ){
            return ResponseEntity.ok(productDtoOptional.get() );
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.warn("Body Request bad: "+bindingResult.getFieldErrors());
            Map<String,Object> errors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->{
                errors.put( error.getField(), error.getDefaultMessage() );
            });
            return ResponseEntity.badRequest().body( errors );
        }
        productDto = productService.save(productDto);

        return ResponseEntity.ok( productDto );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDto productDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.warn("Body Request bad: "+bindingResult.getFieldErrors());
            Map<String,Object> errors =  new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->{
                errors.put( error.getField(), error.getDefaultMessage() );
            });
            return ResponseEntity.badRequest().body( errors );
        }

        productDto = productService.update(id,productDto);

        return ResponseEntity.ok( productDto );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        productService.delete( id );

        return ResponseEntity.ok().build();
    }
}
