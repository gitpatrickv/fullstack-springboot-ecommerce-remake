package com.ecommerce.ecommerce_remake.feature.store.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.user.enums.Role;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class StoreCrudFactoryService extends CrudService {

    private final StoreRepository storeRepository;
    private final Validator validator;
    private final UserService userService;
    private final Pagination pagination;
    private final StoreService storeService;
    private final UserRepository userRepository;

    private EntityToModelMapper<Store, StoreModel> entityToModelMapper = new EntityToModelMapper<>(StoreModel.class);

    @Transactional
    @Override
    protected <T extends Model> Model save(T model) {
        StoreModel storeModel = (StoreModel) model;
        User user = userService.getCurrentAuthenticatedUser();
        Store store = Store.builder()
                .storeName(storeModel.getStoreName())
                .contactNumber(storeModel.getContactNumber())
                .averageRating(BigDecimal.ZERO)
                .reviewsCount(0)
                .status(Status.ACTIVE)
                .user(user)
                .build();

        Store savedStore = storeRepository.save(store);

        user.setRole(Role.SELLER);
        userRepository.save(user);

        return entityToModelMapper.map(savedStore);
    }

    @Override
    protected Optional<StoreModel> getOne(String id) {
        return storeService.getStoreById(id).map(entityToModelMapper::map);
    }

    @Override //TODO: Not yet implemented on the frontend //Get All Stores
    protected GetAllResponse getAll(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(pageable);
        PageResponse pageResponse = pagination.getPagination(stores);
        List<StoreModel> storeModels = stores.stream()
                .map(entityToModelMapper::map)
                .toList();
        return new GetAllResponse(storeModels, pageResponse);
    }

    @Override
    protected  <T extends Model> Model updateOne(T model) {
        StoreModel storeModel = (StoreModel) model;
        Store store = storeService.getStore();
        store.setStoreName(storeModel.getStoreName() != null ? storeModel.getStoreName() : store.getStoreName());
        store.setContactNumber(storeModel.getContactNumber() != null ? storeModel.getContactNumber() : store.getContactNumber());
        Store savedStore = storeRepository.save(store);
        return entityToModelMapper.map(savedStore);
    }

    @Override //TODO: Not yet implemented on the frontend
    protected void changeStatus(String id, Status status) {
        storeService.getStoreById(id).ifPresent(store -> {
            store.setStatus(status);
            storeRepository.save(store);
        });
    }

    @Override
    protected String moduleName() {
        return Module.store.getModuleName();
    }
    @Override
    protected Class modelClass() {
        return StoreModel.class;
    }

    @Override
    protected Validator validator() {
        return validator;
    }

}
