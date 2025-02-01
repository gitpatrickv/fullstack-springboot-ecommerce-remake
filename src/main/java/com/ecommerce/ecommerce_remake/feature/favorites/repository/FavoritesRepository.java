package com.ecommerce.ecommerce_remake.feature.favorites.repository;

import com.ecommerce.ecommerce_remake.feature.favorites.model.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    Optional<Favorites> findByProduct_ProductIdAndUser_UserId(Integer productId, Integer userId);
    void deleteByProduct_ProductIdAndUser_UserId(Integer productId, Integer userId);
    Page<Favorites> findAllByUser_UserId(Integer userId, Pageable pageable);

}
