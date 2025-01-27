package com.ecommerce.ecommerce_remake.config;

import com.ecommerce.ecommerce_remake.common.factory.CrudServiceFactory;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.feature.address.repository.AddressRepository;
import com.ecommerce.ecommerce_remake.feature.address.service.AddressCrudFactoryService;
import com.ecommerce.ecommerce_remake.feature.address.service.AddressService;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductCrudFactoryService;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductService;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.store.service.StoreCrudFactoryService;
import com.ecommerce.ecommerce_remake.feature.store.service.StoreService;
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
    public StoreCrudFactoryService getStoreCrudService(StoreRepository repository,
                                            Validator validator,
                                            UserService userService,
                                            Pagination pagination,
                                            StoreService storeService){
        return new StoreCrudFactoryService(repository, validator, userService, pagination, storeService);
    }

    @Bean (name = "productService")
    public ProductCrudFactoryService getProductService(ProductRepository repository,
                                                       Validator validator,
                                                       UserService userService,
                                                       ProductService productService){
        return new ProductCrudFactoryService(repository, validator, userService, productService);
    }

    @Bean (name = "addressService")
    public AddressCrudFactoryService getAddressService(AddressRepository repository,
                                                       Validator validator,
                                                       UserService userService,
                                                       AddressService addressService){
        return new AddressCrudFactoryService(repository, validator, userService, addressService);
    }

}
