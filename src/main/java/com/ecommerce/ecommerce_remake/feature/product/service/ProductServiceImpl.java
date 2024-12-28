package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
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
import com.ecommerce.ecommerce_remake.web.exception.NotImplementedException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class ProductServiceImpl extends CrudService implements ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;
    private final UserService userService;
    private final InventoryService inventoryService;
    private final ProductImageService productImageService;

    private ModelToEntityMapper<ProductModel, Product> modelToEntityMapper = new ModelToEntityMapper<>(Product.class);
    private EntityToModelMapper<Product, ProductModel> entityToModelMapper = new EntityToModelMapper<>(ProductModel.class);

    @Override
    protected <T extends Model> Model save(T model) {
        throw new NotImplementedException();
    }

    @Override
    protected ProductModel getOne(String id) {      //TODO: Not yet implemented on the frontend
        Optional<Product> optionalProduct = this.getProductByName(id);
        return optionalProduct.map(entityToModelMapper::map)
                .orElse(null);
    }

    @Override
    protected <T extends Model> List<T> getAll() {
        return null;
    }

    @Override
    protected String updateOne() {
        return null;
    }

    @Override
    protected String deleteOne() {
        return null;
    }

    @Override
    protected String moduleName() {

        return Module.product.getModuleName();
    }

    @Override
    protected Class modelClass() {
        return ProductModel.class;
    }

    @Override
    protected Validator validator() {
        return validator;
    }

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

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByProductName(name);
    }
}
