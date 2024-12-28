package com.ecommerce.ecommerce_remake.feature.store.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.user.enums.Role;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StoreServiceImpl extends CrudService implements StoreService {

    private final StoreRepository storeRepository;
    private final Validator validator;
    private final UserService userService;

    private ModelToEntityMapper<StoreModel, Store> modelToEntityMapper = new ModelToEntityMapper<>(Store.class);
    private EntityToModelMapper<Store, StoreModel> entityToModelMapper = new EntityToModelMapper<>(StoreModel.class);
    @Transactional
    @Override
    protected <T extends Model> Model save(T model) {
        User user = userService.getCurrentAuthenticatedUser();
        Store store = modelToEntityMapper.map((StoreModel)model);
        store.setStatus(Status.ACTIVE);
        store.setUser(user);
        Store savedStore = storeRepository.save(store);

        user.setRole(Role.SELLER);

        return entityToModelMapper.map(savedStore);
    }

    @Override
    protected StoreModel getOne(String id) {
        Optional<Store> optionalStore = this.getStoreById(id);
        return optionalStore.map(entityToModelMapper::map)
                .orElse(null);
    }

    @Override
    protected List<StoreModel> getAll() {                   //TODO: Not yet implemented on the frontend
        return storeRepository.findAll()
                .stream()
                .map(entityToModelMapper::map)
                .toList();
    }

    @Override
    protected String updateOne() {
        return "Update One remake";
    }

    @Override
    protected String deleteOne() {
        return "Delete One";
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

    @Override
    public Optional<Store> getStoreById(String id) {
        return storeRepository.findByStoreNameIgnoreCase(id);
    }
}
