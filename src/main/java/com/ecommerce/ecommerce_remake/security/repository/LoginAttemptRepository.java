package com.ecommerce.ecommerce_remake.security.repository;

import com.ecommerce.ecommerce_remake.security.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Integer> {

    Optional<LoginAttempt> findByEmail(String email);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE login_attempts la
                    SET attempts = 0, expiration_time = NULL, is_user_locked = false
                    WHERE la.is_user_locked = true AND la.expiration_time <= :now
                    """)
    void resetLoginAttempts(@Param("now") LocalDateTime now);

}
