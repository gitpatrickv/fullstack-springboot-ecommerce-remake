package com.ecommerce.ecommerce_remake.feature.product_image.service;

import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {

    void uploadProductImage(Product product, MultipartFile[] files);

    String processImages(MultipartFile image);
    byte[] getImages(String filename) throws IOException;
}
