package com.ecommerce.ecommerce_remake.feature.favorites.repository;

import com.ecommerce.ecommerce_remake.feature.favorites.model.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    Optional<Favorites> findByUserIdAndProduct_ProductId(Integer userId, Integer productId);
    void deleteByUserIdAndProduct_ProductId(Integer userId, Integer productId);
    Page<Favorites> findAllByUserId(Integer userId, Pageable pageable);

}
