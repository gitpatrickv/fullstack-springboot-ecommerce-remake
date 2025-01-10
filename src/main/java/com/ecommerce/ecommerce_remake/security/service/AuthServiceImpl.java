package com.ecommerce.ecommerce_remake.security.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartRepository;
import com.ecommerce.ecommerce_remake.feature.user.enums.Role;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.security.dto.request.LoginRequest;
import com.ecommerce.ecommerce_remake.security.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    private ModelToEntityMapper<UserModel, User> modelToEntityMapper = new ModelToEntityMapper<>(User.class);

    @Override
    public LoginResponse registerUser(UserModel userModel) {
        User user = modelToEntityMapper.map(userModel);
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        cart.setTotalItems(0);
        cartRepository.save(cart);

        return this.authenticate(userModel.getEmail(), userModel.getPassword());
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return this.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }

    private LoginResponse authenticate(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        return LoginResponse.builder()
                .jwtToken(jwtService.generateToken(authentication))
                .role(authentication.getAuthorities().iterator().next().getAuthority())
                .build();
    }
}
