package com.ecommerce.ecommerce_remake.feature.user.service;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.web.exception.NotImplementedException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserCrudFactoryService extends CrudService {

    private final Validator validator;
    private final UserRepository userRepository;
    private final Pagination pagination;

    private EntityToModelMapper<User, UserModel> entityToModelMapper = new EntityToModelMapper<>(UserModel.class);

    @Override
    protected <T extends Model> Model save(T model) {
        throw new NotImplementedException();
    }

    @Override
    protected <T extends Model> Optional<Model> getOne(String id) {
        int userId = Integer.parseInt(id);
        return userRepository.findById(userId).map(entityToModelMapper::map);
    }

    @Override
    protected GetAllResponse getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        PageResponse pageResponse = pagination.getPagination(users);
        return new GetAllResponse(this.userModels(users), pageResponse);
    }

    private List<UserModel> userModels(Page<User> users){
        return users.stream()
                .map(entityToModelMapper::map)
                .toList();
    }

    @Override
    protected <T extends Model> Model updateOne(T model) {
        throw new NotImplementedException();
    }

    @Override
    protected void changeStatus(String id, Status status) {
        int userId = Integer.parseInt(id);
        userRepository.findById(userId).ifPresent(user -> {
            user.setStatus(status);
            user.setAccountNonLocked(status.equals(Status.ACTIVE));
            userRepository.save(user);
        });
    }

    @Override
    protected String moduleName() {
        return Module.user.getModuleName();
    }

    @Override
    protected Class modelClass() {
        return UserModel.class;
    }

    @Override
    protected Validator validator() {
        return validator;
    }

}
