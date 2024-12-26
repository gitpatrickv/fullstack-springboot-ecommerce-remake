package com.ecommerce.ecommerce_remake.feature.user.utils.mapper;

import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper = new ModelMapper();

    public UserModel mapEntityToModel(User user){
        return mapper.map(user, UserModel.class);
    }

    public User mapModelToEntity(UserModel userModel){
        User user = mapper.map(userModel, User.class);
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return user;
    }
}
