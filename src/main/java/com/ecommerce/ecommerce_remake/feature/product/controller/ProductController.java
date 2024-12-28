package com.ecommerce.ecommerce_remake.feature.product.controller;

import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = {"/save"},  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveProduct(@RequestPart("product") @Valid ProductModel model,
                                      @RequestPart("file") MultipartFile[] files){
        log.info("Received request to save product");
        if (files != null) {
            log.info("Number of images to be saved: {}", files.length);
        }
        try{
            productService.saveProduct(model, files);
            String message = String.format("Product '%s' saved successfully.", model.getProductName());
            log.info("POST Response 201: {}", message);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e){
            log.error("Unexpected error while saving product: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred while processing the request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
