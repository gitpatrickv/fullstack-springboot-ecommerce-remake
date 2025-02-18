package com.ecommerce.ecommerce_remake.security.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartRepository;
import com.ecommerce.ecommerce_remake.feature.user.enums.Role;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.security.dto.request.LoginRequest;
import com.ecommerce.ecommerce_remake.security.dto.response.LoginResponse;
import com.ecommerce.ecommerce_remake.web.exception.LoginAttemptException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ecommerce.ecommerce_remake.security.service.LoginAttemptServiceImpl.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public LoginResponse registerUser(UserModel userModel) {
        User user = User.builder()
                .email(userModel.getEmail())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .name(userModel.getName())
                .gender(userModel.getGender())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

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
            loginAttemptService.resetAttempt(email);
            return LoginResponse.builder()
                    .jwtToken(jwtService.generateToken(authentication))
                    .role(authentication.getAuthorities().iterator().next().getAuthority())
                    .build();
        } catch (BadCredentialsException ex) {
            loginAttemptService.handleLoginAttempt(email);
            throw new BadCredentialsException("Invalid username or password! Please try again.");
        } catch (LockedException ex) {
            throw new LoginAttemptException(expirationTime);
        }
    }
}
