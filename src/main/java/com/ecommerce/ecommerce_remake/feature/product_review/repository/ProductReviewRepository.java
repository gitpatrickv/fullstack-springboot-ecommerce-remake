package com.ecommerce.ecommerce_remake.feature.product_review.repository;

import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    //TODO NOTE: I've read an article that says using SELECT * is bad practice.
    //@Query(nativeQuery = true, value = "SELECT * FROM product_reviews WHERE user_id = :userId AND product_id = :productId")
    @Query("SELECT pr FROM ProductReview pr WHERE pr.user.userId = :userId AND pr.productId = :productId")
    Optional<ProductReview> findIfReviewAlreadyExistForUser(@Param("userId") Integer userId, @Param("productId") Integer productId);

}
