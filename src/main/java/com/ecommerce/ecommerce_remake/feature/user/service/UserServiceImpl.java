package com.ecommerce.ecommerce_remake.feature.user.service;

import com.ecommerce.ecommerce_remake.common.util.StrUtil;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private EntityToModelMapper<User, UserModel> entityToModelMapper = new EntityToModelMapper<>(UserModel.class);

    @Override
    public User getCurrentAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(StrUtil.USER_NOT_FOUND));
    }

    @Override
    public UserModel getCurrentUserInfo() {
        User user = this.getCurrentAuthenticatedUser();
        UserModel userModel = entityToModelMapper.map(user);

        if(user.getStore() != null) {
            userModel.setStoreId(user.getStore().getStoreId());
        }
        return userModel;
    }
}
