package com.ecommerce.ecommerce_remake.feature.user.service;

import com.ecommerce.ecommerce_remake.common.util.StrUtil;
import com.ecommerce.ecommerce_remake.feature.user.utils.mapper.UserMapper;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.entity.User;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public User getCurrentAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(StrUtil.USER_NOT_FOUND));
    }

    @Override
    public UserModel getCurrentUserInfo() {
        User user = this.getCurrentAuthenticatedUser();
        return mapper.mapEntityToModel(user);
    }


}
