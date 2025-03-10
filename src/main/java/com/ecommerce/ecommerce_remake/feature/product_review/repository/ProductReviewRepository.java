package com.ecommerce.ecommerce_remake.feature.product_review.repository;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.ProductRatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {

    @Query("SELECT pr FROM ProductReview pr WHERE pr.user.userId = :userId AND pr.product.productId = :productId")
    Optional<ProductReview> findIfReviewAlreadyExistForUser(@Param("userId") Integer userId, @Param("productId") Integer productId);

    @Query("""
           SELECT new com.ecommerce.ecommerce_remake.feature.product_review.dto.ProductRatingCount(pr.rating, COUNT(pr.rating))
           FROM ProductReview pr
           WHERE pr.product.productId = :productId
           GROUP BY pr.rating
           """)
    List<ProductRatingCount> getRatingCountByProductId(@Param("productId") Integer productId);
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.productId = :productId AND (:rating IS NULL OR pr.rating = :rating)")
    Page<ProductReview> getProductReviews(@Param("productId") Integer productId,
                                          @Param("rating") Integer rating,
                                          Pageable pageable);
    @Query("""
           SELECT pr FROM ProductReview pr
           JOIN pr.product
           WHERE pr.storeId = :storeId
           """)
    Page<ProductReview> findAllByStoreId(@Param("storeId") Integer storeId, Pageable pageable);

}
