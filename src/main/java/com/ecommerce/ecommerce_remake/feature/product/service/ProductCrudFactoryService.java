package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.NotImplementedException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductCrudFactoryService extends CrudService {

    private final ProductRepository productRepository;
    private final Validator validator;
    private final UserService userService;
    private final Pagination pagination;

    private EntityToModelMapper<Product, ProductModel> entityToModelMapper = new EntityToModelMapper<>(ProductModel.class);

    @Override
    protected <T extends Model> Model save(T model) {
        throw new NotImplementedException();
    }

    @Override
    protected Optional<ProductModel> getOne(String id) {
        return this.getProductById(id).map(entityToModelMapper::map);
    }

    @Override //Get All Store Product (Seller Page)
    protected GetAllResponse getAll(Pageable pageable) {
        List<Status> statusList = List.of(Status.LISTED, Status.SUSPENDED);
        User user = userService.getCurrentAuthenticatedUser();
        Store store = user.getStore();
        Page<Product> products = productRepository.findByStoreAndStatusIn(store, statusList, pageable);
        return this.fetchAllProducts(products);
    }
    @Transactional
    @Override
    protected  <T extends Model> Model updateOne(T model) {
        ProductModel productModel = (ProductModel) model;
        return this.getProductById(productModel.getProductId().toString())
                .map( product -> {
                    Optional.ofNullable(productModel.getProductName())
                            .ifPresent(product::setProductName);
                    Optional.ofNullable(productModel.getDescription())
                            .ifPresent(product::setDescription);
                    product.setSlug(null);
                    Product savedProduct = productRepository.save(product);

                    return entityToModelMapper.map(savedProduct);
                }).orElse(null);
    }

    @Override
    protected void changeStatus(String id, Status status) {
        this.getProductById(id).ifPresent(product -> {
            product.setStatus(status);
            productRepository.save(product);
        });
    }
    @Override
    protected String moduleName() { return Module.product.getModuleName(); }

    @Override
    protected Class modelClass() {
        return ProductModel.class;
    }

    @Override
    protected Validator validator() {
        return validator;
    }

    private GetAllResponse fetchAllProducts(Page<Product> products) {
        PageResponse pageResponse = pagination.getPagination(products);
        List<ProductModel> productModels = this.getAllProductInfo(products);
        return new GetAllResponse(productModels, pageResponse);
    }

    private List<ProductModel> getAllProductInfo(Page<Product> products) {
        return products.stream()
                .map(entityToModelMapper::map)
                .toList();
    }

    private Optional<Product> getProductById(String id) {
        return productRepository.findById(Integer.parseInt(id));
    }
}
