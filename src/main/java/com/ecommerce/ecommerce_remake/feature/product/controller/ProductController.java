package com.ecommerce.ecommerce_remake.feature.product.controller;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.product.dto.StoreCategory;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ecommerce.ecommerce_remake.common.util.PageableUtils.createPaginationAndSorting;

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
            String message = String.format("Product '%s' was saved successfully.", model.getProductName());
            log.info("Save Product POST Response 201 : {}", message);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e){
            log.error("Unexpected error while saving product: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred while processing the request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<GetAllResponse> getAllProducts(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                         @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
                                                         @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection){
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = productService.getAllProducts(pageable);
        if(getAllResponse.getModels().isEmpty()){
            log.warn("GetAllProducts - No data found");
        }
        log.info("GetAllProducts - GET Response: 200 - Returning {} product records", getAllResponse.getModels().size());
        return ResponseEntity.ok(getAllResponse);
    }
    @GetMapping("/{storeId}")
    public ResponseEntity<GetAllResponse> getStoreProductsByStoreId(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                    @RequestParam(value = "sortBy", defaultValue = "productName") String sortBy,
                                                                    @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                                                                    @RequestParam(value = "ratingFilter", required = false) Integer ratingFilter,
                                                                    @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                                    @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                                                    @RequestParam(value = "category", required = false) Category category,
                                                                    @PathVariable("storeId") String storeId){
        log.info("Received request to get products by Store. StoreId: '{}', Sort By: {}, Sort Dir: {}, Rating: {}, Min Price: {}, Max Price: {}, Category: {}",
                storeId, sortBy, sortDirection, ratingFilter, minPrice, maxPrice, category);
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = productService.getStoreProductsByStoreId(pageable, storeId, category, ratingFilter, minPrice, maxPrice);
        if(getAllResponse.getModels().isEmpty()){
            log.warn("GetStoreProductsByStoreId - No data found");
        }
        log.info("GetStoreProductsByStoreId - GET Response: 200 - Returning {} product records", getAllResponse.getModels().size());
        return ResponseEntity.ok(getAllResponse);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<GetAllResponse> getAllProductsByCategory(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "productName") String sortBy,
                                                                   @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                                                                   @RequestParam(value = "ratingFilter", required = false) Integer ratingFilter,
                                                                   @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                                   @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                                                   @PathVariable("category") Category category){
        log.info("Received request to get products by category. Category: '{}', Sort By: {}, Sort Dir: {}, Rating: {}, Min Price: {}, Max Price: {}",
                category, sortBy, sortDirection, ratingFilter, minPrice, maxPrice);
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = productService.getAllProductsByCategory(pageable, category, ratingFilter, minPrice, maxPrice);
        if(getAllResponse.getModels().isEmpty()){
            log.warn("GetAllProductsByCategory - No data found");
        }
        log.info("GetAllProductsByCategory - GET Response: 200 - Returning {} product records", getAllResponse.getModels().size());
        return ResponseEntity.ok(getAllResponse);

    }
    @GetMapping("/search")
    public ResponseEntity<GetAllResponse> searchProduct(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "productName") String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                                                        @RequestParam(value = "keyword") String search,
                                                        @RequestParam(value = "ratingFilter", required = false) Integer ratingFilter,
                                                        @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                        @RequestParam(value = "maxPrice", required = false) Integer maxPrice){
        log.info("Received request to search products. Search keyword: '{}', Sort By: {}, Sort Dir: {}, Rating: {}, Min Price: {}, Max Price: {}",
                search, sortBy, sortDirection, ratingFilter, minPrice, maxPrice);
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = productService.searchProduct(search,ratingFilter,minPrice,maxPrice, pageable);
        log.info("SearchProduct - GET Response: 200 - Returning {} product records", getAllResponse.getModels().size());
        return ResponseEntity.ok(getAllResponse);
    }
    @GetMapping("/{storeId}/categories")
    public List<StoreCategory> getUniqueProductCategoriesForStore(@PathVariable("storeId") String storeId){
        return productService.getUniqueProductCategoriesForStore(storeId);
    }
}
