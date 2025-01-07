package com.ecommerce.ecommerce_remake.feature.user.service;

import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getCurrentAuthenticatedUser();
    UserModel getCurrentUserInfo();
    void uploadUserAvatar(MultipartFile file);
}
