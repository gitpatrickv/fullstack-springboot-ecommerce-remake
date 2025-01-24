package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(ProductModel model, MultipartFile[] files);
    GetAllResponse getAllProducts(int pageNo, int pageSize, String sortBy);
    GetAllResponse getStoreProductsByStoreId(int pageNo, int pageSize, String sortBy, String storeId);
    GetAllResponse getAllProductsByCategory(int pageNo, int pageSize, String sortBy, Category category);
    Optional<Product> getProductById(String id);
    GetAllResponse fetchAllProducts(Page<Product> products);
    List<ProductModel> getProducts(Page<Product> products);
}
