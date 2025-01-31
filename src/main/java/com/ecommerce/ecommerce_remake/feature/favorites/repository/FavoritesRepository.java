package com.ecommerce.ecommerce_remake.feature.favorites.repository;

import com.ecommerce.ecommerce_remake.feature.favorites.model.Favorites;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    Optional<Favorites> findByProduct_ProductIdAndUser(Integer productId, User user);
    void deleteByProduct_ProductIdAndUser(Integer productId, User user);
}
