package com.ecommerce.ecommerce_remake.feature.product_image.service;

import com.ecommerce.ecommerce_remake.common.util.StrUtil;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product_image.model.ProductImage;
import com.ecommerce.ecommerce_remake.feature.product_image.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Override
    public void uploadProductImage(Product product, MultipartFile[] files) {
        for(MultipartFile file : files){
            ProductImage productImage = new ProductImage();
            productImage.setProductImage(this.processImages(file));
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
    }

    @Override
    public String processImages(MultipartFile image) {

        String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();

        try {
            Path fileStorageLocation = Paths.get(StrUtil.PHOTO_DIRECTORY).toAbsolutePath().normalize();

            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename));

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/image/" + filename).toUriString();


        } catch (Exception exception) {
            throw new RuntimeException("Unable to save image");
        }
    }

    @Override
    public byte[] getImages(String filename) throws IOException {
        return Files.readAllBytes(Paths.get(StrUtil.PHOTO_DIRECTORY + filename));
    }

}
