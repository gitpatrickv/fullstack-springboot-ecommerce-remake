package com.ecommerce.ecommerce_remake.feature.product.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.model.ProductModel;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_image.service.ProductImageService;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final InventoryService inventoryService;
    private final ProductImageService productImageService;
    private final Pagination pagination;

    private EntityToModelMapper<Product, ProductModel> entityToModelMapper = new EntityToModelMapper<>(ProductModel.class);

    @Transactional
    @Override
    public void saveProduct(ProductModel model, MultipartFile[] files) {

        User user = userService.getCurrentAuthenticatedUser();
        Store store = user.getStore();

        Product product = Product.builder()
                .productName(model.getProductName())
                .description(model.getDescription())
                .category(model.getCategory())
                .totalSold(0)
                .averageRating(BigDecimal.ZERO)
                .reviewsCount(0)
                .status(Status.LISTED)
                .store(store)
                .build();

        Set<Inventory> saveToInventory = inventoryService.mapModelToInventory(product, model.getInventories());

        product.setInventories(saveToInventory);

        Product savedProduct = productRepository.save(product);

        if(files != null) {
            productImageService.uploadProductImage(savedProduct, files);
        }
    }

    @Override
    public GetAllResponse getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAllByStatus(Status.LISTED, pageable);
        return this.fetchAllProducts(products);
    }

    @Override
    public GetAllResponse getStoreProductsByStoreId(Pageable pageable, String storeId) {
        int id = Integer.parseInt(storeId);
        Page<Product> products = productRepository.findAllByStatusAndStore_StoreId(Status.LISTED, id, pageable);
        return this.fetchAllProducts(products);
    }

    @Override
    public GetAllResponse getAllProductsByCategory(Pageable pageable, Category category, Integer ratingFilter, Integer minPrice, Integer maxPrice) {
        Page<Product> products = productRepository.findProductsByCategory(category, Status.LISTED, ratingFilter, minPrice, maxPrice, pageable);
        return this.fetchAllProducts(products);
    }

    @Override
    public GetAllResponse searchProduct(String search,
                                        Integer ratingFilter,
                                        Integer minPrice,
                                        Integer maxPrice,
                                        Pageable pageable) {
        Page<Product> products = productRepository.searchProduct(search, Status.LISTED, ratingFilter, minPrice ,maxPrice, pageable);
        return this.fetchAllProducts(products);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(Integer.parseInt(id));
    }

    @Override
    public GetAllResponse fetchAllProducts(Page<Product> products) {
        PageResponse pageResponse = pagination.getPagination(products);
        List<ProductModel> productModels = this.getProducts(products);
        return new GetAllResponse(productModels, pageResponse);
    }

    public List<ProductModel> getProducts(Page<Product> products) {
        return products.stream()
                .map(entityToModelMapper::map)
                .toList();
    }
}
