package com.ecommerce.ecommerce_remake.feature.features.repository;

import com.ecommerce.ecommerce_remake.feature.features.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
}
