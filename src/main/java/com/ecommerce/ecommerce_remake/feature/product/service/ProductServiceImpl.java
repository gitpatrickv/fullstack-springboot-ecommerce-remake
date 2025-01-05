package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final Pagination pagination;

    private ModelToEntityMapper<ProductModel, Product> modelToEntityMapper = new ModelToEntityMapper<>(Product.class);
    private EntityToModelMapper<Product, ProductModel> entityToModelMapper = new EntityToModelMapper<>(ProductModel.class);

    @Override
    protected <T extends Model> Model save(T model) {
        throw new NotImplementedException();
    }

    @Override
    protected Optional<ProductModel> getOne(String id) {
        return this.getProductById(id).map(entityToModelMapper::map);
    }

    @Override //Get All Store Product
    protected GetAllResponse getAll(int pageNo, int pageSize, String sortBy) {
        Sort sort = switch (sortBy) {
            case "totalSold" -> Sort.by("totalSold").descending();
            case "lowProductSold" -> Sort.by("totalSold").ascending();
            case "productName" -> Sort.by("productName").ascending();
            case null, default -> Sort.by("createdDate").descending();
        };

        List<Status> statuses = List.of(Status.ACTIVE, Status.SUSPENDED);

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        User user = userService.getCurrentAuthenticatedUser();
        Store store = user.getStore();
        Page<Product> products = productRepository.findByStoreAndStatusIn(store, statuses ,pageable);
        PageResponse pageResponse = pagination.getPagination(products);
        List<ProductModel> productModels = this.getProducts(products);
        return new GetAllResponse(productModels, pageResponse);
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
    protected void changeOneState(String id, Status status) {
        this.getProductById(id).ifPresent(product -> {
            product.setStatus(status);
            productRepository.save(product);
        });
    }

    @Transactional
    @Override
    public void saveProduct(ProductModel model, MultipartFile[] files) {

        User user = userService.getCurrentAuthenticatedUser();
        Store store = user.getStore();

        Product product = modelToEntityMapper.map(model);
        product.setStatus(Status.ACTIVE);
        product.setStore(store);

        Set<Inventory> saveToInventory = inventoryService.mapModelToInventory(product, model.getInventories());

        product.setInventories(saveToInventory);

        Product savedProduct = productRepository.save(product);

        if(files != null) {
            productImageService.uploadProductImage(savedProduct, files);
        }
    }

    @Override
    public GetAllResponse getAllProducts(int pageNo, int pageSize, String sortBy) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAllByStatus(Status.ACTIVE, pageable);
        PageResponse pageResponse = pagination.getPagination(products);
        List<ProductModel> productModels = this.getProducts(products);
        return new GetAllResponse(productModels, pageResponse);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(Integer.parseInt(id));
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

    private List<ProductModel> getProducts(Page<Product> products) {
        return products.stream()
                .map(entityToModelMapper::map)
                .toList();
    }


}
