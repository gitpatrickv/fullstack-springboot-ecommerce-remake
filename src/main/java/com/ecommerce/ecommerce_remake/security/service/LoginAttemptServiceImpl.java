package com.ecommerce.ecommerce_remake.security.service;

import com.ecommerce.ecommerce_remake.feature.user.repository.UserRepository;
import com.ecommerce.ecommerce_remake.security.entity.LoginAttempt;
import com.ecommerce.ecommerce_remake.security.repository.LoginAttemptRepository;
import com.ecommerce.ecommerce_remake.web.exception.LoginAttemptException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LoginAttemptServiceImpl implements LoginAttemptService{

    private final LoginAttemptRepository loginAttemptRepository;
    private final UserRepository userRepository;
    public static final int expirationTime = 1;

    @Override
    public void handleLoginAttempt(String email) {
        int maxLoginAttempts = 3;
        LoginAttempt loginAttempt = this.addAttempt(email);

        if(loginAttempt.getIsUserLocked()){
            throw new LoginAttemptException(expirationTime);
        }

        if(loginAttempt.getAttempts() >= maxLoginAttempts){
            this.lockUser(loginAttempt, email);
        }

        log.warn("Failed login for user {}, attempts: {}, remaining attempts: {}",
                email, loginAttempt.getAttempts(), maxLoginAttempts - loginAttempt.getAttempts());
    }

    @Override
    public void resetAttempt(String email) {
        loginAttemptRepository.findByEmail(email).ifPresent(loginAttempt -> {
            LoginAttempt attempt = this.createNewAttempt(email);
            attempt.setAttemptId(loginAttempt.getAttemptId());
            loginAttemptRepository.save(attempt);
            log.info("Reset Login attempt for user {}", email);
        });
    }

    private LoginAttempt addAttempt(String email){
        LoginAttempt loginAttempt = loginAttemptRepository.findByEmail(email)
                .orElseGet(() -> this.createNewAttempt(email));
        loginAttempt.setAttempts(loginAttempt.getAttempts() + 1);
        return loginAttemptRepository.save(loginAttempt);
    }

    private LoginAttempt createNewAttempt(String email){
        LoginAttempt loginAttempt = new LoginAttempt();
        loginAttempt.setEmail(email);
        loginAttempt.setAttempts(0);
        loginAttempt.setIsUserLocked(false);
        loginAttempt.setExpirationTime(null);
        log.info("Created new login attempt for user {}", email);
        return loginAttempt;
    }

    private void lockUser(LoginAttempt loginAttempt, String email){
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationTime);
        loginAttempt.setExpirationTime(now);
        loginAttempt.setIsUserLocked(true);
        loginAttemptRepository.save(loginAttempt);
        userRepository.accountNonLock(email, false);
        log.warn("Lock User: {} for {} minutes", email, expirationTime);
    }
}
