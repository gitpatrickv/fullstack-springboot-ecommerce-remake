package com.ecommerce.ecommerce_remake.feature.store.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StoreServiceImpl extends CrudService implements StoreService {

    private final StoreRepository storeRepository;
    private final Validator validator;
    private final UserService userService;
    private final Pagination pagination;

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
        Optional<Store> optionalStore = this.getStoreByName(id);
        return optionalStore.map(entityToModelMapper::map)
                .orElse(null);
    }

    @Override //TODO: Not yet implemented on the frontend //Get All Stores
    protected GetAllResponse getAll(int pageNo, int pageSize, String sortBy) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Store> stores = storeRepository.findAll(pageable);
        PageResponse pageResponse = pagination.getPagination(stores);
        List<StoreModel> storeModels = stores.stream()
                .map(entityToModelMapper::map)
                .toList();
        return new GetAllResponse(storeModels, pageResponse);
    }

    @Override
    protected String updateOne() {
        return "Update One remake";
    }

    @Override //TODO: Not yet implemented on the frontend
    protected void changeOneState(String id, Status status) {
        this.getStoreById(id).ifPresent(store -> {
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

    @Override
    public Optional<Store> getStoreByName(String name) {
        return storeRepository.findByStoreNameIgnoreCase(name);
    }

    @Override
    public Optional<Store> getStoreById(String id) {
        return storeRepository.findById(Integer.parseInt(id));
    }
}
