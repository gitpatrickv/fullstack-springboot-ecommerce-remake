package com.ecommerce.ecommerce_remake.config;

import com.ecommerce.ecommerce_remake.common.factory.CrudServiceFactory;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductServiceImpl;
import com.ecommerce.ecommerce_remake.feature.product_image.service.ProductImageService;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.store.service.StoreServiceImpl;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import jakarta.validation.Validator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ApplicationContext applicationContext;


    public ServiceConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CrudServiceFactory getServiceFactory() {
        return new CrudServiceFactory(applicationContext);
    }

    @Bean (name = "storeService")
    public StoreServiceImpl getStoreService(StoreRepository repository,
                                            Validator validator,
                                            UserService userService,
                                            Pagination pagination){
        return new StoreServiceImpl(repository, validator, userService, pagination);
    }

    @Bean (name = "productService")
    public ProductServiceImpl getProductService(ProductRepository repository,
                                                Validator validator,
                                                UserService userService,
                                                InventoryService inventoryService,
                                                ProductImageService productImageService,
                                                Pagination pagination){
        return new ProductServiceImpl(repository, validator, userService, inventoryService, productImageService, pagination);
    }



}
