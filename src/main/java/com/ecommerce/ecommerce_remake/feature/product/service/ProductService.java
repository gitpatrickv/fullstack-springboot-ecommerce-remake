package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    void saveProduct(ProductModel model, MultipartFile[] files);
}
