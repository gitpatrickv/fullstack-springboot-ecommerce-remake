package com.ecommerce.ecommerce_remake.security.service;

import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.security.dto.request.LoginRequest;
import com.ecommerce.ecommerce_remake.security.dto.response.LoginResponse;
import com.ecommerce.ecommerce_remake.feature.user.entity.User;
import com.ecommerce.ecommerce_remake.feature.user.enums.Role;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.feature.user.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public LoginResponse registerUser(UserModel userModel) {
        User user = mapper.mapModelToEntity(userModel);
        user.setRole(Role.USER);
        userRepository.save(user);
        return this.authenticate(userModel.getEmail(), userModel.getPassword());
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return this.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }

    private LoginResponse authenticate(String email, String password){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            return LoginResponse.builder()
                    .jwtToken(jwtService.generateToken(authentication))
                    .role(authentication.getAuthorities().iterator().next().getAuthority())
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password! Please try again.");
        }
    }
}
