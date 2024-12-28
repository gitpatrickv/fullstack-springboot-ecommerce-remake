package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
import com.ecommerce.ecommerce_remake.feature.product.enums.ProductStatus;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_image.service.ProductImageService;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final UserService userService;

    private ModelToEntityMapper<ProductModel, Product> modelToEntityMapper = new ModelToEntityMapper<>(Product.class);
    private EntityToModelMapper<Product, ProductModel> entityToModelMapper = new EntityToModelMapper<>(ProductModel.class);

    @Transactional
    @Override
    public void saveProduct(ProductModel model, MultipartFile[] files) {

        User user = userService.getCurrentAuthenticatedUser();
        Store store = user.getStore();

        Product product = modelToEntityMapper.map(model);
        product.setProductStatus(ProductStatus.LISTED);
        product.setStore(store);

        Set<Inventory> inventories = inventoryService.inventories(product, model.getInventories());

        product.setInventories(inventories);

        Product savedProduct = productRepository.save(product);

        if(files != null) {
            productImageService.uploadProductImage(savedProduct, files);
        }
    }
}
