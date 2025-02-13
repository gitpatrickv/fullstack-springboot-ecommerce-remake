package com.ecommerce.ecommerce_remake.feature.product.repository;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByStore_StoreIdAndStatusIn(Integer storeId, List<Status> statusList, Pageable pageable);
    Page<Product> findAllByStatus(Status status, Pageable pageable);

    @Query("""
           SELECT DISTINCT p.category
           FROM Product p
           WHERE p.store.storeId = :storeId
           """)
    List<Category> findStoreCategories(@Param("storeId") Integer storeId);

    @Query("""
           SELECT DISTINCT p
           FROM Product p
           JOIN p.inventories inv
           WHERE p.status = :status AND p.store.storeId = :storeId
           AND (:ratingFilter IS NULL OR :ratingFilter <= p.averageRating)
           AND (:category IS NULL OR p.category = :category)
           AND (
               (:minPrice IS NULL AND :maxPrice IS NULL) OR
               (:minPrice IS NULL AND :maxPrice >= inv.price) OR
               (:maxPrice IS NULL AND :minPrice <= inv.price) OR
               (inv.price BETWEEN :minPrice AND :maxPrice)
               )
           """)
    Page<Product> findStoreProducts( @Param("status") Status status,
                                     @Param("storeId") Integer storeId,
                                     @Param("category") Category category,
                                     @Param("ratingFilter") Integer ratingFilter,
                                     @Param("minPrice") Integer minPrice,
                                     @Param("maxPrice") Integer maxPrice,
                                     Pageable pageable);

    @Query("""
           SELECT DISTINCT p
           FROM Product p
           JOIN p.inventories inv
           WHERE p.category = :category AND p.status = :status
           AND (:ratingFilter IS NULL OR :ratingFilter <= p.averageRating)
           AND (
               (:minPrice IS NULL AND :maxPrice IS NULL) OR
               (:minPrice IS NULL AND :maxPrice >= inv.price) OR
               (:maxPrice IS NULL AND :minPrice <= inv.price) OR
               (inv.price BETWEEN :minPrice AND :maxPrice)
               )
           """)
    Page<Product> findProductsByCategory(@Param("category") Category category,
                                         @Param("status") Status status,
                                         @Param("ratingFilter") Integer ratingFilter,
                                         @Param("minPrice") Integer minPrice,
                                         @Param("maxPrice") Integer maxPrice,
                                         Pageable pageable);
    @Query("""
            SELECT DISTINCT p
            FROM Product p
            JOIN p.inventories inv
            WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))
            AND p.status = :status
            AND (:ratingFilter IS NULL OR :ratingFilter <= p.averageRating)
            AND (
                (:minPrice IS NULL AND :maxPrice IS NULL) OR
                (:minPrice IS NULL AND :maxPrice >= inv.price) OR
                (:maxPrice IS NULL AND :minPrice <= inv.price) OR
                (inv.price BETWEEN :minPrice AND :maxPrice)
                )
            """)
    Page<Product> searchProduct(@Param("search") String search,
                                @Param("status") Status status,
                                @Param("ratingFilter") Integer ratingFilter,
                                @Param("minPrice") Integer minPrice,
                                @Param("maxPrice") Integer maxPrice,
                                Pageable pageable);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE products p
                    SET total_sold = total_sold + :quantityToAdd
                    WHERE p.product_id = :productId
                    """)
    void addTotalProductSoldOnOrder(@Param("productId") Integer productId, @Param("quantityToAdd") int quantityToAdd);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE products p
                    SET total_sold = CASE
                    WHEN total_sold >= :quantityToSubtract THEN total_sold - :quantityToSubtract
                    ELSE total_sold END
                    WHERE p.product_id = :productId
                    """)
    void subtractTotalProductSoldOnOrderCancellation(@Param("productId") Integer productId, @Param("quantityToSubtract") int quantityToSubtract);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE products p
                    SET p.average_rating = COALESCE((SELECT AVG(pr.rating)
                                         FROM product_reviews pr
                                         WHERE pr.product_id = p.product_id), 0)
                    WHERE p.product_id = :productId
                    """)
    void updateProductAverageRating(@Param("productId") Integer productId);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE products p
                    SET p.reviews_count = (
                    SELECT COUNT(pr.product_id)
                    FROM product_reviews pr
                    WHERE pr.product_id = p.product_id
                    )
                    WHERE p.product_id = :productId
                    """)
    void updateProductReviewsCount(@Param("productId") Integer productId);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.store.storeId = :storeId")
    Integer getStoreProductCount(@Param("storeId") Integer storeId);

}
