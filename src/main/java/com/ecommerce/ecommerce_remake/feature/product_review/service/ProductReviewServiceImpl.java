package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReviewModel;
import com.ecommerce.ecommerce_remake.feature.product_review.repository.ProductReviewRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class ProductReviewServiceImpl extends CrudService {

    private ModelToEntityMapper<ProductReviewModel, ProductReview> modelToEntityMapper = new ModelToEntityMapper<>(ProductReview.class);
    private EntityToModelMapper<ProductReview, ProductReviewModel> entityToModelMapper = new EntityToModelMapper<>(ProductReviewModel.class);

    private final UserService userService;
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final Validator validator;

    @Transactional
    @Override
    protected <T extends Model> Model save(T model) {
        User user = userService.getCurrentAuthenticatedUser();
        ProductReview productReview = modelToEntityMapper.map((ProductReviewModel) model);
        productReview.setUser(user);
        ProductReview savedReview = productReviewRepository.saveAndFlush(productReview);

        productRepository.updateProductAverageRating(savedReview.getProductId());
        productRepository.updateProductReviewsCount(savedReview.getProductId());

        return entityToModelMapper.map(savedReview);
    }

    @Override
    protected <T extends Model> Optional<Model> getOne(String id) {
        return Optional.empty();
    }

    @Override
    protected GetAllResponse getAll(int pageNo, int pageSize, String sortBy) {
        return null;
    }

    @Override
    protected <T extends Model> Model updateOne(T model) {
        return null;
    }

    @Override
    protected void changeStatus(String id, Status status) {

    }

    @Override
    protected String moduleName() {
        return Module.productReview.getModuleName();
    }

    @Override
    protected Class modelClass() {
        return ProductReviewModel.class;
    }

    @Override
    protected Validator validator() {
        return this.validator;
    }



}
