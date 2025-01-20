package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProductReviewServiceImpl extends CrudService {
    @Override
    protected <T extends Model> Model save(T model) {
        return null;
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
        return null;
    }

    @Override
    protected Class modelClass() {
        return null;
    }

    @Override
    protected Validator validator() {
        return null;
    }
}
