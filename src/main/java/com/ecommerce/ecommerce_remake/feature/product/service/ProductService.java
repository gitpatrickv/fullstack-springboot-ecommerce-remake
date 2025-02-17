package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.product.dto.ProductInfoResponse;
import com.ecommerce.ecommerce_remake.feature.product.dto.StoreCategory;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void saveProduct(ProductModel model, MultipartFile[] files);
    GetAllResponse getAllProducts(Pageable pageable);
    GetAllResponse getStoreProductsByStoreId(Pageable pageable, String storeId, Category category, Integer ratingFilter, Integer minPrice, Integer maxPrice);
    GetAllResponse getAllProductsByCategory(Pageable pageable, Category category, Integer ratingFilter, Integer minPrice, Integer maxPrice);
    GetAllResponse searchProduct(String search, Integer ratingFilter, Integer minPrice, Integer maxPrice, Pageable pageable);
    List<StoreCategory> getUniqueProductCategoriesForStore(String storeId);
    Product getProductById(Integer productId);
    ProductInfoResponse mapProductInfo(Product product);
}
