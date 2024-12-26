package com.ecommerce.ecommerce_remake.feature.user.service;

import com.ecommerce.ecommerce_remake.feature.user.entity.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;

public interface UserService {

    User getCurrentAuthenticatedUser();
    UserModel getCurrentUserInfo();
}
