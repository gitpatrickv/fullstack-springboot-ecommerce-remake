package com.ecommerce.ecommerce_remake.feature.user.service;

import com.ecommerce.ecommerce_remake.common.util.StrUtil;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.product_image.service.ProductImageService;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductImageService productImageService;
    private EntityToModelMapper<User, UserModel> entityToModelMapper = new EntityToModelMapper<>(UserModel.class);

    @Override
    public User getCurrentAuthenticatedUser() {
        return userRepository.findById(this.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(StrUtil.USER_NOT_FOUND));
    }

    @Override
    public Integer getUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }

    @Override
    public UserModel getCurrentUserInfo() {
        User user = this.getCurrentAuthenticatedUser();
        return entityToModelMapper.map(user);
    }

    @Override
    public void uploadUserAvatar(MultipartFile file) {
        User user = this.getCurrentAuthenticatedUser();
        user.setPicture(productImageService.processImages(file));
        userRepository.save(user);
    }
}
